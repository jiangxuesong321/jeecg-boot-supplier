package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.ContractObjectMapper;
import org.jeecg.modules.srm.service.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 合同标的
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
@Service
public class ContractObjectServiceImpl extends ServiceImpl<ContractObjectMapper, ContractObject> implements IContractObjectService {
	
	@Autowired
	private ContractObjectMapper contractObjectMapper;
	@Autowired
	private IContractObjectQtyService iContractObjectQtyService;
	@Autowired
	private IInquirySupplierService inquirySupplierService;
	@Autowired
	private IBiddingRecordSupplierService iBiddingRecordSupplierService;
	@Autowired
	private IBiddingSupplierService iBiddingSupplierService;
	@Autowired
	private IContractObjectChildService iContractObjectChildService;
	
	@Override
	public List<ContractObject> selectByMainId(String mainId) {
		return contractObjectMapper.selectByMainId(mainId);
	}

	/**
	 * 合同
	 * @param contractBase
	 * @return
	 */
	@Override
	public List<ContractObject> getContractDetailList(ContractBase contractBase) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();

		List<ContractObject> pageList = baseMapper.getContractDetailList(contractBase);
		List<String> ids = new ArrayList<>();
		for(ContractObject co : pageList){
			ids.add(co.getId());
		}

		List<ContractObjectQty> qtyList = iContractObjectQtyService.list(Wrappers.<ContractObjectQty>query().lambda().
				eq(ContractObjectQty :: getDelFlag, CommonConstant.DEL_FLAG_0).
				in(ContractObjectQty :: getRecordId,ids).
				eq(ContractObjectQty :: getSuppId,suppId));

		for(ContractObject co : pageList){
			List<ContractObjectQty> childList = new ArrayList<>();
			for(ContractObjectQty child : qtyList){
				if(co.getId().equals(child.getRecordId())){
					child.setPrice(child.getContractPrice());
					child.setPriceTax(child.getContractPriceTax());
					child.setPriceLocal(child.getContractPriceLocal());
					child.setPriceTaxLocal(child.getContractPriceTaxLocal());
					child.setAmount(child.getContractAmount());
					child.setAmountTax(child.getContractAmountTax());
					child.setAmountLocal(child.getContractAmountLocal());
					child.setAmountTaxLocal(child.getContractAmountTaxLocal());
					child.setSpeType(child.getProdSpecType());
					childList.add(child);
				}
			}
			co.setChildList(childList);

			List<ContractObjectChild> objList = iContractObjectChildService.list(Wrappers.<ContractObjectChild>query().lambda().
					eq(ContractObjectChild :: getDelFlag,CommonConstant.DEL_FLAG_0).
					eq(ContractObjectChild :: getMainDetailId,co.getId()));
			co.setObjList(objList);
		}
		return pageList;
	}

	/**
	 * 合同明细
	 * @param page
	 * @param obj
	 * @return
	 */
	@Override
	public IPage<ContractObject> listByDetailList(Page<ContractObject> page, ContractObject obj) {
		return baseMapper.listByDetailList(page,obj);
	}

	/**
	 * 供应商中标率
	 * @param obj
	 * @return
	 */
	@Override
	public Map<String, Object> fetchBiddingList(BiddingMain obj) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();
		//询比价
		obj.setSuppId(suppId);
		List<InquirySupplier> iSuppList = inquirySupplierService.list(Wrappers.<InquirySupplier>query().lambda().
				eq(InquirySupplier :: getSupplierId,suppId).
				eq(InquirySupplier :: getDelFlag,CommonConstant.DEL_FLAG_0));
		//招投标
		List<BiddingRecordSupplier> bSuppList = iBiddingRecordSupplierService.fetchBiddingList(suppId);

		//总数量
		int totalNum = 0;
		if(iSuppList != null && iSuppList.size() > 0){
			totalNum = totalNum + iSuppList.size();
		}
		if(bSuppList != null && bSuppList.size() > 0){
			totalNum = totalNum + bSuppList.size();
		}
		//中标数量
		int biddingNum = 0;
		for(InquirySupplier is : iSuppList){
			if("1".equals(is.getIsRecommend())){
				biddingNum = biddingNum + 1;
			}
		}
		for(BiddingRecordSupplier bs : bSuppList){
			if("1".equals(bs.getIsRecommend())){
				biddingNum = biddingNum + 1;
			}
		}

		Map<String,Object> map = new HashMap<>();
		map.put("totalNum",totalNum);
		map.put("biddingNum",biddingNum);
		if(biddingNum == 0){
			map.put("ratio",0);
		}else{
			BigDecimal ratio = new BigDecimal(biddingNum).divide(new BigDecimal(totalNum),4,BigDecimal.ROUND_HALF_UP);
			map.put("ratio",ratio.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
		}


		return map;
	}

}
