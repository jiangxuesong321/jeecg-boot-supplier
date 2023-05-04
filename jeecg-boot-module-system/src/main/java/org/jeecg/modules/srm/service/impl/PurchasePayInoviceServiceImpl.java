package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sap.conn.jco.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.PurchasePayInoviceMapper;
import org.jeecg.modules.srm.service.*;
import org.jeecg.modules.system.entity.PurchaseOrderMain;
import org.jeecg.modules.system.mapper.PurchaseOrderMainMapper;
import org.jeecg.modules.system.model.SapConn;
import org.jeecg.modules.system.util.SAPConnUtil;
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
    private PurchaseOrderMainMapper purchaseOrderMainMapper;

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
//        invoceToSap(purchasePayInovice);

    }

    public void invoceToSap(PurchasePayInovice purchasePayInovice){
        //往sap推送
        LambdaQueryWrapper<PurchaseOrderMain> query = new LambdaQueryWrapper<>();
        query.eq(PurchaseOrderMain::getContactId, purchasePayInovice.getContractId());
        PurchaseOrderMain purchaseOrderMain = purchaseOrderMainMapper.selectOne(query);

        try{
            String JCO_HOST = "192.168.1.20";
            String JCO_SYNSNR = "00";
            String JCO_CLIENT = "200";
            String JCO_USER = "DLW_PDA";
            String JCO_PASSWD = "Delaware.001";
            String JCO_LANG = "ZH";
            String JCO_POOL_CAPACITY = "30";
            String JCO_PEAK_LIMIT = "100";
            String JCO_SAPROUTER = "/H/112.103.135.101/S/3299/W/Dch2017";

            SapConn con = new SapConn(JCO_HOST, JCO_SYNSNR, JCO_CLIENT, JCO_USER, JCO_PASSWD, JCO_LANG, JCO_POOL_CAPACITY, JCO_PEAK_LIMIT, JCO_SAPROUTER);
            JCoDestination jCoDestination = SAPConnUtil.connect(con);

            // 获取调用 RFC 函数对象
            JCoFunction func = jCoDestination.getRepository().getFunction("ZMM_SRM_MIRO_DEMO");
            // 配置传入参数
            JCoParameterList importParameterList = func.getImportParameterList();
            JCoStructure sc = importParameterList.getStructure("IS_HEAD");
//            sc.setValue("XBLNR", purchasePayInovice.getContractNumber());

            JCoTable item_table =  importParameterList.getTable("T_I_ITEM");
            item_table.appendRow();
            item_table.setValue("EBELN",purchaseOrderMain.getSapPo());
            item_table.setValue("RMWWR",purchasePayInovice.getInvoiceAmount());

            // 调用并获取返回值
            func.execute(jCoDestination);
            // 获取 内表 - ET_MARA
            JCoTable maraTable = func.getTableParameterList().getTable("ET_OUTPUT");
//            String po_sap = "";
//            for (int i = 0; i < maraTable.getNumRows(); i++) {
//                po_sap = maraTable.getString("EBELN");
//                break;
//            }
            if (StringUtils.isNotEmpty(purchasePayInovice.getContractNumber())){
                purchaseOrderMain.setContractNumber(purchasePayInovice.getContractNumber());
                purchaseOrderMainMapper.updateById(purchaseOrderMain);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        ContractBase cb = iContractBaseService.getById(purchasePayInovice.getContractId());

        purchasePayInovice.setUpdateTime(nowTime);
        purchasePayInovice.setUpdateBy(username);
        purchasePayInovice.setSupplierId(suppId);
        purchasePayInovice.setSupplierName(realname);
        purchasePayInovice.setStatus("1");
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
            pd.setInvoiceId(purchasePayInovice.getId());
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
