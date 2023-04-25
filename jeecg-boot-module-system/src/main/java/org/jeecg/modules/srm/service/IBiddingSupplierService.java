package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.BiddingSupplier;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 招标邀请供应商
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface IBiddingSupplierService extends IService<BiddingSupplier> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<BiddingSupplier>
	 */
	public List<BiddingSupplier> selectByMainId(String mainId);
}
