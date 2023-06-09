package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.StkOoBillDelivery;
import org.jeecg.modules.srm.entity.StkOoBill;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 出库单
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IStkOoBillService extends IService<StkOoBill> {

	/**
	 * 添加一对多
	 *
	 * @param stkOoBill
	 * @param stkOoBillDeliveryList
	 */
	public void saveMain(StkOoBill stkOoBill,List<StkOoBillDelivery> stkOoBillDeliveryList) ;
	
	/**
	 * 修改一对多
	 *
   * @param stkOoBill
   * @param stkOoBillDeliveryList
	 */
	public void updateMain(StkOoBill stkOoBill,List<StkOoBillDelivery> stkOoBillDeliveryList);
	
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
