package org.jeecg.modules.srm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.PurPayApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 付款申请
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
public interface PurPayApplyMapper extends BaseMapper<PurPayApply> {
    /**
     * 更新发票已用金额
     * @param id
     */
    void updateInvoiceAmount(@Param("id") String id);
}
