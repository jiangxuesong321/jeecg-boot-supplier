package org.jeecg.modules.srm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.InquiryRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 询价单明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface InquiryRecordMapper extends BaseMapper<InquiryRecord> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<InquiryRecord>
   */
	public List<InquiryRecord> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 询价管理
	 * @param inquiryRecord
	 * @return
	 */
    IPage<InquiryRecord> queryPageList(Page<InquiryRecord> page,@Param("query") InquiryRecord inquiryRecord);
	/**
	 * 询价明细
	 * @param id
	 * @return
	 */
    List<InquiryRecord> getRecordById(@Param("id") String id);
}
