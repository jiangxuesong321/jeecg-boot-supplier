package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 付款申请明细
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@ApiModel(value="pur_pay_apply_detail对象", description="付款申请明细")
@Data
@TableName("pur_pay_apply_detail")
public class PurPayApplyDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**付款申请ID*/
    @ApiModelProperty(value = "付款申请ID")
    private String applyId;
	/**采购订单ID*/
	@Excel(name = "采购订单ID", width = 15)
    @ApiModelProperty(value = "采购订单ID")
    private String purchaseOrderId;
	/**入库单明细ID*/
	@Excel(name = "入库单明细ID", width = 15)
    @ApiModelProperty(value = "入库单明细ID")
    private String billDetailId;
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
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
    private String updateUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**付款比例*/
	@Excel(name = "付款比例", width = 15)
    @ApiModelProperty(value = "付款比例")
    private java.math.BigDecimal payRate;
	/**付款金额*/
	@Excel(name = "付款金额", width = 15)
    @ApiModelProperty(value = "付款金额")
    private java.math.BigDecimal payAmount;
	/**付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】*/
	@Excel(name = "付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】", width = 15)
    @ApiModelProperty(value = "付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】")
    private String payStatus;
	/**0:采购订单，1：退货单*/
	@Excel(name = "0:采购订单，1：退货单", width = 15)
    @ApiModelProperty(value = "0:采购订单，1：退货单")
    private String payType;

    /**合同未税单价本币*/
    @Excel(name = "合同未税单价本币", width = 15)
    @ApiModelProperty(value = "合同未税单价本币")
    private java.math.BigDecimal contractPriceLocal;
    /**合同含税单价本币*/
    @Excel(name = "合同含税单价本币", width = 15)
    @ApiModelProperty(value = "合同含税单价本币")
    private java.math.BigDecimal contractPriceTaxLocal;
    /**合同总额未税本币*/
    @Excel(name = "合同总额未税本币", width = 15)
    @ApiModelProperty(value = "合同总额未税本币")
    private java.math.BigDecimal contractAmountLocal;
    /**合同总额含税本币*/
    @Excel(name = "合同总额含税本币", width = 15)
    @ApiModelProperty(value = "合同总额含税本币")
    private java.math.BigDecimal contractAmountTaxLocal;
    /**合同未税单价原币*/
    @Excel(name = "合同未税单价原币", width = 15)
    @ApiModelProperty(value = "合同未税单价原币")
    private java.math.BigDecimal contractPrice;
    /**合同含税单价原币*/
    @Excel(name = "合同含税单价原币", width = 15)
    @ApiModelProperty(value = "合同含税单价原币")
    private java.math.BigDecimal contractPriceTax;
    /**合同总额未税原币*/
    @Excel(name = "合同总额未税原币", width = 15)
    @ApiModelProperty(value = "合同总额未税原币")
    private java.math.BigDecimal contractAmount;
    /**合同总额含税原币*/
    @Excel(name = "合同总额含税原币", width = 15)
    @ApiModelProperty(value = "合同总额含税原币")
    private java.math.BigDecimal contractAmountTax;
    /**序号**/
    private String no;
    /**交期**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date planLeadTime;
    /**交期**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sendTime;
    /**数量**/
    private BigDecimal qty;
}
