package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.srm.entity.BasRateMain;
import org.jeecg.modules.srm.entity.BasTrade;
import org.jeecg.modules.srm.entity.InquiryList;
import org.jeecg.modules.srm.entity.InquiryRecord;
import org.jeecg.modules.srm.entity.InquirySupplier;
import org.jeecg.modules.srm.entity.ProjectExchangeRate;
import org.jeecg.modules.srm.entity.SupQuote;
import org.jeecg.modules.srm.entity.SupQuoteChild;
import org.jeecg.modules.srm.mapper.SupQuoteMapper;
import org.jeecg.modules.srm.service.IBasRateMainService;
import org.jeecg.modules.srm.service.IBasTradeService;
import org.jeecg.modules.srm.service.IInquiryListService;
import org.jeecg.modules.srm.service.IInquiryRecordService;
import org.jeecg.modules.srm.service.IInquirySupplierService;
import org.jeecg.modules.srm.service.IProjectExchangeRateService;
import org.jeecg.modules.srm.service.ISupQuoteChildService;
import org.jeecg.modules.srm.service.ISupQuoteService;
import org.jeecg.modules.srm.vo.InquiryListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: sup_quote
 * @Author: jeecg-boot
 * @Date:   2022-09-27
 * @Version: V1.0
 */
@Service
public class SupQuoteServiceImpl extends ServiceImpl<SupQuoteMapper, SupQuote> implements ISupQuoteService {
    @Autowired
    private IInquiryRecordService inquiryRecordService;
    @Autowired
    private IInquiryListService inquiryListService;
    @Autowired
    private IInquirySupplierService inquirySupplierService;
    @Autowired
    private IProjectExchangeRateService iProjectExchangeRateService;
    @Autowired
    private ISupQuoteChildService iSupQuoteChildService;
    @Autowired
    private IBasRateMainService iBasRateMainService;
    @Autowired
    private IBasTradeService iBasTradeService;
    /**
     * 报价
     * @param page
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveQuote(InquiryListPage page) throws Exception {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = sysUser.getUsername();
        String suppId = sysUser.getSupplierId();
        Date nowTime = new Date();

        //更新 询价中
        InquiryList iMain = inquiryListService.getById(page.getInquiryId());
        iMain.setInquiryStatus("1");
        inquiryListService.updateById(iMain);
        //更新 询价明细
        InquiryRecord record = inquiryRecordService.getById(page.getId());
        record.setStatus("1");
        inquiryRecordService.updateById(record);
        //更新 询价供应商状态,已报价
        InquirySupplier supp = inquirySupplierService.getOne(Wrappers.<InquirySupplier>query().lambda().
                eq(InquirySupplier :: getDelFlag,CommonConstant.DEL_FLAG_0).
                eq(InquirySupplier :: getRecordId,record.getId()).
                eq(InquirySupplier :: getSupplierId,suppId));
        supp.setStatus("1");
        inquirySupplierService.updateById(supp);

        //生成报价记录
        List<InquiryRecord> recordList = page.getRecordList();
        List<SupQuoteChild> childList = new ArrayList<>();
        List<SupQuote> quoteList = new ArrayList<>();

        //删除已报价记录,重新插入
        this.remove(Wrappers.<SupQuote>query().lambda().eq(SupQuote :: getRecordId,record.getId()).eq(SupQuote :: getSuppId,suppId));

        BigDecimal addTax = BigDecimal.ONE;
        BigDecimal customsTax = BigDecimal.ONE;
        BigDecimal otherAmount = BigDecimal.ZERO;
        for(InquiryRecord ir : recordList){
            //币种
            String currency = ir.getSuppCurrency();
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
//                    String month = "";
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

                //判断是否存在贸易方式对应的公式
                List<BasTrade> tradeList = iBasTradeService.list(Wrappers.<BasTrade>query().lambda().eq(BasTrade :: getTradeType,ir.getTradeType()));
                if((tradeList == null || tradeList.size() == 0) && !"RMB".equals(currency)){
                    throw new Exception("请联系中环领先配置相应的贸易方式计算公式");
                }else{
                    if(!"RMB".equals(currency)){
                        BasTrade trade = tradeList.get(0);
                        if("EXW".equals(trade.getTradeType()) || "FCA".equals(trade.getTradeType())
                                || "CIP".equals(trade.getTradeType()) || "CIF".equals(trade.getTradeType())){
                            addTax = trade.getAddTax().divide(new BigDecimal(100));
                            customsTax = trade.getCustomsTax().divide(new BigDecimal(100));
                            otherAmount = trade.getAmount();
                        }else if("DAP".equals(trade.getTradeType())){
                            addTax = trade.getAddTax().divide(new BigDecimal(100));
                            customsTax = trade.getCustomsTax().divide(new BigDecimal(100));
                        }
                    }
                }
            }

            String id = String.valueOf(IdWorker.getId());
            SupQuote quote = new SupQuote();
            quote.setId(id);
            quote.setSuppId(suppId);
            quote.setRecordId(record.getId());
            quote.setLeadTime(ir.getSuppLeadTime());
            quote.setComment(page.getComment());
            quote.setAttachment(page.getAttachment());
            quote.setDelFlag(CommonConstant.NO_READ_FLAG);
            quote.setCreateTime(nowTime);
            quote.setCreateUser(username);
            quote.setUpdateTime(nowTime);
            quote.setUpdateUser(username);
            quote.setBrandName(ir.getSuppBrandName());
            quote.setSpeType(ir.getSuppSpeType());
            quote.setCurrency(ir.getSuppCurrency());
            quote.setQty(ir.getQty());
            quote.setOrderPriceTax(ir.getOrderPriceTax());
            quote.setOrderAmountTax(ir.getOrderAmountTax());
            quote.setTaxRate(ir.getTaxRate());
            quote.setExchangeRate(exchangeRate);
            quote.setAmount(otherAmount);
            quote.setAddTax(addTax);
            quote.setCustomsTax(customsTax);

            //计算未税总额
            BigDecimal taxRate = quote.getTaxRate();
            if(taxRate.compareTo(new BigDecimal(100)) == 0){
                taxRate = BigDecimal.ZERO;
            }
            taxRate = taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));

            BigDecimal orderAmount = quote.getOrderAmountTax().divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
            quote.setOrderAmount(orderAmount);
            BigDecimal orderPrice = orderAmount.divide(quote.getQty(),4,BigDecimal.ROUND_HALF_UP);
            quote.setOrderPrice(orderPrice);

            //计算本币金额
            BigDecimal priceTaxLocal = quote.getOrderPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
            priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
            priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
            quote.setOrderPriceTaxLocal(priceTaxLocal);

            BigDecimal amountTaxLocal = priceTaxLocal.multiply(quote.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
            quote.setOrderAmountTaxLocal(amountTaxLocal);

            BigDecimal amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
            quote.setOrderAmountLocal(amountLocal);

            BigDecimal priceLocal = amountLocal.divide(quote.getQty(),4,BigDecimal.ROUND_HALF_UP);
            quote.setOrderPriceLocal(priceLocal);


            quote.setTradeType(ir.getTradeType());
            quote.setFareAmount(ir.getFareAmount());
            quoteList.add(quote);

            if(ir.getChildList() != null && ir.getChildList().size() > 0){
                for(SupQuoteChild sc : ir.getChildList()){
                    sc.setId(String.valueOf(IdWorker.getId()));
                    sc.setQuoteId(quote.getId());
                    sc.setDelFlag(CommonConstant.NO_READ_FLAG);
                    sc.setCreateTime(nowTime);
                    sc.setCreateUser(username);
                    sc.setUpdateTime(nowTime);
                    sc.setUpdateUser(username);
                    sc.setCurrency(quote.getCurrency());
//                    sc.setTaxRate(quote.getTaxRate());

                    //计算未税总额
                    BigDecimal cTaxRate = sc.getTaxRate();
                    if(cTaxRate.compareTo(new BigDecimal(100)) == 0){
                        cTaxRate = BigDecimal.ZERO;
                    }
                    taxRate = cTaxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));
                    orderAmount = sc.getOrderAmountTax().divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderAmount(orderAmount);
                    orderPrice = orderAmount.divide(sc.getQty(),4,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderPrice(orderPrice);

                    //计算本币金额
                    priceTaxLocal = sc.getOrderPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
                    priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
                    priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderPriceTaxLocal(priceTaxLocal);

                    amountTaxLocal = priceTaxLocal.multiply(sc.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderAmountTaxLocal(amountTaxLocal);

                    amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderAmountLocal(amountLocal);

                    priceLocal = amountLocal.divide(sc.getQty(),4,BigDecimal.ROUND_HALF_UP);
                    sc.setOrderPriceLocal(priceLocal);

                    childList.add(sc);
                }
            }else{
                SupQuoteChild sc = new SupQuoteChild();
                sc.setId(String.valueOf(IdWorker.getId()));
                sc.setQuoteId(quote.getId());
                sc.setDelFlag(CommonConstant.NO_READ_FLAG);
                sc.setCreateTime(nowTime);
                sc.setCreateUser(username);
                sc.setUpdateTime(nowTime);
                sc.setUpdateUser(username);
                sc.setCurrency(quote.getCurrency());
                sc.setTaxRate(quote.getTaxRate());
                sc.setQty(quote.getQty());
                sc.setTaxRate(quote.getTaxRate());
                sc.setOrderPriceTax(quote.getOrderPriceTax());
                sc.setOrderAmountTax(quote.getOrderAmountTax());
                sc.setOrderPrice(quote.getOrderPrice());
                sc.setOrderAmount(quote.getOrderAmount());
                sc.setProdName(ir.getProdName());
                sc.setBrandName(quote.getBrandName());
                sc.setSpeType(quote.getSpeType());

                sc.setOrderPriceTaxLocal(quote.getOrderPriceTaxLocal());
                sc.setOrderAmountTaxLocal(quote.getOrderAmountTaxLocal());
                sc.setOrderPriceLocal(quote.getOrderPriceLocal());
                sc.setOrderAmountLocal(quote.getOrderAmountLocal());

                childList.add(sc);
            }

        }
        this.saveBatch(quoteList);
        if(childList != null && childList.size() > 0){
            iSupQuoteChildService.saveBatch(childList);
        }
    }
}
