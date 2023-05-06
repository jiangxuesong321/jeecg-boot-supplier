package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.PurchasePayInoviceMapper;
import org.jeecg.modules.srm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 发票登记
 * @Author: jeecg-boot
 * @Date:   2022-06-20
 * @Version: V1.0
 */
@Service
public class PurchasePayInoviceServiceImpl extends ServiceImpl<PurchasePayInoviceMapper, PurchasePayInovice> implements IPurchasePayInoviceService {
    @Autowired
    private IPurchasePayInvoiceDetailService iPurchasePayInvoiceDetailService;
    @Autowired
    private IContractObjectQtyService iContractObjectQtyService;
    @Autowired
    private IContractBaseService iContractBaseService;
    @Autowired
    private IPurchaseApplyInvoiceService iPurchaseApplyInvoiceService;
    @Autowired
    private IStkIoBillEntryService iStkIoBillEntryService;
    /**
     * 发票登记
     * @param purchasePayInovice
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveInvoice(PurchasePayInovice purchasePayInovice) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = loginUser.getUsername();
        String realname = loginUser.getRealname();
        String suppId = loginUser.getSupplierId();
        Date nowTime = new Date();

        ContractBase cb = iContractBaseService.getById(purchasePayInovice.getContractId());

        String id = String.valueOf(IdWorker.getId());

        purchasePayInovice.setId(id);
        purchasePayInovice.setCreateTime(nowTime);
        purchasePayInovice.setCreateBy(username);
        purchasePayInovice.setUpdateTime(nowTime);
        purchasePayInovice.setUpdateBy(username);
        purchasePayInovice.setSupplierId(suppId);
        purchasePayInovice.setSupplierName(realname);
        purchasePayInovice.setStatus("1");
        purchasePayInovice.setDelFlag(CommonConstant.NO_READ_FLAG);
        purchasePayInovice.setExchangeRate(cb.getContractExchangeRate());
        this.saveOrUpdate(purchasePayInovice);

        List<PurchasePayInvoiceDetail> detailList = purchasePayInovice.getDetailList();
        List<String> recordIds = new ArrayList<>();
        Map<String, BigDecimal> map = new HashMap<>();
        for(PurchasePayInvoiceDetail pd : detailList){
            pd.setId(String.valueOf(IdWorker.getId()));
            pd.setInvoiceId(id);
            pd.setCreateTime(nowTime);
            pd.setCreateBy(username);
            pd.setUpdateTime(nowTime);
            pd.setUpdateBy(username);
            pd.setDelFlag(CommonConstant.NO_READ_FLAG);

            map.put(pd.getBillDetailId(),pd.getInvoiceRate());

            recordIds.add(pd.getBillDetailId());
        }
        iPurchasePayInvoiceDetailService.saveOrUpdateBatch(detailList);

        //更新付款比例
        if(recordIds != null && recordIds.size() > 0){
            List<ContractObjectQty> objList = iContractObjectQtyService.listByIds(recordIds);
            for(ContractObjectQty co : objList){
                BigDecimal invoiceRate = map.get(co.getId());
                co.setInvoiceRate(co.getInvoiceRate().add(invoiceRate));
            }
            iContractObjectQtyService.updateBatchById(objList);
        }
    }

    /**
     * 发票登记
     * @param purchasePayInovice
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInvoice(PurchasePayInovice purchasePayInovice) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = loginUser.getUsername();
        String realname = loginUser.getRealname();
        String suppId = loginUser.getSupplierId();
        Date nowTime = new Date();

//        ContractBase cb = iContractBaseService.getById(purchasePayInovice.getContractId());

        purchasePayInovice.setUpdateTime(nowTime);
        purchasePayInovice.setUpdateBy(username);
        purchasePayInovice.setSupplierId(suppId);
        purchasePayInovice.setSupplierName(realname);
        purchasePayInovice.setStatus("1");
//        purchasePayInovice.setExchangeRate(cb.getContractExchangeRate());
        this.saveOrUpdate(purchasePayInovice);

//        //删除明细
//        List<PurchasePayInvoiceDetail> existList = iPurchasePayInvoiceDetailService.list(Wrappers.<PurchasePayInvoiceDetail>query().lambda().
//                eq(PurchasePayInvoiceDetail :: getInvoiceId,purchasePayInovice.getId()).
//                eq(PurchasePayInvoiceDetail :: getDelFlag,CommonConstant.DEL_FLAG_0));
//        Map<String,BigDecimal> exist = new HashMap<>();
//        for(PurchasePayInvoiceDetail pd : existList){
//            pd.setDelFlag(CommonConstant.HAS_READ_FLAG);
//
//            exist.put(pd.getBillDetailId(),pd.getInvoiceRate());
//        }
//        iPurchasePayInvoiceDetailService.updateBatchById(existList);

        List<PurchasePayInvoiceDetail> detailList = purchasePayInovice.getDetailList();
        List<StkIoBillEntry> goods = new ArrayList<>();
        List<String> recordIds = new ArrayList<>();
        Map<String, BigDecimal> map = new HashMap<>();
        for(PurchasePayInvoiceDetail pd : detailList){
            pd.setUpdateTime(nowTime);
            pd.setUpdateBy(username);
            pd.setDelFlag(CommonConstant.NO_READ_FLAG);

            StkIoBillEntry sibe = iStkIoBillEntryService.getById(pd.getBillDetailId());
            sibe.setInvoiceRate(sibe.getInvoiceRate().add(pd.getInvoiceRate()));
            goods.add(sibe);
        }
        iPurchasePayInvoiceDetailService.saveOrUpdateBatch(detailList);
        iStkIoBillEntryService.updateBatchById(goods);

    }

    /**
     * 发票登记
     * @param purchasePayInovice
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void draftInvoice(PurchasePayInovice purchasePayInovice) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String username = loginUser.getUsername();
        String realname = loginUser.getRealname();
        String suppId = loginUser.getSupplierId();
        Date nowTime = new Date();

        ContractBase cb = iContractBaseService.getById(purchasePayInovice.getContractId());

        String id = String.valueOf(IdWorker.getId());
        if(StringUtils.isNotEmpty(purchasePayInovice.getId())){
            id = purchasePayInovice.getId();
        }else{
            purchasePayInovice.setId(id);
            purchasePayInovice.setCreateTime(nowTime);
            purchasePayInovice.setCreateBy(username);
        }

        purchasePayInovice.setUpdateTime(nowTime);
        purchasePayInovice.setUpdateBy(username);
        purchasePayInovice.setSupplierId(suppId);
        purchasePayInovice.setSupplierName(realname);
        purchasePayInovice.setStatus("0");
        purchasePayInovice.setDelFlag(CommonConstant.NO_READ_FLAG);
        purchasePayInovice.setExchangeRate(cb.getContractExchangeRate());
        this.saveOrUpdate(purchasePayInovice);

        //删除明细
        List<PurchasePayInvoiceDetail> existList = iPurchasePayInvoiceDetailService.list(Wrappers.<PurchasePayInvoiceDetail>query().lambda().
                eq(PurchasePayInvoiceDetail :: getInvoiceId,purchasePayInovice.getId()).
                eq(PurchasePayInvoiceDetail :: getDelFlag,CommonConstant.DEL_FLAG_0));

        Map<String,BigDecimal> exist = new HashMap<>();
        for(PurchasePayInvoiceDetail pd : existList){
            pd.setDelFlag(CommonConstant.HAS_READ_FLAG);

            exist.put(pd.getBillDetailId(),pd.getInvoiceRate());
        }
        iPurchasePayInvoiceDetailService.updateBatchById(existList);

        List<PurchasePayInvoiceDetail> detailList = purchasePayInovice.getDetailList();
        List<String> recordIds = new ArrayList<>();
        Map<String, BigDecimal> map = new HashMap<>();
        for(PurchasePayInvoiceDetail pd : detailList){
            pd.setId(String.valueOf(IdWorker.getId()));
            pd.setInvoiceId(id);
            pd.setCreateTime(nowTime);
            pd.setCreateBy(username);
            pd.setUpdateTime(nowTime);
            pd.setUpdateBy(username);
            pd.setDelFlag(CommonConstant.NO_READ_FLAG);

            map.put(pd.getBillDetailId(),pd.getInvoiceRate());
            recordIds.add(pd.getBillDetailId());
        }
        iPurchasePayInvoiceDetailService.saveOrUpdateBatch(detailList);

        //更新付款比例
        if(recordIds != null && recordIds.size() > 0){
            List<ContractObjectQty> objList = iContractObjectQtyService.listByIds(recordIds);
            for(ContractObjectQty co : objList){
                BigDecimal invoiceRate = map.get(co.getId());
                BigDecimal existRate = exist.get(co.getId()) == null ? BigDecimal.ZERO : exist.get(co.getId());
                co.setInvoiceRate(co.getInvoiceRate().add(invoiceRate).subtract(existRate));
            }
            iContractObjectQtyService.updateBatchById(objList);
        }
    }

    /**
     * 开票明细
     * @param id
     * @return
     */
    @Override
    public List<ContractObjectQty> queryPurPayInvoiceDetailByMainId(String id) {
        return baseMapper.queryPurPayInvoiceDetailByMainId(id);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMain(String id) throws Exception {
        //判断发票是否在使用中
        List<PurchaseApplyInvoice> paiList = iPurchaseApplyInvoiceService.list(Wrappers.<PurchaseApplyInvoice>query().lambda().
                eq(PurchaseApplyInvoice :: getDelFlag,CommonConstant.DEL_FLAG_0).
                eq(PurchaseApplyInvoice :: getInvoiceId,id));
        if(paiList != null && paiList.size() > 0){
            throw new Exception("发票已经被使用,不能删除");
        }

        List<PurchasePayInvoiceDetail> detailList = iPurchasePayInvoiceDetailService.list(Wrappers.<PurchasePayInvoiceDetail>query().lambda().
                eq(PurchasePayInvoiceDetail :: getDelFlag,CommonConstant.DEL_FLAG_0).
                eq(PurchasePayInvoiceDetail :: getInvoiceId,id));
        List<String> ids = new ArrayList<>();
        for(PurchasePayInvoiceDetail ppad : detailList){
            ids.add(ppad.getBillDetailId());
        }
        //发票比例还原
        List<ContractObjectQty> coList = iContractObjectQtyService.listByIds(ids);
        for (ContractObjectQty co : coList){
            for(PurchasePayInvoiceDetail pd : detailList){
                if(co.getId().equals(pd.getBillDetailId())){
                    co.setInvoiceRate(co.getInvoiceRate().subtract(pd.getInvoiceRate()));
                    break;
                }
            }
        }
        iContractObjectQtyService.updateBatchById(coList);

        UpdateWrapper<PurchasePayInovice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("del_flag",CommonConstant.DEL_FLAG_1);
        updateWrapper.eq("id",id);
        this.update(updateWrapper);

        UpdateWrapper<PurchasePayInvoiceDetail> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.set("del_flag",CommonConstant.DEL_FLAG_1);
        updateWrapper1.eq("invoice_id",id);
        iPurchasePayInvoiceDetailService.update(updateWrapper1);
    }

    /**
     * 发票列表
     * @param page
     * @param purchasePayInovice
     * @return
     */
    @Override
    public IPage<PurchasePayInovice> fetchInvoiceList(Page<PurchasePayInovice> page, PurchasePayInovice purchasePayInovice) {
        return baseMapper.fetchInvoiceList(page,purchasePayInovice);
    }
}
