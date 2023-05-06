package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: contract_object_qty
 * @Author: jeecg-boot
 * @Date:   2022-10-10
 * @Version: V1.0
 */
@Data
@TableName("contract_object_qty")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="contract_object_qty对象", description="contract_object_qty")
public class ContractObjectQty implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**合同ID*/
	@Excel(name = "合同ID", width = 15)
    @ApiModelProperty(value = "合同ID")
    private String contractId;
    /**合同ID*/
    @Excel(name = "合同明细ID", width = 15)
    @ApiModelProperty(value = "合同明细ID")
    private String recordId;
    /**供应商ID*/
    @Excel(name = "供应商ID", width = 15)
    @ApiModelProperty(value = "供应商ID")
    private String suppId;
	/**标的类型*/
	@Excel(name = "标的类型", width = 15)
    @ApiModelProperty(value = "标的类型")
    private String objectType;
	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
    @ApiModelProperty(value = "设备编号")
    private String prodCode;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private String prodName;
	/**合同未税单价原币*/
	@Excel(name = "合同未税单价原币", width = 15)
    @ApiModelProperty(value = "合同未税单价原币")
    private BigDecimal contractPrice;
	/**合同含税单价原币*/
	@Excel(name = "合同含税单价原币", width = 15)
    @ApiModelProperty(value = "合同含税单价原币")
    private BigDecimal contractPriceTax;
	/**合同总额未税原币*/
	@Excel(name = "合同总额未税原币", width = 15)
    @ApiModelProperty(value = "合同总额未税原币")
    private BigDecimal contractAmount;
	/**合同总额含税原币*/
	@Excel(name = "合同总额含税原币", width = 15)
    @ApiModelProperty(value = "合同总额含税原币")
    private BigDecimal contractAmountTax;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private BigDecimal contractTaxRate;
	/**设备品牌*/
	@Excel(name = "设备品牌", width = 15)
    @ApiModelProperty(value = "设备品牌")
    private String prodBrand;
	/**设备规格型号*/
	@Excel(name = "设备规格型号", width = 15)
    @ApiModelProperty(value = "设备规格型号")
    private String prodSpecType;
	/**汇率*/
	@Excel(name = "汇率", width = 15)
    @ApiModelProperty(value = "汇率")
    private BigDecimal exchangeRate;
	/**合同未税单价本币*/
	@Excel(name = "合同未税单价本币", width = 15)
    @ApiModelProperty(value = "合同未税单价本币")
    private BigDecimal contractPriceLocal;
	/**合同含税单价本币*/
	@Excel(name = "合同含税单价本币", width = 15)
    @ApiModelProperty(value = "合同含税单价本币")
    private BigDecimal contractPriceTaxLocal;
	/**合同总额未税本币*/
	@Excel(name = "合同总额未税本币", width = 15)
    @ApiModelProperty(value = "合同总额未税本币")
    private BigDecimal contractAmountLocal;
	/**合同总额含税本币*/
	@Excel(name = "合同总额含税本币", width = 15)
    @ApiModelProperty(value = "合同总额含税本币")
    private BigDecimal contractAmountTaxLocal;
	/**需求日期*/
	@Excel(name = "需求日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "需求日期")
    private Date requireDate;
	/**计划交期*/
	@Excel(name = "计划交期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "计划交期")
    private Date planDeliveryDate;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String comment;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
	/**删除标志位*/
	@Excel(name = "删除标志位", width = 15)
    @ApiModelProperty(value = "删除标志位")
    private String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createUser;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updateUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**询报价明细ID*/
	@Excel(name = "询报价明细ID", width = 15)
    @ApiModelProperty(value = "询报价明细ID")
    private String toRecordId;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;
	/**设备产能*/
	@Excel(name = "设备产能", width = 15)
    @ApiModelProperty(value = "设备产能")
    private String capacity;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    @Dict(dicCode = "unit")
    private String unitId;
	/**付款申请比例*/
	@Excel(name = "付款申请比例", width = 15)
    @ApiModelProperty(value = "付款申请比例")
    private BigDecimal payRate;
	/**付款数量*/
	@Excel(name = "付款数量", width = 15)
    @ApiModelProperty(value = "付款数量")
    private BigDecimal payQty;
	/**开票申请比例*/
	@Excel(name = "开票申请比例", width = 15)
    @ApiModelProperty(value = "开票申请比例")
    private BigDecimal invoiceRate;
	/**开票数量*/
	@Excel(name = "开票数量", width = 15)
    @ApiModelProperty(value = "开票数量")
    private BigDecimal invoiceQty;
	/**已发货数量*/
	@Excel(name = "已发货数量", width = 15)
    @ApiModelProperty(value = "已发货数量")
    private BigDecimal toSendQty;

    /**单价*/
    @TableField(exist = false)
    private BigDecimal price;
    /**单价*/
    @TableField(exist = false)
    private BigDecimal priceTax;
    /**小计*/
    @TableField(exist = false)
    private BigDecimal amount;
    /**小计*/
    @TableField(exist = false)
    private BigDecimal amountTax;
    /*** 单价*/
    @TableField(exist = false)
    private BigDecimal priceLocal;
    /*** 小计*/
    @TableField(exist = false)
    private BigDecimal amountLocal;
    /*** 单价*/
    @TableField(exist = false)
    private BigDecimal priceTaxLocal;
    /*** 小计*/
    @TableField(exist = false)
    private BigDecimal amountTaxLocal;
    /**规格型号**/
    @TableField(exist = false)
    private String speType;

    /**关联ID**/
    @TableField(exist = false)
    private String billDetailId;
    /**税额**/
    @TableField(exist = false)
    private BigDecimal invoiceTax;
    /**产品ID**/
    private String prodId;

    @TableField(exist = false)
    private BigDecimal taxRate;

}
