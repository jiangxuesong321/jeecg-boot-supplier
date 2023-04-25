package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.InquiryList;
import org.jeecg.modules.srm.entity.InquiryRecord;
import org.jeecg.modules.srm.entity.InquirySupplier;
import org.jeecg.modules.srm.mapper.InquiryRecordMapper;
import org.jeecg.modules.srm.mapper.InquirySupplierMapper;
import org.jeecg.modules.srm.mapper.InquiryListMapper;
import org.jeecg.modules.srm.service.IInquiryListService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 询价单主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class InquiryListServiceImpl extends ServiceImpl<InquiryListMapper, InquiryList> implements IInquiryListService {

	@Autowired
	private InquiryListMapper inquiryListMapper;
	@Autowired
	private InquiryRecordMapper inquiryRecordMapper;
	@Autowired
	private InquirySupplierMapper inquirySupplierMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(InquiryList inquiryList, List<InquiryRecord> inquiryRecordList,List<InquirySupplier> inquirySupplierList) {
		inquiryListMapper.insert(inquiryList);
		if(inquiryRecordList!=null && inquiryRecordList.size()>0) {
			for(InquiryRecord entity:inquiryRecordList) {
				//外键设置
				entity.setInquiryId(inquiryList.getId());
				inquiryRecordMapper.insert(entity);
			}
		}
		if(inquirySupplierList!=null && inquirySupplierList.size()>0) {
			for(InquirySupplier entity:inquirySupplierList) {
				//外键设置
				entity.setInquiryId(inquiryList.getId());
				inquirySupplierMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(InquiryList inquiryList,List<InquiryRecord> inquiryRecordList,List<InquirySupplier> inquirySupplierList) {
		inquiryListMapper.updateById(inquiryList);
		
		//1.先删除子表数据
		inquiryRecordMapper.deleteByMainId(inquiryList.getId());
		inquirySupplierMapper.deleteByMainId(inquiryList.getId());
		
		//2.子表数据重新插入
		if(inquiryRecordList!=null && inquiryRecordList.size()>0) {
			for(InquiryRecord entity:inquiryRecordList) {
				//外键设置
				entity.setInquiryId(inquiryList.getId());
				inquiryRecordMapper.insert(entity);
			}
		}
		if(inquirySupplierList!=null && inquirySupplierList.size()>0) {
			for(InquirySupplier entity:inquirySupplierList) {
				//外键设置
				entity.setInquiryId(inquiryList.getId());
				inquirySupplierMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		inquiryRecordMapper.deleteByMainId(id);
		inquirySupplierMapper.deleteByMainId(id);
		inquiryListMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			inquiryRecordMapper.deleteByMainId(id.toString());
			inquirySupplierMapper.deleteByMainId(id.toString());
			inquiryListMapper.deleteById(id);
		}
	}
	
}
