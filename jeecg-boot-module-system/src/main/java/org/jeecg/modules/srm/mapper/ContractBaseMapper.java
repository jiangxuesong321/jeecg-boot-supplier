package org.jeecg.modules.srm.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.srm.entity.BasMaterial;
import org.jeecg.modules.srm.entity.ContractBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.srm.entity.ContractObject;
import org.jeecg.modules.srm.vo.ContractObjectVo;

/**
 * @Description: 合同基本信息表
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
public interface ContractBaseMapper extends BaseMapper<ContractBase> {
    /**
     * 合同付款进度
     * @param obj
     * @return
     */
    List<Map<String, Object>> fetchPayList(@Param("query") ContractBase obj);

    /**
     * 设备数量
     * @param obj
     * @return
     */
    List<Map<String, Object>> fetchEqpList(@Param("query") BasMaterial obj);

    /**
     * 合同报表
     * @param page
     * @param contractBase
     * @return
     */
    IPage<ContractObjectVo> fetchContractReport(Page<ContractBase> page, @Param("query") ContractBase contractBase);

    /**
     * 合同报表
     * @param contractBase
     * @return
     */
    List<ContractObjectVo> exportContractReport(@Param("query") ContractBase contractBase);

    /**
     * 合同总额
     * @param supplierId
     * @return
     */
    List<Map<String,Object>> fetchContractAmount(@Param("suppId") String supplierId);

    /**
     * 付款总额
     * @param supplierId
     * @return
     */
    List<Map<String,Object>> fetchPayAmount(@Param("suppId") String supplierId);

    /**
     * 开票总额
     * @param supplierId
     * @return
     */
    List<Map<String, Object>> fetchInvoiceAmount(@Param("suppId") String supplierId);

    /**
     * 合同总额
     * @param suppId
     * @return
     */
    BigDecimal fetchContractAmountRmb(@Param("suppId") String suppId);

    /**
     * 付款总额
     * @param suppId
     * @return
     */
    BigDecimal fetchPayAmountRmb(@Param("suppId") String suppId);

    /**
     * 进行中合同
     * @param suppId
     * @return
     */
    Integer fetchOnGoingContract(@Param("suppId") String suppId);

    /**
     * 已完成合同
     * @param suppId
     * @return
     */
    Integer fetchCompleteContract(@Param("suppId") String suppId);
}
