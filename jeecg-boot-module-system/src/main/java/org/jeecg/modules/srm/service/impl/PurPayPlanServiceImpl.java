package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.PurPayPlan;
import org.jeecg.modules.srm.entity.PurPayPlanDetail;
import org.jeecg.modules.srm.mapper.PurPayPlanDetailMapper;
import org.jeecg.modules.srm.mapper.PurPayPlanMapper;
import org.jeecg.modules.srm.service.IPurPayPlanService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 付款计划
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Service
public class PurPayPlanServiceImpl extends ServiceImpl<PurPayPlanMapper, PurPayPlan> implements IPurPayPlanService {

	@Autowired
	private PurPayPlanMapper purPayPlanMapper;
	@Autowired
	private PurPayPlanDetailMapper purPayPlanDetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(PurPayPlan purPayPlan, List<PurPayPlanDetail> purPayPlanDetailList) {
		purPayPlanMapper.insert(purPayPlan);
		if(purPayPlanDetailList!=null && purPayPlanDetailList.size()>0) {
			for(PurPayPlanDetail entity:purPayPlanDetailList) {
				//外键设置
				entity.setPayPlanId(purPayPlan.getId());
				purPayPlanDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(PurPayPlan purPayPlan,List<PurPayPlanDetail> purPayPlanDetailList) {
		purPayPlanMapper.updateById(purPayPlan);
		
		//1.先删除子表数据
		purPayPlanDetailMapper.deleteByMainId(purPayPlan.getId());
		
		//2.子表数据重新插入
		if(purPayPlanDetailList!=null && purPayPlanDetailList.size()>0) {
			for(PurPayPlanDetail entity:purPayPlanDetailList) {
				//外键设置
				entity.setPayPlanId(purPayPlan.getId());
				purPayPlanDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		purPayPlanDetailMapper.deleteByMainId(id);
		purPayPlanMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			purPayPlanDetailMapper.deleteByMainId(id.toString());
			purPayPlanMapper.deleteById(id);
		}
	}
	
}
