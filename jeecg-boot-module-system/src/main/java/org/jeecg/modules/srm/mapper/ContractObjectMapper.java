package org.jeecg.modules.srm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.srm.entity.ContractBase;
import org.jeecg.modules.srm.entity.ContractObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 合同标的
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
public interface ContractObjectMapper extends BaseMapper<ContractObject> {

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
   * @return List<ContractObject>
   */
	public List<ContractObject> selectByMainId(@Param("mainId") String mainId);

	/**
	 * 合同明细
	 * @param contractBase
	 * @return
	 */
	List<ContractObject> getContractDetailList(@Param("query") ContractBase contractBase);

	/**
	 * 合同明细
	 * @param page
	 * @param obj
	 * @return
	 */
    IPage<ContractObject> listByDetailList(Page<ContractObject> page, @Param("query") ContractObject obj);
}
