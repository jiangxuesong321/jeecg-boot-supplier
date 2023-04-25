package org.jeecg.modules.srm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srm.vo.BiddingMainPage;
import org.jeecg.modules.srm.vo.BiddingRecordPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 招标主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface IBiddingMainService extends IService<BiddingMain> {

	/**
	 * 添加一对多
	 *
	 * @param biddingMain
	 * @param biddingRecordList
	 * @param biddingSupplierList
	 * @param biddingProfessionalsList
	 */
	public void saveMain(BiddingMain biddingMain,List<BiddingRecord> biddingRecordList,List<BiddingSupplier> biddingSupplierList,List<BiddingProfessionals> biddingProfessionalsList) ;
	
	/**
	 * 修改一对多
	 *
   * @param biddingMain
   * @param biddingRecordList
   * @param biddingSupplierList
   * @param biddingProfessionalsList
	 */
	public void updateMain(BiddingMain biddingMain,List<BiddingRecord> biddingRecordList,List<BiddingSupplier> biddingSupplierList,List<BiddingProfessionals> biddingProfessionalsList);
	
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
	 * 招标列表
	 * @param page
	 * @param biddingMain
	 * @return
	 */
    IPage<BiddingMain> queryPageList(Page<BiddingMain> page, BiddingMain biddingMain);

	/**
	 * 招标明细
	 * @param biddingMain
	 * @return
	 */
    List<BiddingRecord> fetchDetailList(BiddingMain biddingMain);

	/**
	 * 报价
	 * @param biddingMainPage
	 */
	void toQuote(BiddingMainPage biddingMainPage) throws Exception;

	/**
	 * 获取中标供应商信息
	 * @param page
	 * @return
	 */
	BasSupplier getSuppInfo(BiddingMain page);

	/**
	 * 交期排名
	 * @param id
	 * @param suppId
	 * @return
	 */
    String getLeadTimeRank(String id, String suppId);

	/**
	 * 报价排名
	 * @param id
	 * @param suppId
	 * @return
	 */
	String getPriceRank(String id, String suppId);

	/**
	 * 中标议价列表
	 * @param page
	 * @param biddingMain
	 * @return
	 */
    IPage<BiddingRecordPage> fetchBarginList(Page<BiddingMain> page, BiddingMain biddingMain);

	/**
	 * 议价
	 * @param page
	 */
	void submitBargin(BiddingRecordPage page) throws Exception;
}
