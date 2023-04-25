package org.jeecg.modules.srm.service.impl;

import org.jeecg.modules.srm.entity.BiddingRecordSupplier;
import org.jeecg.modules.srm.mapper.BiddingRecordSupplierMapper;
import org.jeecg.modules.srm.service.IBiddingRecordSupplierService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: bidding_record_supplier
 * @Author: jeecg-boot
 * @Date:   2022-10-24
 * @Version: V1.0
 */
@Service
public class BiddingRecordSupplierServiceImpl extends ServiceImpl<BiddingRecordSupplierMapper, BiddingRecordSupplier> implements IBiddingRecordSupplierService {
    /**
     * 投标数量
     * @param suppId
     * @return
     */
    @Override
    public List<BiddingRecordSupplier> fetchBiddingList(String suppId) {
        return baseMapper.fetchBiddingList(suppId);
    }
}
