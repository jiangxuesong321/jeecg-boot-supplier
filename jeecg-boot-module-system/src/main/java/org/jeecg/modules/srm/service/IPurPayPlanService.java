package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurPayPlanDetail;
import org.jeecg.modules.srm.entity.PurPayPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 付款计划
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IPurPayPlanService extends IService<PurPayPlan> {

	/**
	 * 添加一对多
	 *
	 * @param purPayPlan
	 * @param purPayPlanDetailList
	 */
	public void saveMain(PurPayPlan purPayPlan,List<PurPayPlanDetail> purPayPlanDetailList) ;
	
	/**
	 * 修改一对多
	 *
   * @param purPayPlan
   * @param purPayPlanDetailList
	 */
	public void updateMain(PurPayPlan purPayPlan,List<PurPayPlanDetail> purPayPlanDetailList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
