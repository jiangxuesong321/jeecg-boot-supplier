package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.PurchaseApplyInvoice;
import org.jeecg.modules.srm.mapper.PurchaseApplyInvoiceMapper;
import org.jeecg.modules.srm.service.IPurchaseApplyInvoiceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: purchase_apply_invoice
 * @Author: jeecg-boot
 * @Date:   2023-02-15
 * @Version: V1.0
 */
@Service
public class PurchaseApplyInvoiceServiceImpl extends ServiceImpl<PurchaseApplyInvoiceMapper, PurchaseApplyInvoice> implements IPurchaseApplyInvoiceService {
    /**
     * 开票明细
     * @param id
     * @return
     */
    @Override
    public List<PurchaseApplyInvoice> queryInvoiceList(String id) {
        return baseMapper.queryInvoiceList(id);
    }
}
