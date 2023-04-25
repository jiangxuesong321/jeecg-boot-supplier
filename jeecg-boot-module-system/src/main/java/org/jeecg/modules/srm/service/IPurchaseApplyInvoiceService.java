package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurchaseApplyInvoice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: purchase_apply_invoice
 * @Author: jeecg-boot
 * @Date:   2023-02-15
 * @Version: V1.0
 */
public interface IPurchaseApplyInvoiceService extends IService<PurchaseApplyInvoice> {
    /**
     * 开票明细
     * @param id
     * @return
     */
    List<PurchaseApplyInvoice> queryInvoiceList(String id);
}
