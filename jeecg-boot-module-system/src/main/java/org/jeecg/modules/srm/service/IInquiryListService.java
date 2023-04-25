package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.InquiryRecord;
import org.jeecg.modules.srm.entity.InquirySupplier;
import org.jeecg.modules.srm.entity.InquiryList;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 询价单主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface IInquiryListService extends IService<InquiryList> {

	/**
	 * 添加一对多
	 *
	 * @param inquiryList
	 * @param inquiryRecordList
	 * @param inquirySupplierList
	 */
	public void saveMain(InquiryList inquiryList,List<InquiryRecord> inquiryRecordList,List<InquirySupplier> inquirySupplierList) ;
	
	/**
	 * 修改一对多
	 *
   * @param inquiryList
   * @param inquiryRecordList
   * @param inquirySupplierList
	 */
	public void updateMain(InquiryList inquiryList,List<InquiryRecord> inquiryRecordList,List<InquirySupplier> inquirySupplierList);
	
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
