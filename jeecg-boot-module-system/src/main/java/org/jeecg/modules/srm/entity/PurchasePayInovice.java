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
 * @Description: 发票登记
 * @Author: jeecg-boot
 * @Date:   2022-06-20
 * @Version: V1.0
 */
@Data
@TableName("purchase_pay_inovice")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="purchase_pay_inovice对象", description="发票登记")
public class PurchasePayInovice implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**发票编号*/
	@Excel(name = "发票编号", width = 15)
    @ApiModelProperty(value = "发票编号")
    private String invoiceNo;
	/**发票税率*/
	@Excel(name = "发票税率", width = 15)
    @ApiModelProperty(value = "发票税率")
    private BigDecimal taxRate;
	/**税额*/
	@Excel(name = "税额", width = 15)
    @ApiModelProperty(value = "税额")
    private BigDecimal invoiceTax;
	/**开票金额（未税）*/
	@Excel(name = "开票金额（未税）", width = 15)
    @ApiModelProperty(value = "开票金额（未税）")
    private BigDecimal invoiceAmount;
	/**开票金额（含税）*/
	@Excel(name = "开票金额（含税）", width = 15)
    @ApiModelProperty(value = "开票金额（含税）")
    private BigDecimal invoiceAmountTax;
    /**开票金额（未税）*/
    @Excel(name = "开票金额（未税）", width = 15)
    @ApiModelProperty(value = "开票金额（未税）")
    private BigDecimal invoiceAmountLocal;
    /**开票金额（含税）*/
    @Excel(name = "开票金额（含税）", width = 15)
    @ApiModelProperty(value = "开票金额（含税）")
    private BigDecimal invoiceAmountTaxLocal;
	/**开票日期*/
	@Excel(name = "开票日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开票日期")
    private Date invoiceDate;
	/**供应商ID*/
	@Excel(name = "供应商ID", width = 15)
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**确认状态（00；待确认，10确认）*/
	@Excel(name = "确认状态（00；待确认，10确认）", width = 15)
    @ApiModelProperty(value = "确认状态（00；待确认，10确认）")
    @Dict(dicCode = "invoice_status")
    private String status;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
    @ApiModelProperty(value = "项目ID")
    private String projectId;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private String projectName;
	/**合同ID*/
	@Excel(name = "合同ID", width = 15)
    @ApiModelProperty(value = "合同ID")
    private String contractId;
	/**合同名称*/
	@Excel(name = "合同名称", width = 15)
    @ApiModelProperty(value = "合同名称")
    private String contractName;
	/**付款类型*/
	@Excel(name = "付款类型", width = 15)
    @ApiModelProperty(value = "付款类型")
    private String payType;
	/**发票类型*/
	@Excel(name = "发票类型", width = 15)
    @ApiModelProperty(value = "发票类型")
    @Dict(dicCode = "invoice_type")
    private String invoiceType;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    @Dict(dicCode = "currency_type")
    private String currency;
	/**发票是否被使用**/
	private String isUsed;
	/**删除标志**/
	private String delFlag;
	/**附件**/
	private String attachment;

	@TableField(exist = false)
	private List<PurchasePayInvoiceDetail> detailList;
    /**汇率**/
	private BigDecimal exchangeRate;

	private BigDecimal hasInvoiceAmount;

    private BigDecimal hasInvoiceAmountTax;

    private BigDecimal hasInvoiceAmountLocal;

    private BigDecimal hasInvoiceAmountTaxLocal;

    @TableField(exist = false)
    private String applyId;

    @TableField(exist = false)
    private BigDecimal applyInvoiceAmount;
    @TableField(exist = false)
    private BigDecimal applyInvoiceAmountTax;
    @TableField(exist = false)
    private BigDecimal applyInvoiceAmountLocal;
    @TableField(exist = false)
    private BigDecimal applyInvoiceAmountTaxLocal;
    @TableField(exist = false)
    private String contractNumber;

    private String invoiceName;
}
