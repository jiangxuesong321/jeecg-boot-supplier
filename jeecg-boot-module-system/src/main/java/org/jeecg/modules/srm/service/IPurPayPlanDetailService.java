package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurPayPlanDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 付款计划明细
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IPurPayPlanDetailService extends IService<PurPayPlanDetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<PurPayPlanDetail>
	 */
	public List<PurPayPlanDetail> selectByMainId(String mainId);
}
