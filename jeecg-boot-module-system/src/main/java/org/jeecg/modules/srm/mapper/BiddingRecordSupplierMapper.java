package org.jeecg.modules.srm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.BiddingRecordSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: bidding_record_supplier
 * @Author: jeecg-boot
 * @Date:   2022-10-24
 * @Version: V1.0
 */
public interface BiddingRecordSupplierMapper extends BaseMapper<BiddingRecordSupplier> {
    /**
     * 投标数量
     * @param suppId
     * @return
     */
    List<BiddingRecordSupplier> fetchBiddingList(@Param("suppId") String suppId);
}
