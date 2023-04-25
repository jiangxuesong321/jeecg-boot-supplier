package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.BiddingRecord;
import org.jeecg.modules.srm.mapper.BiddingRecordMapper;
import org.jeecg.modules.srm.service.IBiddingRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 招标明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class BiddingRecordServiceImpl extends ServiceImpl<BiddingRecordMapper, BiddingRecord> implements IBiddingRecordService {
	
	@Autowired
	private BiddingRecordMapper biddingRecordMapper;
	
	@Override
	public List<BiddingRecord> selectByMainId(String mainId) {
		return biddingRecordMapper.selectByMainId(mainId);
	}

	/**
	 * 招标明细
	 * @param id
	 * @return
	 */
	@Override
	public List<BiddingRecord> queryRecordList(String id) {
		return baseMapper.queryRecordList(id);
	}
}
