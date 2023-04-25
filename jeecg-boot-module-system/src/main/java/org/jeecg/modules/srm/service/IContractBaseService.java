package org.jeecg.modules.srm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srm.vo.ContractObjectVo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同基本信息表
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
public interface IContractBaseService extends IService<ContractBase> {

	/**
	 * 添加一对多
	 *
	 * @param contractBase
	 * @param contractObjectList
	 * @param contractPayStepList
	 * @param contractTermsList
	 */
	public void saveMain(ContractBase contractBase,List<ContractObject> contractObjectList,List<ContractPayStep> contractPayStepList,List<ContractTerms> contractTermsList) ;
	
	/**
	 * 修改一对多
	 *
   * @param contractBase
   * @param contractObjectList
   * @param contractPayStepList
   * @param contractTermsList
	 */
	public void updateMain(ContractBase contractBase,List<ContractObject> contractObjectList,List<ContractPayStep> contractPayStepList,List<ContractTerms> contractTermsList);
	
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
	 * 合同执行率
	 * @param obj
	 * @return
	 */
    Map<String, Object> fetchContractList(ContractBase obj);

	/**
	 * 合同付款进度
	 * @param obj
	 * @return
	 */
	List<Map<String,Object>> fetchPayList(ContractBase obj);

	/**
	 * 设备数量
	 * @param obj
	 * @return
	 */
	List<Map<String, Object>> fetchEqpList(BasMaterial obj);

	/**
	 * 合同报表
	 * @param page
	 * @param contractBase
	 * @return
	 */
    IPage<ContractObjectVo> fetchContractReport(Page<ContractBase> page, ContractBase contractBase);

	/**
	 * 合同报表
	 * @param request
	 * @param contractBase
	 * @param contractObjectVoClass
	 * @param title
	 * @return
	 */
	ModelAndView exportContractReport(HttpServletRequest request, ContractBase contractBase, Class<ContractObjectVo> contractObjectVoClass, String title);

	/**
	 * 合同总额、付款总额、开票总额
	 * @param supplierId
	 * @return
	 */
    Map<String, BigDecimal> fetchAmount(String supplierId);
}
