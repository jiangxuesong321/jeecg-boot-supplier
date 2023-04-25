package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.BiddingProfessionals;
import org.jeecg.modules.srm.mapper.BiddingProfessionalsMapper;
import org.jeecg.modules.srm.service.IBiddingProfessionalsService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: bidding_professionals
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class BiddingProfessionalsServiceImpl extends ServiceImpl<BiddingProfessionalsMapper, BiddingProfessionals> implements IBiddingProfessionalsService {
	
	@Autowired
	private BiddingProfessionalsMapper biddingProfessionalsMapper;
	
	@Override
	public List<BiddingProfessionals> selectByMainId(String mainId) {
		return biddingProfessionalsMapper.selectByMainId(mainId);
	}
}
