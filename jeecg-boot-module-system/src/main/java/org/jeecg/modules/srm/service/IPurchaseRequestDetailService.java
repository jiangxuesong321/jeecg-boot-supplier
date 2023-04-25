package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: purchase_request_detail
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IPurchaseRequestDetailService extends IService<PurchaseRequestDetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<PurchaseRequestDetail>
	 */
	public List<PurchaseRequestDetail> selectByMainId(String mainId);
}
