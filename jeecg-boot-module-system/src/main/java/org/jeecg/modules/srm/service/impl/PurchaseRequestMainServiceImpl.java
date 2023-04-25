package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.PurchaseRequestMain;
import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import org.jeecg.modules.srm.mapper.PurchaseRequestDetailMapper;
import org.jeecg.modules.srm.mapper.PurchaseRequestMainMapper;
import org.jeecg.modules.srm.service.IPurchaseRequestMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: purchase_request_main
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Service
public class PurchaseRequestMainServiceImpl extends ServiceImpl<PurchaseRequestMainMapper, PurchaseRequestMain> implements IPurchaseRequestMainService {

	@Autowired
	private PurchaseRequestMainMapper purchaseRequestMainMapper;
	@Autowired
	private PurchaseRequestDetailMapper purchaseRequestDetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(PurchaseRequestMain purchaseRequestMain, List<PurchaseRequestDetail> purchaseRequestDetailList) {
		purchaseRequestMainMapper.insert(purchaseRequestMain);
		if(purchaseRequestDetailList!=null && purchaseRequestDetailList.size()>0) {
			for(PurchaseRequestDetail entity:purchaseRequestDetailList) {
				//外键设置
				entity.setReqId(purchaseRequestMain.getId());
				purchaseRequestDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(PurchaseRequestMain purchaseRequestMain,List<PurchaseRequestDetail> purchaseRequestDetailList) {
		purchaseRequestMainMapper.updateById(purchaseRequestMain);
		
		//1.先删除子表数据
		purchaseRequestDetailMapper.deleteByMainId(purchaseRequestMain.getId());
		
		//2.子表数据重新插入
		if(purchaseRequestDetailList!=null && purchaseRequestDetailList.size()>0) {
			for(PurchaseRequestDetail entity:purchaseRequestDetailList) {
				//外键设置
				entity.setReqId(purchaseRequestMain.getId());
				purchaseRequestDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		purchaseRequestDetailMapper.deleteByMainId(id);
		purchaseRequestMainMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			purchaseRequestDetailMapper.deleteByMainId(id.toString());
			purchaseRequestMainMapper.deleteById(id);
		}
	}

	/**
	 * 需求
	 * @param contractId
	 * @return
	 */
	@Override
	public PurchaseRequestMain getPurchaseRequest(String contractId) {
		return baseMapper.getPurchaseRequest(contractId);
	}

}
