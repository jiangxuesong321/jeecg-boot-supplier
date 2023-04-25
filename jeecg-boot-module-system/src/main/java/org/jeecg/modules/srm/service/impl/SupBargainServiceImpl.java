package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.srm.entity.BasRateMain;
import org.jeecg.modules.srm.entity.InquiryList;
import org.jeecg.modules.srm.entity.InquiryRecord;
import org.jeecg.modules.srm.entity.InquirySupplier;
import org.jeecg.modules.srm.entity.ProjectExchangeRate;
import org.jeecg.modules.srm.entity.SupBargain;
import org.jeecg.modules.srm.entity.SupQuote;
import org.jeecg.modules.srm.mapper.SupBargainMapper;
import org.jeecg.modules.srm.service.IBasRateMainService;
import org.jeecg.modules.srm.service.IInquiryListService;
import org.jeecg.modules.srm.service.IInquiryRecordService;
import org.jeecg.modules.srm.service.IInquirySupplierService;
import org.jeecg.modules.srm.service.IProjectExchangeRateService;
import org.jeecg.modules.srm.service.ISupBargainService;
import org.jeecg.modules.srm.service.ISupQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: sup_bargain
 * @Author: jeecg-boot
 * @Date:   2022-09-28
 * @Version: V1.0
 */
@Service
public class SupBargainServiceImpl extends ServiceImpl<SupBargainMapper, SupBargain> implements ISupBargainService {
    @Autowired
    private IInquirySupplierService inquirySupplierService;
    @Autowired
    private IProjectExchangeRateService iProjectExchangeRateService;
    @Autowired
    private ISupQuoteService iSupQuoteService;
    @Autowired
    private IInquiryRecordService inquiryRecordService;
    @Autowired
    private IInquiryListService inquiryListService;
    @Autowired
    private IBasRateMainService iBasRateMainService;
    /**
     * 更新议价
     * @param supBargain
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editBargain(SupBargain supBargain) throws Exception {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = loginUser.getUsername();
        String supplierId = loginUser.getSupplierId();
        Date nowTime = new Date();

        //更新议价记录
        SupBargain entity = this.getById(supBargain.getBargainId());
        entity.setUpdateTime(nowTime);
        entity.setUpdateUser(username);
        entity.setAttachment(supBargain.getAttachment());
        //议价运费
        entity.setSuppFareAmount(supBargain.getFareAmount());
        //议价含税单价
        entity.setSuppOrderPriceTax(supBargain.getOrderPriceTax());
        BigDecimal orderPriceTax = entity.getSuppOrderPriceTax();
        //数量
        BigDecimal qty = entity.getQty();
        //议价含税总额
        BigDecimal orderAmountTax = orderPriceTax.multiply(qty);
        entity.setSuppOrderAmountTax(orderAmountTax);

        //税率
        BigDecimal taxRate = entity.getTaxRate();
        if(taxRate.compareTo(new BigDecimal(100)) == 0){
            taxRate = BigDecimal.ZERO;
        }
        taxRate = taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));

        //议价未税总额
        BigDecimal orderAmount = orderAmountTax.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderAmount(orderAmount);

        //议价未税单价
        BigDecimal orderPrice = orderAmount.divide(qty,4,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderPrice(orderPrice);

        //报价明细
        SupQuote quote = iSupQuoteService.getById(entity.getQuoteId());

        InquiryRecord record = inquiryRecordService.getById(quote.getRecordId());
        InquiryList iMain = inquiryListService.getById(record.getInquiryId());
        //币种
        String currency = quote.getCurrency();
        BigDecimal exchangeRate = BigDecimal.ZERO;
        if("RMB".equals(currency)){
            exchangeRate = BigDecimal.ONE;
        }else{
            //获取项目对应的币种汇率
            ProjectExchangeRate rate = iProjectExchangeRateService.getOne(Wrappers.<ProjectExchangeRate>query().lambda().
                    eq(ProjectExchangeRate :: getProjectId,iMain.getProjectId()).
                    eq(ProjectExchangeRate :: getCurrencyB,currency));
            if(rate == null){
                String month = DateUtils.toPrevMonth().substring(0,7);
//                String month = "";
                List<BasRateMain> mainList = iBasRateMainService.list(Wrappers.<BasRateMain>query().lambda().
                        eq(BasRateMain :: getMonth,month).
                        eq(BasRateMain :: getCurrencyB,currency));
                if(mainList == null || mainList.size() == 0){
                    throw new Exception("请检查上月汇率是否存在");
                }
                BasRateMain bMain = mainList.get(0);
                exchangeRate = bMain.getValueB();
            }else{
                exchangeRate = rate.getValueB();
            }
        }
        BigDecimal addTax = BigDecimal.ONE;
        BigDecimal customsTax = BigDecimal.ONE;
        BigDecimal otherAmount = BigDecimal.ZERO;
        if(!"RMB".equals(currency)){
            addTax = quote.getAddTax();
            customsTax = quote.getCustomsTax();
            otherAmount = quote.getAmount();
        }
        //计算本币金额
        BigDecimal priceTaxLocal = entity.getSuppOrderPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
        priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
        priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderPriceTaxLocal(priceTaxLocal);

        BigDecimal amountTaxLocal = priceTaxLocal.multiply(entity.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderAmountTaxLocal(amountTaxLocal);

        BigDecimal amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderAmountLocal(amountLocal);

        BigDecimal priceLocal = amountLocal.divide(entity.getQty(),4,BigDecimal.ROUND_HALF_UP);
        entity.setSuppOrderPriceLocal(priceLocal);

        this.updateById(entity);

        //更新价格
//        quote.setOrderPrice(entity.getSuppOrderPrice());
//        quote.setOrderPriceTax(entity.getSuppOrderPriceTax());
//        quote.setOrderAmount(entity.getSuppOrderAmount());
//        quote.setOrderAmountTax(entity.getSuppOrderAmountTax());
//        quote.setOrderPriceLocal(entity.getSuppOrderPriceLocal());
//        quote.setOrderPriceTaxLocal(entity.getSuppOrderPriceTaxLocal());
//        quote.setOrderAmountLocal(entity.getSuppOrderAmountLocal());
//        quote.setOrderAmountTaxLocal(entity.getSuppOrderAmountTaxLocal());
//        iSupQuoteService.updateById(quote);

        //更新 询价供应商状态
        InquirySupplier supp = inquirySupplierService.getOne(Wrappers.<InquirySupplier>query().lambda().
                eq(InquirySupplier :: getDelFlag, CommonConstant.DEL_FLAG_0).
                eq(InquirySupplier :: getRecordId,entity.getRecordId()).
                eq(InquirySupplier :: getSupplierId,supplierId));
        if("4".equals(supp.getStatus())){
            supp.setIsBargin("2");
        }else{
            supp.setStatus("3");
        }

        inquirySupplierService.updateById(supp);
    }
}
