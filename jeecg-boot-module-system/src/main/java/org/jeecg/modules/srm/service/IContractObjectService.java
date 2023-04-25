package org.jeecg.modules.srm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.BiddingMain;
import org.jeecg.modules.srm.entity.ContractBase;
import org.jeecg.modules.srm.entity.ContractObject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同标的
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
public interface IContractObjectService extends IService<ContractObject> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ContractObject>
	 */
	public List<ContractObject> selectByMainId(String mainId);

	/**
	 * 合同
	 * @param contractBase
	 * @return
	 */
    List<ContractObject> getContractDetailList(ContractBase contractBase);

	/**
	 * 合同明细
	 * @param page
	 * @param obj
	 * @return
	 */
    IPage<ContractObject> listByDetailList(Page<ContractObject> page, ContractObject obj);

	/**
	 * 供应商中标率
	 * @param obj
	 * @return
	 */
    Map<String, Object> fetchBiddingList(BiddingMain obj);

}
