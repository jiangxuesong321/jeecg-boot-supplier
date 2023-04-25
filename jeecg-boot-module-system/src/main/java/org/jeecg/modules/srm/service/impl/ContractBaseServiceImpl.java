package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.ContractObjectMapper;
import org.jeecg.modules.srm.mapper.ContractPayStepMapper;
import org.jeecg.modules.srm.mapper.ContractTermsMapper;
import org.jeecg.modules.srm.mapper.ContractBaseMapper;
import org.jeecg.modules.srm.service.*;
import org.jeecg.modules.srm.vo.ContractObjectVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: 合同基本信息表
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
@Service
public class ContractBaseServiceImpl extends ServiceImpl<ContractBaseMapper, ContractBase> implements IContractBaseService {

	@Autowired
	private ContractBaseMapper contractBaseMapper;
	@Autowired
	private ContractObjectMapper contractObjectMapper;
	@Autowired
	private ContractPayStepMapper contractPayStepMapper;
	@Autowired
	private ContractTermsMapper contractTermsMapper;
	@Autowired
	private IContractPayStepService iContractPayStepService;
	@Autowired
	private IContractTermsService iContractTermsService;
	@Autowired
	private IApproveRecordService iApproveRecordService;
	@Autowired
	private IContractObjectQtyService iContractObjectQtyService;
	@Value("${jeecg.path.upload}")
	private String upLoadPath;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ContractBase contractBase, List<ContractObject> contractObjectList,List<ContractPayStep> contractPayStepList,List<ContractTerms> contractTermsList) {
		contractBaseMapper.insert(contractBase);
		if(contractObjectList!=null && contractObjectList.size()>0) {
			for(ContractObject entity:contractObjectList) {
				//外键设置
				entity.setContractId(contractBase.getId());
				contractObjectMapper.insert(entity);
			}
		}
		if(contractPayStepList!=null && contractPayStepList.size()>0) {
			for(ContractPayStep entity:contractPayStepList) {
				//外键设置
				entity.setContractId(contractBase.getId());
				contractPayStepMapper.insert(entity);
			}
		}
		if(contractTermsList!=null && contractTermsList.size()>0) {
			for(ContractTerms entity:contractTermsList) {
				//外键设置
				entity.setContractId(contractBase.getId());
				contractTermsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ContractBase contractBase,List<ContractObject> contractObjectList,List<ContractPayStep> contractPayStepList,List<ContractTerms> contractTermsList) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String username = sysUser.getUsername();
		String suppId = sysUser.getSupplierId();
		Date nowTime = new Date();

		contractBase.setUpdateTime(nowTime);
		contractBase.setUpdateUser(username);
		contractBaseMapper.updateById(contractBase);

		//审核原因
//		ApproveRecord record = new ApproveRecord();
//		record.setId(String.valueOf(IdWorker.getId()));
//		record.setBusinessId(contractBase.getId());
//		record.setApprover(username);
//		record.setApproveComment(contractBase.getApproveComment());
//		record.setDelFlag(CommonConstant.NO_READ_FLAG);
//		record.setCreateTime(nowTime);
//		record.setCreateUser(username);
//		record.setUpdateUser(username);
//		record.setUpdateTime(nowTime);
//		record.setType("contract_supp");
//		iApproveRecordService.save(record);
		
		//1.先删除子表数据
		UpdateWrapper<ContractPayStep> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag","1");
		updateWrapper.eq("contract_id",contractBase.getId());
		iContractPayStepService.update(updateWrapper);

		UpdateWrapper<ContractTerms> updateWrapper1 = new UpdateWrapper<>();
		updateWrapper1.set("del_flag","1");
		updateWrapper1.eq("contract_id",contractBase.getId());
		iContractTermsService.update(updateWrapper1);

		UpdateWrapper<ContractObjectQty> updateWrapper2 = new UpdateWrapper<>();
		updateWrapper2.set("del_flag","1");
		updateWrapper2.eq("contract_id",contractBase.getId());
		iContractObjectQtyService.update(updateWrapper2);

		List<ContractObjectQty> recordList = new ArrayList<>();
		if(contractObjectList != null && contractObjectList.size() > 0){
			for(ContractObject co : contractObjectList){
				List<ContractObjectQty> qtyList = co.getChildList();
				int i = 1;
				for(ContractObjectQty rc : qtyList){
					rc.setId(String.valueOf(IdWorker.getId()));
					rc.setRecordId(co.getId());
					rc.setSuppId(suppId);
					rc.setContractAmount(rc.getContractPrice().multiply(rc.getQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
					rc.setContractAmountTax(rc.getContractPriceTax().multiply(rc.getQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
					rc.setContractAmountLocal(rc.getContractPriceLocal().multiply(rc.getQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
					rc.setContractAmountTaxLocal(rc.getContractPriceTaxLocal().multiply(rc.getQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
					rc.setSort(i);
					rc.setDelFlag(CommonConstant.NO_READ_FLAG);
					rc.setCreateTime(nowTime);
					rc.setCreateUser(username);
					rc.setUpdateTime(nowTime);
					rc.setUpdateUser(username);
					rc.setProdId(co.getProdId());
					rc.setUnitId(co.getUnitId());
					i++;
					recordList.add(rc);
				}
			}
			iContractObjectQtyService.saveOrUpdateBatch(recordList);
		}


		if(contractPayStepList!=null && contractPayStepList.size()>0) {
			for(ContractPayStep entity:contractPayStepList) {
				//外键设置
				entity.setId(String.valueOf(IdWorker.getId()));
				entity.setContractId(contractBase.getId());
				entity.setDelFlag(CommonConstant.NO_READ_FLAG);
				entity.setCreateTime(nowTime);
				entity.setCreateUser(username);
				entity.setUpdateUser(username);
				entity.setUpdateTime(nowTime);
			}
			iContractPayStepService.saveBatch(contractPayStepList);
		}
		if(contractTermsList!=null && contractTermsList.size()>0) {
			for(ContractTerms entity:contractTermsList) {
				//外键设置
				entity.setId(String.valueOf(IdWorker.getId()));
				entity.setContractId(contractBase.getId());
				entity.setDelFlag(CommonConstant.NO_READ_FLAG);
				entity.setCreateTime(nowTime);
				entity.setCreateUser(username);
				entity.setUpdateUser(username);
				entity.setUpdateTime(nowTime);
			}
			iContractTermsService.saveBatch(contractTermsList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		contractObjectMapper.deleteByMainId(id);
		contractPayStepMapper.deleteByMainId(id);
		contractTermsMapper.deleteByMainId(id);
		contractBaseMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			contractObjectMapper.deleteByMainId(id.toString());
			contractPayStepMapper.deleteByMainId(id.toString());
			contractTermsMapper.deleteByMainId(id.toString());
			contractBaseMapper.deleteById(id);
		}
	}

	/**
	 * 合同执行率
	 * @param obj
	 * @return
	 */
	@Override
	public Map<String, Object> fetchContractList(ContractBase obj) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();

//		List<ContractBase> cbList = this.list(Wrappers.<ContractBase>query().lambda().
//				eq(ContractBase :: getDelFlag,CommonConstant.DEL_FLAG_0).
//				eq(ContractBase :: getContractSecondPartyId,suppId));
		//合同总额
		BigDecimal contractAmountTaxLocal = contractBaseMapper.fetchContractAmountRmb(suppId);
		//付款总额
		BigDecimal payAmountTaxLocal = contractBaseMapper.fetchPayAmountRmb(suppId);
		if(payAmountTaxLocal == null){
			payAmountTaxLocal = BigDecimal.ZERO;
		}
		//进行中合同 = 未完全付款
		Integer onGoingNum = contractBaseMapper.fetchOnGoingContract(suppId);
		//已完成合同 - 已完成付款
		Integer completeNum = contractBaseMapper.fetchCompleteContract(suppId);

		Map<String,Object> map = new HashMap<>();
		map.put("onGoingNum",onGoingNum == null ? BigDecimal.ZERO:onGoingNum);
		map.put("completeNum",completeNum == null ? BigDecimal.ZERO:completeNum);

		if(contractAmountTaxLocal != null){
			BigDecimal ratio = payAmountTaxLocal.divide(contractAmountTaxLocal,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			map.put("ratio",ratio);
		}else{
			map.put("ratio",0);
		}
		return map;
	}

	/**
	 * 合同付款进度
	 * @param obj
	 * @return
	 */
	@Override
	public List<Map<String, Object>> fetchPayList(ContractBase obj) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();
		obj.setContractSecondPartyId(suppId);
		List<Map<String,Object>> map = baseMapper.fetchPayList(obj);
		return map;
	}

	/**
	 * 设备数量
	 * @param obj
	 * @return
	 */
	@Override
	public List<Map<String, Object>> fetchEqpList(BasMaterial obj) {
		return baseMapper.fetchEqpList(obj);
	}

	/**
	 * 合同报表
	 * @param page
	 * @param contractBase
	 * @return
	 */
	@Override
	public IPage<ContractObjectVo> fetchContractReport(Page<ContractBase> page, ContractBase contractBase) {
		return baseMapper.fetchContractReport(page,contractBase);
	}

	/**
	 * 合同报表
	 * @param request
	 * @param contractBase
	 * @param clazz
	 * @param title
	 * @return
	 */
	@Override
	public ModelAndView exportContractReport(HttpServletRequest request, ContractBase contractBase, Class<ContractObjectVo> clazz, String title) {
		List<ContractObjectVo> exportList = baseMapper.exportContractReport(contractBase);
		for(ContractObjectVo co : exportList){
			if(StringUtils.isNotEmpty(co.getPayMethod())){
				String payMethod = co.getPayMethod();
				payMethod = payMethod.replaceAll(";","\r\n");
				co.setPayMethod(payMethod);
			}
			if(StringUtils.isNotEmpty(co.getPayRate())){
				String payRate = co.getPayRate();
				payRate = payRate.replaceAll(";","\r\n");
				co.setPayRate(payRate);
			}
			if(StringUtils.isNotEmpty(co.getInvoiceAmount())){
				String invoiceAmount = co.getInvoiceAmount();
				invoiceAmount = invoiceAmount.replaceAll(";","\r\n");
				co.setInvoiceAmount(invoiceAmount);
			}
			if(StringUtils.isNotEmpty(co.getInvoiceNo())){
				String invoiceNo = co.getInvoiceNo();
				invoiceNo = invoiceNo.replaceAll(";","\r\n");
				co.setInvoiceNo(invoiceNo);
			}
		}


		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		//此处设置的filename无效 ,前端会重更新设置一下
		mv.addObject(NormalExcelConstants.FILE_NAME, title);
		mv.addObject(NormalExcelConstants.CLASS, clazz);
		//update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
		ExportParams exportParams=new ExportParams(title, "导出人:" + sysUser.getRealname(), title);
		exportParams.setImageBasePath(upLoadPath);
		//update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
		mv.addObject(NormalExcelConstants.PARAMS,exportParams);
		mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		return mv;
	}

	/**
	 * 合同、付款、发票总额
	 * @param supplierId
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> fetchAmount(String supplierId) {
		//合同总额
		List<Map<String,Object>> contract = baseMapper.fetchContractAmount(supplierId);
		//付款总额
		List<Map<String,Object>> pay = baseMapper.fetchPayAmount(supplierId);
		//开票总额
		List<Map<String,Object>> invoice = baseMapper.fetchInvoiceAmount(supplierId);
		Map<String,BigDecimal> map = new HashMap<>();
		map.put("contractAmountRmb",BigDecimal.ZERO);
		map.put("contractAmountUsd",BigDecimal.ZERO);
		map.put("contractAmountJpy",BigDecimal.ZERO);
		map.put("contractAmountEur",BigDecimal.ZERO);

		map.put("payAmountRmb",BigDecimal.ZERO);
		map.put("payAmountUsd",BigDecimal.ZERO);
		map.put("payAmountJpy",BigDecimal.ZERO);
		map.put("payAmountEur",BigDecimal.ZERO);

		map.put("invoiceAmountRmb",BigDecimal.ZERO);
		map.put("invoiceAmountUsd",BigDecimal.ZERO);
		map.put("invoiceAmountJpy",BigDecimal.ZERO);
		map.put("invoiceAmountEur",BigDecimal.ZERO);
		for(Map<String,Object> c : contract){
			String currency = (String) c.get("currency");
			BigDecimal amount = (BigDecimal)c.get("amount");
			if("RMB".equals(currency)){
				map.put("contractAmountRmb",amount);
			}else if("USD".equals(currency)){
				map.put("contractAmountUsd",amount);
			}else if("JPY".equals(currency)){
				map.put("contractAmountJpy",amount);
			}else if("EUR".equals(currency)){
				map.put("contractAmountEur",amount);
			}
		}
		for(Map<String,Object> p : pay){
			String currency = (String)p.get("currency");
			BigDecimal amount = (BigDecimal)p.get("amount");
			if("RMB".equals(currency)){
				map.put("payAmountRmb",amount);
			}else if("USD".equals(currency)){
				map.put("payAmountUsd",amount);
			}else if("JPY".equals(currency)){
				map.put("payAmountJpy",amount);
			}else if("EUR".equals(currency)){
				map.put("payAmountEur",amount);
			}
		}
		for(Map<String,Object> i : invoice){
			String currency = (String)i.get("currency");
			BigDecimal amount = (BigDecimal)i.get("amount");
			if("RMB".equals(currency)){
				map.put("invoiceAmountRmb",amount);
			}else if("USD".equals(currency)){
				map.put("invoiceAmountUsd",amount);
			}else if("JPY".equals(currency)){
				map.put("invoiceAmountJpy",amount);
			}else if("EUR".equals(currency)){
				map.put("invoiceAmountEur",amount);
			}
		}
		return map;
	}

}
