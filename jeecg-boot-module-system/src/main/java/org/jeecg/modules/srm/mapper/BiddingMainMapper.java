package org.jeecg.modules.srm.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.BasSupplier;
import org.jeecg.modules.srm.entity.BiddingMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.srm.entity.BiddingRecord;
import org.jeecg.modules.srm.vo.BiddingRecordPage;

/**
 * @Description: 招标主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
public interface BiddingMainMapper extends BaseMapper<BiddingMain> {
    /**
     * 招标列表
     * @param page
     * @param biddingMain
     * @return
     */
    IPage<BiddingMain> queryPageList(Page<BiddingMain> page, @Param("query") BiddingMain biddingMain);

    /**
     * 招标明细
     * @param biddingMain
     * @return
     */
    List<BiddingRecord> fetchDetailList(@Param("query") BiddingMain biddingMain);
    /**
     * 中标供应商信息
     * @param page
     * @return
     */
    BasSupplier getSuppInfo(@Param("query") BiddingMain page);

    /**
     * 交期排名
     * @param id
     * @param suppId
     * @return
     */
    String getLeadTimeRank(@Param("id") String id, @Param("suppId") String suppId);

    /**
     * 报价排名
     * @param id
     * @param suppId
     * @return
     */
    String getPriceRank(@Param("id") String id, @Param("suppId") String suppId);

    /**
     * 中标议价列表
     * @param page
     * @param biddingMain
     * @return
     */
    IPage<BiddingRecordPage> fetchBarginList(Page<BiddingMain> page, @Param("query") BiddingMain biddingMain);
}
