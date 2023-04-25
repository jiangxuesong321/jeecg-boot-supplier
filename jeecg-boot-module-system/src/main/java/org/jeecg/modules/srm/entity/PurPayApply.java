package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
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

/**
 * @Description: 付款申请
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@ApiModel(value="pur_pay_apply对象", description="付款申请")
@Data
@TableName("pur_pay_apply")
public class PurPayApply implements Serializable {
    private static final long serialVersionUID = 1L;

	/**采购付款申请ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "采购付款申请ID")
    private String id;
	/**付款申请单号*/
	@Excel(name = "付款申请单号", width = 15)
    @ApiModelProperty(value = "付款申请单号")
    private String applyCode;
	/**付款金额*/
	@Excel(name = "付款金额", width = 15)
    @ApiModelProperty(value = "付款金额")
    private java.math.BigDecimal payAmount;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal taxRate;
	/**付款方式*/
	@Excel(name = "付款方式", width = 15)
    @ApiModelProperty(value = "付款方式")
    @Dict(dicCode = "payMethod")
    private String payMethod;
    /**付款类型*/
    @Excel(name = "付款类型", width = 15)
    @ApiModelProperty(value = "付款类型")
    @Dict(dicCode = "payType")
    private String payType;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    @Dict(dicCode = "currency_type")
    private String currency;
	/**申请理由*/
	@Excel(name = "申请理由", width = 15)
    @ApiModelProperty(value = "申请理由")
    private String applyReason;
	/**申请状态：00：待审核10：已驳回20:已受理,30:部分付款,40:付款完成*/
	@Excel(name = "申请状态：00：待审核10：已驳回20:已受理,30:部分付款,40:付款完成", width = 15)
    @ApiModelProperty(value = "申请状态：00：待审核10：已驳回20:已受理,30:部分付款,40:付款完成")
    @Dict(dicCode = "applyStatus")
    private String applyStatus;
	/**申请时间*/
	@Excel(name = "申请时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "申请时间")
    private Date applyTime;
	/**收款开户行*/
	@Excel(name = "收款开户行", width = 15)
    @ApiModelProperty(value = "收款开户行")
    private String receivingBank;
	/**收款账号*/
	@Excel(name = "收款账号", width = 15)
    @ApiModelProperty(value = "收款账号")
    private String receivingNumber;
	/**应付款日期*/
	@Excel(name = "应付款日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "应付款日期")
    private Date dueDate;
	/**供应商id*/
	@Excel(name = "供应商id", width = 15)
    @ApiModelProperty(value = "供应商id")
    private String suppId;
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
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private String suppName;

	/**申请附件地址*/
	@Excel(name = "申请附件地址", width = 15)
    @ApiModelProperty(value = "申请附件地址")
    private String attachment;
	/**合同**/
	private String contractId;
    /**合同**/
    private String contractName;
    /**合同编码**/
    private String contractNumber;
    /**项目**/
    private String projectId;
    /**项目**/
    private String projectName;
    /**附件**/
    private String suppAttachment;
    /**附件**/
    private String forwarderAttachment;
    /****/
    private String isPlan;

    private java.math.BigDecimal payAmountOther;

    private BigDecimal exchangeRate;
    /**发票ID**/
    @TableField(exist = false)
    private String invoiceIds;
}
