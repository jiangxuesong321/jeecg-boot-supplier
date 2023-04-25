package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.PurPayApplyDetail;
import org.jeecg.modules.srm.entity.PurPayApply;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srm.entity.PurchaseApplyInvoice;
import org.jeecg.modules.srm.entity.PurchasePayInovice;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 付款申请
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
public interface IPurPayApplyService extends IService<PurPayApply> {

	/**
	 * 添加一对多
	 *
	 * @param purPayApply
	 * @param purPayApplyDetailList
	 */
	public void saveMain(PurPayApply purPayApply,List<PurPayApplyDetail> purPayApplyDetailList, List<PurchaseApplyInvoice> invoiceList) throws Exception;
	
	/**
	 * 修改一对多
	 *
   * @param purPayApply
   * @param purPayApplyDetailList
	 */
	public void updateMain(PurPayApply purPayApply,List<PurPayApplyDetail> purPayApplyDetailList, List<PurchaseApplyInvoice> invoiceList) throws Exception;
	
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
	 * 草稿
	 * @param purPayApply
	 * @param purPayApplyDetailList
	 */
    void draft(PurPayApply purPayApply, List<PurPayApplyDetail> purPayApplyDetailList, List<PurchaseApplyInvoice> invoiceList) throws Exception;
}
