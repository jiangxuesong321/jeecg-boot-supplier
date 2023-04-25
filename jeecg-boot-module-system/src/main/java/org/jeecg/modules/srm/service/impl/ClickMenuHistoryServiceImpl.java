package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.srm.entity.ClickMenuHistory;
import org.jeecg.modules.srm.mapper.ClickMenuHistoryMapper;
import org.jeecg.modules.srm.service.IClickMenuHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: click_menu_history
 * @Author: jeecg-boot
 * @Date:   2022-10-21
 * @Version: V1.0
 */
@Service
public class ClickMenuHistoryServiceImpl extends ServiceImpl<ClickMenuHistoryMapper, ClickMenuHistory> implements IClickMenuHistoryService {
    /**
     * 查询
     * @param clickMenuHistory
     * @return
     */
    @Override
    public List<ClickMenuHistory> queryList(ClickMenuHistory clickMenuHistory) {
        return baseMapper.queryList(clickMenuHistory);
    }
}
