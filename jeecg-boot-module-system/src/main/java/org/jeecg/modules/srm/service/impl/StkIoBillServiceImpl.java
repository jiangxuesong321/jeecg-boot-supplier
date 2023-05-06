package org.jeecg.modules.srm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.StkIoBillEntryMapper;
import org.jeecg.modules.srm.mapper.StkIoBillMapper;
import org.jeecg.modules.srm.service.*;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 入库单
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Service
public class StkIoBillServiceImpl extends ServiceImpl<StkIoBillMapper, StkIoBill> implements IStkIoBillService {

	@Autowired
	private StkIoBillMapper stkIoBillMapper;
	@Autowired
	private StkIoBillEntryMapper stkIoBillEntryMapper;
	@Autowired
	private IBasSupplierService iBasSupplierService;
	@Autowired
	private IProjBaseService iProjBaseService;
	@Autowired
	private IStkIoBillEntryService iStkIoBillEntryService;
	@Autowired
	private IContractObjectQtyService iContractObjectQtyService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(StkIoBill stkIoBill, List<StkIoBillEntry> stkIoBillEntryList) {
		String id = String.valueOf(IdWorker.getId());
		Date nowTime = new Date();
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = loginUser.getSupplierId();
		//申请单号生成
		JSONObject formData = new JSONObject();
		formData.put("prefix", "GE");
		String code = (String) FillRuleUtil.executeRule("IO_CODE", formData);
		//供应商
		BasSupplier supp = iBasSupplierService.getById(suppId);
		//项目
//		ProjBase projBase = iProjBaseService.getById(stkIoBill.getProjectId());


		//发货单
		stkIoBill.setId(id);
		stkIoBill.setBillNo(code);
		stkIoBill.setSuppId(suppId);
		stkIoBill.setSuppName(supp.getName());
		stkIoBill.setCreateBy(loginUser.getUsername());
		stkIoBill.setCreateTime(nowTime);
		stkIoBill.setUpdateBy(loginUser.getUsername());
		stkIoBill.setUpdateTime(nowTime);
		stkIoBill.setDelFlag(CommonConstant.NO_READ_FLAG);
//		stkIoBill.setProjectName(projBase.getProjName());
		stkIoBill.setSendStatus("0");
		stkIoBill.setSendProcessId(String.valueOf(IdWorker.getId()));
		//合同明细
		List<String> ids = new ArrayList<>();

//		Date sendTime = stkIoBillEntryList.get(0).getSendTime();

		List<ContractObjectQty> qtyList = new ArrayList<>();
		//发货明细
		for(StkIoBillEntry sibe : stkIoBillEntryList){
			sibe.setId(String.valueOf(IdWorker.getId()));
			sibe.setMid(id);
			sibe.setBillNo(code);
			sibe.setSupplierId(suppId);
			sibe.setCreateBy(loginUser.getUsername());
			sibe.setCreateTime(nowTime);
			sibe.setUpdateBy(loginUser.getUsername());
			sibe.setUpdateTime(nowTime);
			sibe.setDelFlag(CommonConstant.NO_READ_FLAG);
			sibe.setOrderId(stkIoBill.getContractId());
			sibe.setOrderNumber(stkIoBill.getContractNumber());
			sibe.setProjectId(stkIoBill.getProjectId());

			ContractObjectQty coq = iContractObjectQtyService.getById(sibe.getOrderDetailId());
			coq.setToSendQty(coq.getToSendQty().add(sibe.getQty()));
			qtyList.add(coq);

			ids.add(sibe.getOrderDetailId());

//			long time1 = sibe.getSendTime().getTime();
//			long time2 = sendTime.getTime();
//			if(time1 > time2){
//				sendTime = sibe.getSendTime();
//			}

		}
		iStkIoBillEntryService.saveBatch(stkIoBillEntryList);
		iContractObjectQtyService.updateBatchById(qtyList);

		//获取明细最晚发货时间
//		stkIoBill.setSendTime(sendTime);
		this.save(stkIoBill);

//		UpdateWrapper<ContractObjectQty> updateWrapper = new UpdateWrapper<>();
//		updateWrapper.set("to_send_qty","1");
//		updateWrapper.in("id",ids);
//		iContractObjectQtyService.update(updateWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(StkIoBill stkIoBill,List<StkIoBillEntry> stkIoBillEntryList) {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = loginUser.getSupplierId();
		Date nowTime = new Date();

		//供应商
		BasSupplier supp = iBasSupplierService.getById(suppId);
		//项目
//		ProjBase projBase = iProjBaseService.getById(stkIoBill.getProjectId());

		stkIoBill.setSuppId(suppId);
		stkIoBill.setSuppName(supp.getName());
		stkIoBill.setUpdateBy(loginUser.getUsername());
		stkIoBill.setUpdateTime(nowTime);
		stkIoBill.setDelFlag(CommonConstant.NO_READ_FLAG);
//		stkIoBill.setProjectName(projBase.getProjName());
		stkIoBill.setSendStatus("0");
		stkIoBill.setSendProcessId(String.valueOf(IdWorker.getId()));


		//删除明细
		List<StkIoBillEntry> oldList = iStkIoBillEntryService.list(Wrappers.<StkIoBillEntry>query().lambda().
				eq(StkIoBillEntry :: getMid,stkIoBill.getId()).
				eq(StkIoBillEntry :: getDelFlag,CommonConstant.DEL_FLAG_0));
		List<String> oldIds = new ArrayList<>();
		for(StkIoBillEntry old : oldList){
			oldIds.add(old.getOrderDetailId());
		}
//		UpdateWrapper<ContractObjectQty> updateWrapper1 = new UpdateWrapper<>();
//		updateWrapper1.set("to_send_qty","0");
//		updateWrapper1.in("id",oldIds);
//		iContractObjectQtyService.update(updateWrapper1);
		List<ContractObjectQty> qtyList = iContractObjectQtyService.listByIds(oldIds);


		UpdateWrapper<StkIoBillEntry> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.ACT_SYNC_1);
		updateWrapper.eq("mid",stkIoBill.getId());
		iStkIoBillEntryService.update(updateWrapper);

		//合同明细
		List<String> ids = new ArrayList<>();
		//发货明细

//		Date sendTime = stkIoBillEntryList.get(0).getSendTime();

		for(StkIoBillEntry sibe : stkIoBillEntryList){
			sibe.setId(String.valueOf(IdWorker.getId()));
			sibe.setMid(stkIoBill.getId());
			sibe.setBillNo(stkIoBill.getBillNo());
			sibe.setSupplierId(suppId);
			sibe.setCreateBy(loginUser.getUsername());
			sibe.setCreateTime(nowTime);
			sibe.setUpdateBy(loginUser.getUsername());
			sibe.setUpdateTime(nowTime);
			sibe.setDelFlag(CommonConstant.NO_READ_FLAG);
			sibe.setOrderId(stkIoBill.getContractId());
			sibe.setOrderNumber(stkIoBill.getContractNumber());
			sibe.setProjectId(stkIoBill.getProjectId());

			ids.add(sibe.getOrderDetailId());

//			long time1 = sibe.getSendTime().getTime();
//			long time2 = sendTime.getTime();
//			if(time1 > time2){
//				sendTime = sibe.getSendTime();
//			}

			for(ContractObjectQty coq : qtyList){
				if(sibe.getOrderDetailId().equals(coq.getId())){
					coq.setToSendQty(coq.getToSendQty().add(sibe.getQty()));
					break;
				}
			}
		}
//		stkIoBill.setSendTime(sendTime);
		this.updateById(stkIoBill);
		iStkIoBillEntryService.saveBatch(stkIoBillEntryList);

		iContractObjectQtyService.updateBatchById(qtyList);

//		updateWrapper1 = new UpdateWrapper<>();
//		updateWrapper1.set("to_send_qty","1");
//		updateWrapper1.in("id",ids);
//		iContractObjectQtyService.update(updateWrapper1);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		stkIoBillEntryMapper.deleteByMainId(id);
		stkIoBillMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			stkIoBillEntryMapper.deleteByMainId(id.toString());
			stkIoBillMapper.deleteById(id);
		}
	}
	
}
