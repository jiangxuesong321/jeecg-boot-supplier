package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 供应商基本信息
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
public interface IBasSupplierService extends IService<BasSupplier> {

	/**
	 * 添加一对多
	 *
	 * @param basSupplier
	 * @param basSupplierContactList
	 * @param basSupplierQualificationList
	 */
	public void saveMain(BasSupplier basSupplier,List<BasSupplierContact> basSupplierContactList,List<BasSupplierQualification> basSupplierQualificationList,
						 List<BasSupplierBank> basSupplierBankList,List<BasSupplierFast> basSupplierFastList) ;
	
	/**
	 * 修改一对多
	 *
   * @param basSupplier
   * @param basSupplierContactList
   * @param basSupplierQualificationList
	 */
	public void updateMain(BasSupplier basSupplier, List<BasSupplierContact> basSupplierContactList, List<BasSupplierQualification> basSupplierQualificationList,
						   List<BasSupplierBank> basSupplierBankList, List<BasSupplierFast> basSupplierFastList);
	
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
