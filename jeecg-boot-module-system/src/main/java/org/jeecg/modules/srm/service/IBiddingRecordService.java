package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.BiddingRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 招标明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface IBiddingRecordService extends IService<BiddingRecord> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<BiddingRecord>
	 */
	public List<BiddingRecord> selectByMainId(String mainId);

	/**
	 * 招标明细
	 * @param id
	 * @return
	 */
    List<BiddingRecord> queryRecordList(String id);
}
