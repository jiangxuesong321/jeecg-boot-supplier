package org.jeecg.modules.srm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.ContractObject;
import org.jeecg.modules.srm.entity.ContractObjectQty;
import org.jeecg.modules.srm.entity.PurchasePayInovice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 发票登记
 * @Author: jeecg-boot
 * @Date:   2022-06-20
 * @Version: V1.0
 */
public interface IPurchasePayInoviceService extends IService<PurchasePayInovice> {
    /**
     * 发票登记
     * @param purchasePayInovice
     */
    void saveInvoice(PurchasePayInovice purchasePayInovice);

    /**
     * 发票登记
     * @param purchasePayInovice
     */
    void editInvoice(PurchasePayInovice purchasePayInovice);

    /**
     * 发票登记
     * @param purchasePayInovice
     */
    void draftInvoice(PurchasePayInovice purchasePayInovice);

    /**
     * 开票明细
     * @param id
     * @return
     */
    List<ContractObjectQty> queryPurPayInvoiceDetailByMainId(String id);

    /**
     * 删除
     * @param id
     */
    void delMain(String id) throws Exception;

    /**
     * 发票列表
     * @param page
     * @param purchasePayInovice
     * @return
     */
    IPage<PurchasePayInovice> fetchInvoiceList(Page<PurchasePayInovice> page, PurchasePayInovice purchasePayInovice);
}
