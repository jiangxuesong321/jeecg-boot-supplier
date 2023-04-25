package org.jeecg.modules.srm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.PurchaseRequestMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: purchase_request_main
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface PurchaseRequestMainMapper extends BaseMapper<PurchaseRequestMain> {
    /**
     * 需求
     * @param contractId
     * @return
     */
    PurchaseRequestMain getPurchaseRequest(@Param("contractId") String contractId);
}
