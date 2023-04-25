package org.jeecg.modules.srm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.PurchaseApplyInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: purchase_apply_invoice
 * @Author: jeecg-boot
 * @Date:   2023-02-15
 * @Version: V1.0
 */
public interface PurchaseApplyInvoiceMapper extends BaseMapper<PurchaseApplyInvoice> {
    /**
     * 开票明细
     * @param id
     * @return
     */
    List<PurchaseApplyInvoice> queryInvoiceList(@Param("id") String id);
}
