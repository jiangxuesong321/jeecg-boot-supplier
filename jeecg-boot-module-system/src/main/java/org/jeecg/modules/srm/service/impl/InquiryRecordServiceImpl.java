package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.InquiryRecord;
import org.jeecg.modules.srm.mapper.InquiryRecordMapper;
import org.jeecg.modules.srm.service.IInquiryRecordService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 询价单明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class InquiryRecordServiceImpl extends ServiceImpl<InquiryRecordMapper, InquiryRecord> implements IInquiryRecordService {
	
	@Autowired
	private InquiryRecordMapper inquiryRecordMapper;
	
	@Override
	public List<InquiryRecord> selectByMainId(String mainId) {
		return inquiryRecordMapper.selectByMainId(mainId);
	}

	/**
	 * 询价管理
	 * @param inquiryRecord
	 * @return
	 */
	@Override
	public IPage<InquiryRecord> queryPageList(Page<InquiryRecord> page,InquiryRecord inquiryRecord) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		inquiryRecord.setSuppId(sysUser.getSupplierId());
		return baseMapper.queryPageList(page,inquiryRecord);
	}
	/**
	 * 询价明细
	 * @param id
	 * @return
	 */
	@Override
	public List<InquiryRecord> getRecordById(String id) {
		return baseMapper.getRecordById(id);
	}
}
