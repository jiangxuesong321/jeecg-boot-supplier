package org.jeecg.modules.srm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 询价单明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@ApiModel(value="inquiry_record对象", description="询价单明细表")
@Data
@TableName("inquiry_record")
public class InquiryRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**询价记录ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "询价记录ID")
    private String id;
	/**询价单ID*/
    @ApiModelProperty(value = "询价单ID")
    private String inquiryId;
	/**询价物品ID*/
	@Excel(name = "询价物品ID", width = 15)
    @ApiModelProperty(value = "询价物品ID")
    private String prodId;
	/**记录ID*/
	@Excel(name = "记录ID", width = 15)
    @ApiModelProperty(value = "记录ID")
    private String toRecordId;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    @Dict(dicCode = "unit")
    private String unitId;
	/**交期*/
	@Excel(name = "交期", width = 15)
    @ApiModelProperty(value = "交期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date leadTime;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
	/**删除标志*/
	@Excel(name = "删除标志", width = 15)
    @ApiModelProperty(value = "删除标志")
    private String delFlag;
	/**创建用户*/
	@Excel(name = "创建用户", width = 15)
    @ApiModelProperty(value = "创建用户")
    private String createUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
    @ApiModelProperty(value = "更新用户")
    private String updateUser;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String comment;
	/**询价状态(3:已中标)*/
	@Excel(name = "询价状态(3:已中标)", width = 15)
    @ApiModelProperty(value = "询价状态(3:已中标)")
    private String status;
    @TableField(exist = false)
	private String isBargin;
	/**驳回原因*/
	@Excel(name = "驳回原因", width = 15)
    @ApiModelProperty(value = "驳回原因")
    private String backReason;
	/**询价物品名称*/
	@Excel(name = "询价物品名称", width = 15)
    @ApiModelProperty(value = "询价物品名称")
    private String prodName;
	/**询价物品规格描述*/
	@Excel(name = "询价物品规格描述", width = 15)
    @ApiModelProperty(value = "询价物品规格描述")
    private String speType;
    /**询价状态**/
    private String inquiryStatus;
    /**产品编码**/
    private String prodCode;
    /**是否生成合同**/
    private String isContract;
    /**设备类型**/
    @TableField(exist = false)
    @Dict(dicCode = "model")
    private String model;
    /**单价*/
    private BigDecimal price;
    /** 小计*/
    private BigDecimal amount;
    /** 单价*/
    private BigDecimal priceTax;
    /**小计*/
    private BigDecimal amountTax;
    /**询价单号**/
    @TableField(exist = false)
    private String inquiryCode;
    /**供应商ID**/
    @TableField(exist = false)
    private String suppId;
    /**采购人员**/
    @TableField(exist = false)
    private String inquirer;
    /**询价名称**/
    @TableField(exist = false)
    private String inquiryName;
    /**采购说明**/
    @TableField(exist = false)
    private String remark;
    /**报价截止日期**/
    @TableField(exist = false)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date quotationDeadline;
    /**币种**/
    @TableField(exist = false)
    private String currency;

    /**报价品牌**/
    @TableField(exist = false)
    private String suppBrandName;
    /**报价规格型号**/
    @TableField(exist = false)
    private String suppSpeType;
    /**报价币种**/
    @TableField(exist = false)
    private String suppCurrency;
    /**报价单价**/
    @TableField(exist = false)
    private BigDecimal orderPriceTax;
    /**报价小计**/
    @TableField(exist = false)
    private BigDecimal orderAmountTax;
    /**预估交期**/
    @TableField(exist = false)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date suppLeadTime;
    /**税率**/
    @TableField(exist = false)
    private BigDecimal taxRate;
    /**运费**/
    @TableField(exist = false)
    private BigDecimal fareAmount;
    /**贸易方式**/
    @TableField(exist = false)
    private String tradeType;
    /**判断前端按钮是否显示**/
    @TableField(exist = false)
    private String isShow;
    /**产能**/
    @TableField(exist = false)
    private String capacity;
    /**采购类型**/
    @TableField(exist = false)
    private String reqType;

    /**子项**/
    @TableField(exist = false)
    private List<SupQuoteChild> childList;


    @TableField(exist = false)
    private String reqComment;

    @TableField(exist = false)
    private String zbComment;

    @TableField(exist = false)
    private String zbAttachment;

}
