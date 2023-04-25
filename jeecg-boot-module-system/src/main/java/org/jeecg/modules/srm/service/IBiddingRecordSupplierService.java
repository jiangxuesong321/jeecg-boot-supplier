package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.BiddingRecordSupplier;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: bidding_record_supplier
 * @Author: jeecg-boot
 * @Date:   2022-10-24
 * @Version: V1.0
 */
public interface IBiddingRecordSupplierService extends IService<BiddingRecordSupplier> {
    /**
     * 投标数量
     * @param suppId
     * @return
     */
    List<BiddingRecordSupplier> fetchBiddingList(String suppId);
}
