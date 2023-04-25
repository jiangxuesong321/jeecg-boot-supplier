package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.BiddingProfessionals;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: bidding_professionals
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface IBiddingProfessionalsService extends IService<BiddingProfessionals> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<BiddingProfessionals>
	 */
	public List<BiddingProfessionals> selectByMainId(String mainId);
}
