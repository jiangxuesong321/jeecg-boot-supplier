package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.StkIoBillEntry;
import org.jeecg.modules.srm.entity.StkIoBill;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 入库单
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
public interface IStkIoBillService extends IService<StkIoBill> {

	/**
	 * 添加一对多
	 *
	 * @param stkIoBill
	 * @param stkIoBillEntryList
	 */
	public void saveMain(StkIoBill stkIoBill,List<StkIoBillEntry> stkIoBillEntryList) ;
	
	/**
	 * 修改一对多
	 *
   * @param stkIoBill
   * @param stkIoBillEntryList
	 */
	public void updateMain(StkIoBill stkIoBill,List<StkIoBillEntry> stkIoBillEntryList);
	
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
