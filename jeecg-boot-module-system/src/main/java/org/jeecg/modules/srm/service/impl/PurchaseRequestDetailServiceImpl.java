package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import org.jeecg.modules.srm.mapper.PurchaseRequestDetailMapper;
import org.jeecg.modules.srm.service.IPurchaseRequestDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: purchase_request_detail
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Service
public class PurchaseRequestDetailServiceImpl extends ServiceImpl<PurchaseRequestDetailMapper, PurchaseRequestDetail> implements IPurchaseRequestDetailService {
	
	@Autowired
	private PurchaseRequestDetailMapper purchaseRequestDetailMapper;
	
	@Override
	public List<PurchaseRequestDetail> selectByMainId(String mainId) {
		return purchaseRequestDetailMapper.selectByMainId(mainId);
	}
}
