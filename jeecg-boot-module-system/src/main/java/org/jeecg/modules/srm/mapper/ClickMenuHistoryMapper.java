package org.jeecg.modules.srm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.ClickMenuHistory;

import java.util.List;

/**
 * @Description: click_menu_history
 * @Author: jeecg-boot
 * @Date:   2022-10-21
 * @Version: V1.0
 */
public interface ClickMenuHistoryMapper extends BaseMapper<ClickMenuHistory> {
    /**
     * 查询
     * @param clickMenuHistory
     * @return
     */
    List<ClickMenuHistory> queryList(@Param("query") ClickMenuHistory clickMenuHistory);
}
