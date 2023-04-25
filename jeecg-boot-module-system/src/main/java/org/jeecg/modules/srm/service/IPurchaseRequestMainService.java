package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import org.jeecg.modules.srm.entity.PurchaseRequestMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: purchase_request_main
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IPurchaseRequestMainService extends IService<PurchaseRequestMain> {

	/**
	 * 添加一对多
	 *
	 * @param purchaseRequestMain
	 * @param purchaseRequestDetailList
	 */
	public void saveMain(PurchaseRequestMain purchaseRequestMain,List<PurchaseRequestDetail> purchaseRequestDetailList) ;
	
	/**
	 * 修改一对多
	 *
   * @param purchaseRequestMain
   * @param purchaseRequestDetailList
	 */
	public void updateMain(PurchaseRequestMain purchaseRequestMain,List<PurchaseRequestDetail> purchaseRequestDetailList);
	
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

	/**
	 * 需求
	 * @param contractId
	 * @return
	 */
    PurchaseRequestMain getPurchaseRequest(String contractId);
}
