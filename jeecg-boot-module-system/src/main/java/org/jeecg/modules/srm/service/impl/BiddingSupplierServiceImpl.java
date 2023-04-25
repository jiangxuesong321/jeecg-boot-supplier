package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.BiddingSupplier;
import org.jeecg.modules.srm.mapper.BiddingSupplierMapper;
import org.jeecg.modules.srm.service.IBiddingSupplierService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 招标邀请供应商
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class BiddingSupplierServiceImpl extends ServiceImpl<BiddingSupplierMapper, BiddingSupplier> implements IBiddingSupplierService {
	
	@Autowired
	private BiddingSupplierMapper biddingSupplierMapper;
	
	@Override
	public List<BiddingSupplier> selectByMainId(String mainId) {
		return biddingSupplierMapper.selectByMainId(mainId);
	}
}
