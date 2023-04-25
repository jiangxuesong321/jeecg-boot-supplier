package org.jeecg.modules.srm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.ContractObject;
import org.jeecg.modules.srm.entity.ContractObjectQty;
import org.jeecg.modules.srm.entity.PurchasePayInovice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 发票登记
 * @Author: jeecg-boot
 * @Date:   2022-06-20
 * @Version: V1.0
 */
public interface PurchasePayInoviceMapper extends BaseMapper<PurchasePayInovice> {
    /**
     * 开票明细
     * @param id
     * @return
     */
    List<ContractObjectQty> queryPurPayInvoiceDetailByMainId(@Param("id") String id);

    /**
     * 发票列表
     * @param page
     * @param purchasePayInovice
     * @return
     */
    IPage<PurchasePayInovice> fetchInvoiceList(Page<PurchasePayInovice> page, @Param("query") PurchasePayInovice purchasePayInovice);
}
