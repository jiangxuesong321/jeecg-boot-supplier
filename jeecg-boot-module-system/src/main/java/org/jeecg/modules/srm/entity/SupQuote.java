package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

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
 * @Description: sup_quote
 * @Author: jeecg-boot
 * @Date:   2022-09-27
 * @Version: V1.0
 */
@Data
@TableName("sup_quote")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sup_quote对象", description="sup_quote")
public class SupQuote implements Serializable {
    private static final long serialVersionUID = 1L;

	/**报价ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "报价ID")
    private java.lang.String id;
	/**供应商ID*/
	@Excel(name = "供应商ID", width = 15)
    @ApiModelProperty(value = "供应商ID")
    private java.lang.String suppId;
	/**询价单明细ID*/
	@Excel(name = "询价单明细ID", width = 15)
    @ApiModelProperty(value = "询价单明细ID")
    private java.lang.String recordId;
	/**供货周期(交期)*/
	@Excel(name = "供货周期(交期)", width = 15)
    @ApiModelProperty(value = "供货周期(交期)")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date leadTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String comment;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sort;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private java.lang.String tenantId;
	/**删除标志位*/
	@Excel(name = "删除标志位", width = 15)
    @ApiModelProperty(value = "删除标志位")
    private java.lang.String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String createUser;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateTime;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
    @ApiModelProperty(value = "品牌")
    private java.lang.String brandName;
	/**规格型号*/
	@Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private java.lang.String speType;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    private java.lang.String currency;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal taxRate;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal orderPrice;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal orderPriceTax;
	/**小计*/
	@Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal orderAmount;
	/**小计*/
	@Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal orderAmountTax;

    /**单价*/
    @Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal orderPriceLocal;
    /**单价*/
    @Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal orderPriceTaxLocal;
    /**小计*/
    @Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal orderAmountLocal;
    /**小计*/
    @Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal orderAmountTaxLocal;
    /**汇率**/
    private BigDecimal exchangeRate;
    /**运费及通过杂费总计*/
    @Excel(name = "运费及通过杂费总计", width = 15)
    @ApiModelProperty(value = "运费及通过杂费总计")
    private java.math.BigDecimal amount;
    /**增值税税率*/
    @Excel(name = "增值税税率", width = 15)
    @ApiModelProperty(value = "增值税税率")
    private java.math.BigDecimal addTax;
    /**关税税率*/
    @Excel(name = "关税税率", width = 15)
    @ApiModelProperty(value = "关税税率")
    private java.math.BigDecimal customsTax;

	/**附件**/
	private String attachment;
	/**运费**/
	private BigDecimal fareAmount;
    /**贸易方式**/
    private String tradeType;
    /**询价数量**/
    private BigDecimal qty;

    /**议价价格**/
    @TableField(exist = false)
    private BigDecimal bgOrderPriceTax;
    /**议价运费**/
    @TableField(exist = false)
    private BigDecimal bgFareAmount;
    /**议价ID**/
    @TableField(exist = false)
    private String bargainId;
    /**子项**/
    @TableField(exist = false)
    List<SupQuoteChild> childList;
}
