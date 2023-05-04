package org.jeecg.modules.srm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.mapper.BasSupplierContactMapper;
import org.jeecg.modules.srm.mapper.BasSupplierQualificationMapper;
import org.jeecg.modules.srm.mapper.BasSupplierMapper;
import org.jeecg.modules.srm.service.*;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 供应商基本信息
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Service
public class BasSupplierServiceImpl extends ServiceImpl<BasSupplierMapper, BasSupplier> implements IBasSupplierService {

	@Autowired
	private BasSupplierMapper basSupplierMapper;
	@Autowired
	private BasSupplierContactMapper basSupplierContactMapper;
	@Autowired
	private BasSupplierQualificationMapper basSupplierQualificationMapper;
	@Autowired
	private IBasSupplierContactService iBasSupplierContactService;
	@Autowired
	private IBasSupplierQualificationService iBasSupplierQualificationService;
	@Autowired
	private IBasSupplierBankService iBasSupplierBankService;
	@Autowired
	private IBasSupplierFastService iBasSupplierFastService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(BasSupplier basSupplier, List<BasSupplierContact> basSupplierContactList,
						 List<BasSupplierQualification> basSupplierQualificationList,
						 List<BasSupplierBank> basSupplierBankList,
						 List<BasSupplierFast> basSupplierFastList) {
		JSONObject formData = new JSONObject();
		formData.put("prefix", "S");
		String code = (String) FillRuleUtil.executeRule("supp_code", formData);

		basSupplier.setCode(code);
		basSupplier.setStatus("0");
		basSupplierMapper.insert(basSupplier);
		//2.子表数据重新插入
		if(basSupplierContactList!=null && basSupplierContactList.size()>0) {
			for(BasSupplierContact entity:basSupplierContactList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierContactService.saveBatch(basSupplierContactList);
		}
		if(basSupplierQualificationList!=null && basSupplierQualificationList.size()>0) {
			for(BasSupplierQualification entity:basSupplierQualificationList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierQualificationService.saveBatch(basSupplierQualificationList);
		}
		if(basSupplierBankList!=null && basSupplierBankList.size()>0) {
			for(BasSupplierBank entity:basSupplierBankList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierBankService.saveBatch(basSupplierBankList);
		}
		if(basSupplierFastList!=null && basSupplierFastList.size()>0) {
			for(BasSupplierFast entity:basSupplierFastList) {
				//外键设置
				entity.setSuppId(basSupplier.getId());
			}
			iBasSupplierFastService.saveBatch(basSupplierFastList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(BasSupplier basSupplier,List<BasSupplierContact> basSupplierContactList,
						   List<BasSupplierQualification> basSupplierQualificationList,
						   List<BasSupplierBank> basSupplierBankList,
						   List<BasSupplierFast> basSupplierFastList) {
		if("2".equals(basSupplier.getStatus())){
			basSupplier.setStatus("0");
		}
		basSupplier.setStatus("0");
		this.updateById(basSupplier);

		//1.先删除子表数据
		iBasSupplierContactService.deleteByMainId(basSupplier.getId());
		iBasSupplierQualificationService.deleteByMainId(basSupplier.getId());
		iBasSupplierBankService.deleteByMainId(basSupplier.getId());
		iBasSupplierFastService.remove(Wrappers.<BasSupplierFast>query().lambda().eq(BasSupplierFast :: getSuppId,basSupplier.getId()));

		//2.子表数据重新插入
		if(basSupplierContactList!=null && basSupplierContactList.size()>0) {
			for(BasSupplierContact entity:basSupplierContactList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierContactService.saveBatch(basSupplierContactList);
		}
		if(basSupplierQualificationList!=null && basSupplierQualificationList.size()>0) {
			for(BasSupplierQualification entity:basSupplierQualificationList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierQualificationService.saveBatch(basSupplierQualificationList);
		}
		if(basSupplierBankList!=null && basSupplierBankList.size()>0) {
			for(BasSupplierBank entity:basSupplierBankList) {
				//外键设置
				entity.setSupplierId(basSupplier.getId());
			}
			iBasSupplierBankService.saveBatch(basSupplierBankList);
		}
		if(basSupplierFastList!=null && basSupplierFastList.size()>0) {
			for(BasSupplierFast entity:basSupplierFastList) {
				//外键设置
				entity.setSuppId(basSupplier.getId());
			}
			iBasSupplierFastService.saveBatch(basSupplierFastList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		basSupplierContactMapper.deleteByMainId(id);
		basSupplierQualificationMapper.deleteByMainId(id);
		basSupplierMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			basSupplierContactMapper.deleteByMainId(id.toString());
			basSupplierQualificationMapper.deleteByMainId(id.toString());
			basSupplierMapper.deleteById(id);
		}
	}
	
}
