package org.jeecg.modules.srm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.PurPayApplyDetailMapper;
import org.jeecg.modules.srm.mapper.PurPayApplyMapper;
import org.jeecg.modules.srm.service.*;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Handler;

/**
 * @Description: 付款申请
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Service
public class PurPayApplyServiceImpl extends ServiceImpl<PurPayApplyMapper, PurPayApply> implements IPurPayApplyService {

	@Autowired
	private PurPayApplyMapper purPayApplyMapper;
	@Autowired
	private PurPayApplyDetailMapper purPayApplyDetailMapper;
	@Autowired
	private IPurPayApplyDetailService iPurPayApplyDetailService;
	@Autowired
	private IContractBaseService iContractBaseService;
	@Autowired
	private IContractObjectQtyService iContractObjectQtyService;
	@Autowired
	private IPayToInvoiceService iPayToInvoiceService;
	@Autowired
	private IPurchasePayInoviceService iPurchasePayInoviceService;
	@Autowired
	private IPurchaseApplyInvoiceService iPurchaseApplyInvoiceService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(PurPayApply purPayApply, List<PurPayApplyDetail> purPayApplyDetailList, List<PurchaseApplyInvoice> invoiceList) throws Exception {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String username = loginUser.getUsername();
		String suppId = loginUser.getSupplierId();
		Date nowTime = new Date();

		JSONObject formData = new JSONObject();
		formData.put("prefix", "PAC");
		String code = (String) FillRuleUtil.executeRule("pay_apply_code", formData);


		String id = String.valueOf(IdWorker.getId());
		if(StringUtils.isNotEmpty(purPayApply.getId())){
			id = purPayApply.getId();
		}else{
			purPayApply.setId(id);
			purPayApply.setCreateTime(nowTime);
			purPayApply.setCreateUser(username);
			purPayApply.setApplyCode(code);
			purPayApply.setApplyTime(nowTime);
			purPayApply.setSuppId(suppId);
		}
		purPayApply.setApplyStatus("10");
		purPayApply.setUpdateTime(nowTime);
		purPayApply.setUpdateUser(username);
		purPayApply.setDelFlag(CommonConstant.NO_READ_FLAG);
		purPayApply.setExchangeRate(purPayApply.getExchangeRate());
		this.saveOrUpdate(purPayApply);

		//删除明细
		UpdateWrapper<PurPayApplyDetail> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.ACT_SYNC_1);
		updateWrapper.eq("apply_id",id);
		iPurPayApplyDetailService.update(updateWrapper);

//		List<PayToInvoice> existList = iPayToInvoiceService.list(Wrappers.<PayToInvoice>query().lambda().eq(PayToInvoice :: getPayApplyId,id));
//		//释放发票使用标志
//		List<String> ids = new ArrayList<>();
//		for(PayToInvoice pti : existList){
//			ids.add(pti.getInvoiceId());
//		}
//		if(ids != null && ids.size() > 0){
//			UpdateWrapper<PurchasePayInovice> updateWrapper1 = new UpdateWrapper<>();
//			updateWrapper1.set("is_used","0");
//			updateWrapper1.in("id",ids);
//			iPurchasePayInoviceService.update(updateWrapper1);
//		}
//
//		//删除发票与付款记录表
//		iPayToInvoiceService.remove(Wrappers.<PayToInvoice>query().lambda().eq(PayToInvoice :: getPayApplyId,id));
//
//		//生成发票与付款的关系
//		String invoiceIds = purPayApply.getInvoiceIds();
//		List<PayToInvoice> invoiceList = new ArrayList<>();
//		ids = new ArrayList<>();
//		if (StringUtils.isNotEmpty(invoiceIds)) {
//			for(String invoiceId : invoiceIds.split(",")){
//				PayToInvoice pti = new PayToInvoice();
//				pti.setId(String.valueOf(IdWorker.getId()));
//				pti.setPayApplyId(id);
//				pti.setInvoiceId(invoiceId);
//				invoiceList.add(pti);
//
//				ids.add(invoiceId);
//			}
//			iPayToInvoiceService.saveBatch(invoiceList);
//
//			UpdateWrapper<PurchasePayInovice> updateWrapper1 = new UpdateWrapper<>();
//			updateWrapper1.set("is_used","1");
//			updateWrapper1.in("id",ids);
//			iPurchasePayInoviceService.update(updateWrapper1);
//		}

		//更新已用发票金额
		baseMapper.updateInvoiceAmount(purPayApply.getId());

		List<String> ids = new ArrayList<>();
		for(PurchaseApplyInvoice pai : invoiceList){
			ids.add(pai.getInvoiceId());
		}
		List<PurchasePayInovice> inoviceList = new ArrayList<>();
		if(ids != null && ids.size() > 0){
			inoviceList = iPurchasePayInoviceService.listByIds(ids);
		}
		iPurchaseApplyInvoiceService.remove(Wrappers.<PurchaseApplyInvoice>query().lambda().eq(PurchaseApplyInvoice :: getApplyId,purPayApply.getId()));

		for(PurchaseApplyInvoice pai : invoiceList){
			pai.setId(String.valueOf(IdWorker.getId()));
			pai.setApplyId(purPayApply.getId());
			pai.setDelFlag(CommonConstant.NO_READ_FLAG);
			pai.setCreateTime(nowTime);
			pai.setCreateBy(username);
			pai.setUpdateTime(nowTime);
			pai.setUpdateBy(username);

			for(PurchasePayInovice ppi : inoviceList){
				if(pai.getInvoiceId().equals(ppi.getId())){
					ppi.setHasInvoiceAmount(ppi.getHasInvoiceAmount().add(pai.getInvoiceAmount()));
					ppi.setHasInvoiceAmountTax(ppi.getHasInvoiceAmountTax().add(pai.getInvoiceAmountTax()));
					ppi.setHasInvoiceAmountLocal(ppi.getHasInvoiceAmountLocal().add(pai.getInvoiceAmountLocal()));
					ppi.setHasInvoiceAmountTaxLocal(ppi.getHasInvoiceAmountTaxLocal().add(pai.getInvoiceAmountTaxLocal()));
					if(ppi.getHasInvoiceAmountTax().compareTo(ppi.getInvoiceAmountTax()) == 0){
						ppi.setIsUsed("1");
					}else {
						ppi.setIsUsed("0");
					}
					break;
				}
			}
		}
		if(invoiceList != null && invoiceList.size() > 0){
			iPurchaseApplyInvoiceService.saveBatch(invoiceList);
			iPurchasePayInoviceService.updateBatchById(inoviceList);
		}


//		List<String> recordIds = new ArrayList<>();
		List<ContractObjectQty> objList = new ArrayList<>();
		for(PurPayApplyDetail pd : purPayApplyDetailList){
			pd.setId(String.valueOf(IdWorker.getId()));
			pd.setApplyId(id);
			pd.setPurchaseOrderId(purPayApply.getContractId());
			pd.setDelFlag(CommonConstant.NO_READ_FLAG);
			pd.setPayRate(pd.getPayRate());
			pd.setPayAmount(pd.getContractAmountTaxLocal());
			pd.setCreateTime(nowTime);
			pd.setCreateUser(username);
			pd.setUpdateTime(nowTime);
			pd.setCreateUser(username);

			//更新付款金额
			ContractObjectQty coq = iContractObjectQtyService.getById(pd.getBillDetailId());
			coq.setPayRate(coq.getPayRate().add(pd.getContractAmountTax()));
			objList.add(coq);
//			recordIds.add(pd.getBillDetailId());

		}
		iPurPayApplyDetailService.saveOrUpdateBatch(purPayApplyDetailList);
		iContractObjectQtyService.updateBatchById(objList);
		//更新付款金额
//		if(recordIds != null && recordIds.size() > 0){
//			List<ContractObjectQty> objList = iContractObjectQtyService.listByIds(recordIds);
//			for(ContractObjectQty co : objList){
//				//最大比例值
//				co.setPayRate((co.getPayRate().add(purPayApply.getPayRate())));
//				//判断最大值
//				if(co.getPayRate().compareTo(new BigDecimal(100)) == 1){
//					throw new Exception("存在明细超出最大比例");
//				}
//
//			}
//			iContractObjectQtyService.updateBatchById(objList);
//		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(PurPayApply purPayApply,List<PurPayApplyDetail> purPayApplyDetailList, List<PurchaseApplyInvoice> invoiceList) throws Exception {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String username = loginUser.getUsername();
		String suppId = loginUser.getSupplierId();
		Date nowTime = new Date();

		JSONObject formData = new JSONObject();
		formData.put("prefix", "PAC");
		String code = (String) FillRuleUtil.executeRule("pay_apply_code", formData);


		String id = String.valueOf(IdWorker.getId());
		BigDecimal payRate = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(purPayApply.getId())){
			id = purPayApply.getId();
			PurPayApply ppa = this.getById(id);
			payRate = ppa.getPayRate();
		}else{
			purPayApply.setId(id);
			purPayApply.setCreateTime(nowTime);
			purPayApply.setCreateUser(username);
			purPayApply.setApplyCode(code);
			purPayApply.setApplyTime(nowTime);
			purPayApply.setSuppId(suppId);
		}
		purPayApply.setApplyStatus("10");
		purPayApply.setUpdateTime(nowTime);
		purPayApply.setUpdateUser(username);
		purPayApply.setDelFlag(CommonConstant.NO_READ_FLAG);
//		purPayApply.setExchangeRate(cb.getContractExchangeRate());
		this.saveOrUpdate(purPayApply);

		//查询旧明细
		List<PurPayApplyDetail> oldList = iPurPayApplyDetailService.list(Wrappers.<PurPayApplyDetail>query().lambda().
				eq(PurPayApplyDetail :: getApplyId,id).
				eq(PurPayApplyDetail :: getDelFlag,CommonConstant.DEL_FLAG_0));

		//删除明细
		UpdateWrapper<PurPayApplyDetail> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.ACT_SYNC_1);
		updateWrapper.eq("apply_id",id);
		iPurPayApplyDetailService.update(updateWrapper);



		//更新已用发票金额
		baseMapper.updateInvoiceAmount(purPayApply.getId());

		List<String> ids = new ArrayList<>();
		for(PurchaseApplyInvoice pai : invoiceList){
			ids.add(pai.getInvoiceId());
		}
		List<PurchasePayInovice> inoviceList = new ArrayList<>();
		if(ids != null && ids.size() > 0){
			inoviceList = iPurchasePayInoviceService.listByIds(ids);
		}
		iPurchaseApplyInvoiceService.remove(Wrappers.<PurchaseApplyInvoice>query().lambda().eq(PurchaseApplyInvoice :: getApplyId,purPayApply.getId()));
		for(PurchaseApplyInvoice pai : invoiceList){
			pai.setId(String.valueOf(IdWorker.getId()));
			pai.setApplyId(purPayApply.getId());
			pai.setDelFlag(CommonConstant.NO_READ_FLAG);
			pai.setCreateTime(nowTime);
			pai.setCreateBy(username);
			pai.setUpdateTime(nowTime);
			pai.setUpdateBy(username);


			for(PurchasePayInovice ppi : inoviceList){
				if(pai.getInvoiceId().equals(ppi.getId())){
					ppi.setHasInvoiceAmount(ppi.getHasInvoiceAmount().add(pai.getInvoiceAmount()));
					ppi.setHasInvoiceAmountTax(ppi.getHasInvoiceAmountTax().add(pai.getInvoiceAmountTax()));
					ppi.setHasInvoiceAmountLocal(ppi.getHasInvoiceAmountLocal().add(pai.getInvoiceAmountLocal()));
					ppi.setHasInvoiceAmountTaxLocal(ppi.getHasInvoiceAmountTaxLocal().add(pai.getInvoiceAmountTaxLocal()));
					if(ppi.getHasInvoiceAmountTax().compareTo(ppi.getInvoiceAmountTax()) == 0){
						ppi.setIsUsed("1");
					}else {
						ppi.setIsUsed("0");
					}
					break;
				}
			}
		}
		if(invoiceList != null && invoiceList.size() > 0){
			iPurchaseApplyInvoiceService.saveBatch(invoiceList);
			iPurchasePayInoviceService.updateBatchById(inoviceList);
		}


		List<ContractObjectQty> objList = new ArrayList<>();
		for(PurPayApplyDetail pd : purPayApplyDetailList){
			//更新付款金额
			ContractObjectQty coq = iContractObjectQtyService.getById(pd.getBillDetailId());

			pd.setId(String.valueOf(IdWorker.getId()));
			pd.setApplyId(id);
			pd.setPurchaseOrderId(purPayApply.getContractId());
			pd.setDelFlag(CommonConstant.NO_READ_FLAG);
			pd.setPayRate(pd.getPayRate());
			pd.setPayAmount(pd.getContractAmountTaxLocal());
			pd.setCreateTime(nowTime);
			pd.setCreateUser(username);
			pd.setUpdateTime(nowTime);
			pd.setCreateUser(username);

			BigDecimal subctuct = BigDecimal.ZERO;
			for(PurPayApplyDetail old : oldList){
				if(old.getBillDetailId().equals(pd.getBillDetailId())){
					subctuct = old.getContractAmountTax();
					break;
				}
			}
			coq.setPayRate(coq.getPayRate().add(pd.getContractAmountTax()).subtract(subctuct));
			objList.add(coq);

		}
		iPurPayApplyDetailService.saveOrUpdateBatch(purPayApplyDetailList);
		iContractObjectQtyService.updateBatchById(objList);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		List<PurPayApplyDetail> detailList = iPurPayApplyDetailService.list(Wrappers.<PurPayApplyDetail>query().lambda().
				eq(PurPayApplyDetail :: getDelFlag,CommonConstant.DEL_FLAG_0).
				eq(PurPayApplyDetail :: getApplyId,id));
		List<String> ids = new ArrayList<>();
		for(PurPayApplyDetail ppad : detailList){
			ids.add(ppad.getBillDetailId());
		}
		if(ids != null && ids.size() > 0){
			//付款比例还原
			List<ContractObjectQty> coList = iContractObjectQtyService.listByIds(ids);
			for (ContractObjectQty co : coList){
				for(PurPayApplyDetail pd : detailList){
					if(co.getId().equals(pd.getBillDetailId())){
						co.setPayRate(co.getPayRate().subtract(pd.getContractAmountTax()));
						break;
					}
				}
			}
			iContractObjectQtyService.updateBatchById(coList);
		}


		UpdateWrapper<PurPayApply> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.DEL_FLAG_1);
		updateWrapper.eq("id",id);
		this.update(updateWrapper);

		UpdateWrapper<PurPayApplyDetail> updateWrapper1 = new UpdateWrapper<>();
		updateWrapper1.set("del_flag",CommonConstant.DEL_FLAG_1);
		updateWrapper1.eq("apply_id",id);
		iPurPayApplyDetailService.update(updateWrapper1);

		List<PurchaseApplyInvoice> invoiceList = iPurchaseApplyInvoiceService.list(Wrappers.<PurchaseApplyInvoice>query().lambda().eq(PurchaseApplyInvoice :: getApplyId,id));
		List<String> ins = new ArrayList<>();
		if(invoiceList != null && invoiceList.size() > 0){
			for(PurchaseApplyInvoice pai : invoiceList){
				ins.add(pai.getInvoiceId());
			}
			List<PurchasePayInovice> payInoviceList = iPurchasePayInoviceService.listByIds(ins);
			for(PurchasePayInovice ppi : payInoviceList){
				ppi.setIsUsed("0");

				for(PurchaseApplyInvoice pai : invoiceList){
					if(ppi.getId().equals(pai.getInvoiceId())){
						ppi.setHasInvoiceAmount(ppi.getHasInvoiceAmount().subtract(pai.getInvoiceAmount()));
						ppi.setHasInvoiceAmountTax(ppi.getHasInvoiceAmountTax().subtract(pai.getInvoiceAmountTax()));
						ppi.setHasInvoiceAmountLocal(ppi.getHasInvoiceAmountLocal().subtract(pai.getInvoiceAmountLocal()));
						ppi.setHasInvoiceAmountTaxLocal(ppi.getHasInvoiceAmountTaxLocal().subtract(pai.getInvoiceAmountTaxLocal()));
					}
				}
			}
			iPurchasePayInoviceService.updateBatchById(payInoviceList);
			iPurchaseApplyInvoiceService.remove(Wrappers.<PurchaseApplyInvoice>query().lambda().eq(PurchaseApplyInvoice :: getApplyId,id));
		}



//		List<PayToInvoice> existList = iPayToInvoiceService.list(Wrappers.<PayToInvoice>query().lambda().eq(PayToInvoice :: getPayApplyId,id));
//		//释放发票使用标志
//		ids = new ArrayList<>();
//		for(PayToInvoice pti : existList){
//			ids.add(pti.getInvoiceId());
//		}
//		if(ids != null && ids.size() > 0){
//			UpdateWrapper<PurchasePayInovice> updateWrapper2 = new UpdateWrapper<>();
//			updateWrapper2.set("is_used","0");
//			updateWrapper2.in("id",ids);
//			iPurchasePayInoviceService.update(updateWrapper2);
//		}
//
//		//删除发票与付款记录表
//		iPayToInvoiceService.remove(Wrappers.<PayToInvoice>query().lambda().eq(PayToInvoice :: getPayApplyId,id));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			purPayApplyDetailMapper.deleteByMainId(id.toString());
			purPayApplyMapper.deleteById(id);
		}
	}

	/**
	 * 草稿
	 * @param purPayApply
	 * @param purPayApplyDetailList
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void draft(PurPayApply purPayApply, List<PurPayApplyDetail> purPayApplyDetailList,List<PurchaseApplyInvoice> invoiceList) throws Exception {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String username = loginUser.getUsername();
		String suppId = loginUser.getSupplierId();
		Date nowTime = new Date();

		JSONObject formData = new JSONObject();
		formData.put("prefix", "PAC");
		String code = (String) FillRuleUtil.executeRule("pay_apply_code", formData);

		ContractBase cb = iContractBaseService.getById(purPayApply.getContractId());

		String id = String.valueOf(IdWorker.getId());
		BigDecimal payRate = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(purPayApply.getId())){
			id = purPayApply.getId();
			PurPayApply ppa = this.getById(id);
			payRate = ppa.getPayRate();
		}else{
			purPayApply.setId(id);
			purPayApply.setCreateTime(nowTime);
			purPayApply.setCreateUser(username);
			purPayApply.setApplyCode(code);
			purPayApply.setApplyTime(nowTime);
			purPayApply.setSuppId(suppId);
		}
		purPayApply.setApplyStatus("00");
		purPayApply.setUpdateTime(nowTime);
		purPayApply.setUpdateUser(username);
		purPayApply.setDelFlag(CommonConstant.NO_READ_FLAG);
		purPayApply.setExchangeRate(cb.getContractExchangeRate());
		this.saveOrUpdate(purPayApply);

		//查询旧明细
		List<PurPayApplyDetail> oldList = iPurPayApplyDetailService.list(Wrappers.<PurPayApplyDetail>query().lambda().
				eq(PurPayApplyDetail :: getApplyId,id).
				eq(PurPayApplyDetail :: getDelFlag,CommonConstant.DEL_FLAG_0));

		//删除明细
		UpdateWrapper<PurPayApplyDetail> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.ACT_SYNC_1);
		updateWrapper.eq("apply_id",id);
		iPurPayApplyDetailService.update(updateWrapper);

		//更新已用发票金额
		baseMapper.updateInvoiceAmount(purPayApply.getId());

		List<String> ids = new ArrayList<>();
		for(PurchaseApplyInvoice pai : invoiceList){
			ids.add(pai.getInvoiceId());
		}
		List<PurchasePayInovice> inoviceList = new ArrayList<>();
		if(ids != null && ids.size() > 0){
			inoviceList = iPurchasePayInoviceService.listByIds(ids);
		}
		//删除发票关联明细
		iPurchaseApplyInvoiceService.remove(Wrappers.<PurchaseApplyInvoice>query().lambda().eq(PurchaseApplyInvoice :: getApplyId,purPayApply.getId()));
		for(PurchaseApplyInvoice pai : invoiceList){
			pai.setId(String.valueOf(IdWorker.getId()));
			pai.setApplyId(purPayApply.getId());
			pai.setDelFlag(CommonConstant.NO_READ_FLAG);
			pai.setCreateTime(nowTime);
			pai.setCreateBy(username);
			pai.setUpdateTime(nowTime);
			pai.setUpdateBy(username);


			for(PurchasePayInovice ppi : inoviceList){
				if(pai.getInvoiceId().equals(ppi.getId())){
					ppi.setHasInvoiceAmount(ppi.getHasInvoiceAmount().add(pai.getInvoiceAmount()));
					ppi.setHasInvoiceAmountTax(ppi.getHasInvoiceAmountTax().add(pai.getInvoiceAmountTax()));
					ppi.setHasInvoiceAmountLocal(ppi.getHasInvoiceAmountLocal().add(pai.getInvoiceAmountLocal()));
					ppi.setHasInvoiceAmountTaxLocal(ppi.getHasInvoiceAmountTaxLocal().add(pai.getInvoiceAmountTaxLocal()));
					if(ppi.getHasInvoiceAmountTax().compareTo(ppi.getInvoiceAmountTax()) == 0){
						ppi.setIsUsed("1");
					}else {
						ppi.setIsUsed("0");
					}
					break;
				}
			}
		}
		if(invoiceList != null && invoiceList.size() > 0){
			iPurchaseApplyInvoiceService.saveBatch(invoiceList);
			iPurchasePayInoviceService.updateBatchById(inoviceList);
		}

		List<ContractObjectQty> objList = new ArrayList<>();
		for(PurPayApplyDetail pd : purPayApplyDetailList){
			//更新付款金额
			ContractObjectQty coq = iContractObjectQtyService.getById(pd.getBillDetailId());

			pd.setId(String.valueOf(IdWorker.getId()));
			pd.setApplyId(id);
			pd.setPurchaseOrderId(purPayApply.getContractId());
			pd.setDelFlag(CommonConstant.NO_READ_FLAG);
			pd.setPayRate(pd.getPayRate());
			pd.setPayAmount(pd.getContractAmountTaxLocal());
			pd.setCreateTime(nowTime);
			pd.setCreateUser(username);
			pd.setUpdateTime(nowTime);
			pd.setCreateUser(username);

			BigDecimal subctuct = BigDecimal.ZERO;
			for(PurPayApplyDetail old : oldList){
				if(old.getBillDetailId().equals(pd.getBillDetailId())){
					subctuct = old.getContractAmountTax();
					break;
				}
			}
			coq.setPayRate(coq.getPayRate().add(pd.getContractAmountTax()).subtract(subctuct));
			objList.add(coq);

		}
		iPurPayApplyDetailService.saveOrUpdateBatch(purPayApplyDetailList);
		iContractObjectQtyService.updateBatchById(objList);
	}

}
