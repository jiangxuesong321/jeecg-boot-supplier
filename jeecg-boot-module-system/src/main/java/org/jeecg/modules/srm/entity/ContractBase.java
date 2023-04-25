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

/**
 * @Description: 合同基本信息表
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
@ApiModel(value="contract_base对象", description="合同基本信息表")
@Data
@TableName("contract_base")
public class ContractBase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**合同ID*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "合同ID")
    private String id;
    /**合同编号*/
    @Excel(name = "合同编号", width = 15)
    @ApiModelProperty(value = "合同编号")
    private String contractNumber;
    /**合同名称*/
    @Excel(name = "合同名称", width = 15)
    @ApiModelProperty(value = "合同名称")
    private String contractName;
    /**合同类型*/
    @Excel(name = "合同类型", width = 15)
    @ApiModelProperty(value = "合同类型")
    @Dict(dicCode = "project_type")
    private String contractType;
    /**项目ID*/
    @Excel(name = "项目ID", width = 15)
    @ApiModelProperty(value = "项目ID")
    private String projectId;
    /**需求ID*/
    @Excel(name = "需求ID", width = 15)
    @ApiModelProperty(value = "需求ID")
    private String requestId;
    /**合同币种*/
    @Excel(name = "合同币种", width = 15)
    @ApiModelProperty(value = "合同币种")
    @Dict(dicCode = "currency_type")
    private String contractCurrency;
    /**合同等级*/
    @Excel(name = "合同等级", width = 15)
    @ApiModelProperty(value = "合同等级")
    private String contractLevel;
    /**合同状态*/
    @Excel(name = "合同状态", width = 15)
    @ApiModelProperty(value = "合同状态")
    @Dict(dicCode = "contract_status")
    private String contractStatus;
    /**合同份数*/
    @Excel(name = "合同份数", width = 15)
    @ApiModelProperty(value = "合同份数")
    private Integer contractCopiesNumber;
    /**合同签订日期*/
    @Excel(name = "合同签订日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "合同签订日期")
    private Date contractSignDate;
    /**签订地点*/
    @Excel(name = "签订地点", width = 15)
    @ApiModelProperty(value = "签订地点")
    private String contractSignAddress;
    /**合同生效日期*/
    @Excel(name = "合同生效日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "合同生效日期")
    private Date contractValidDate;
    /**合同金额原币*/
    @Excel(name = "合同金额原币", width = 15)
    @ApiModelProperty(value = "合同金额原币")
    private java.math.BigDecimal contractAmount;
    /**合同含税金额原币*/
    @Excel(name = "合同含税金额原币", width = 15)
    @ApiModelProperty(value = "合同含税金额原币")
    private java.math.BigDecimal contractAmountTax;
    /**合同金额本币*/
    @Excel(name = "合同金额本币", width = 15)
    @ApiModelProperty(value = "合同金额本币")
    private java.math.BigDecimal contractAmountLocal;
    /**合同含税金额本币*/
    @Excel(name = "合同含税金额本币", width = 15)
    @ApiModelProperty(value = "合同含税金额本币")
    private java.math.BigDecimal contractAmountTaxLocal;
    /**合同汇率*/
    @Excel(name = "合同汇率", width = 15)
    @ApiModelProperty(value = "合同汇率")
    private java.math.BigDecimal contractExchangeRate;
    /**合同税率*/
    @Excel(name = "合同税率", width = 15)
    @ApiModelProperty(value = "合同税率")
    private java.math.BigDecimal contractTaxRate;
    /**甲方*/
    @Excel(name = "甲方", width = 15)
    @ApiModelProperty(value = "甲方")
    private String contractFirstParty;
    /**甲方*/
    @Excel(name = "甲方", width = 15)
    @ApiModelProperty(value = "甲方")
    private String contractFirstPartyId;
    /**乙方*/
    @Excel(name = "乙方", width = 15)
    @ApiModelProperty(value = "乙方")
    private String contractSecondParty;
    /**乙方*/
    @Excel(name = "乙方", width = 15)
    @ApiModelProperty(value = "乙方")
    private String contractSecondPartyId;
    /**甲方地址*/
    @Excel(name = "甲方地址", width = 15)
    @ApiModelProperty(value = "甲方地址")
    private String contractFirstAddress;
    /**甲方电话*/
    @Excel(name = "甲方电话", width = 15)
    @ApiModelProperty(value = "甲方电话")
    private String contractFirstTelphone;
    /**甲方传真*/
    @Excel(name = "甲方传真", width = 15)
    @ApiModelProperty(value = "甲方传真")
    private String contractFirstFax;
    /**甲方联系人*/
    @Excel(name = "甲方联系人", width = 15)
    @ApiModelProperty(value = "甲方联系人")
    private String contractFirstContact;
    /**甲方开户行*/
    @Excel(name = "甲方开户行", width = 15)
    @ApiModelProperty(value = "甲方开户行")
    private String contractFirstOpeningBank;
    /**甲方银行账号*/
    @Excel(name = "甲方银行账号", width = 15)
    @ApiModelProperty(value = "甲方银行账号")
    private String contractFirstBankAccount;
    /**甲方法人*/
    @Excel(name = "甲方法人", width = 15)
    @ApiModelProperty(value = "甲方法人")
    private String contractFirstLegalPerson;
    /**甲方代理人*/
    @Excel(name = "甲方代理人", width = 15)
    @ApiModelProperty(value = "甲方代理人")
    private String contractFirstAgent;
    /**甲方邮政编码*/
    @Excel(name = "甲方邮政编码", width = 15)
    @ApiModelProperty(value = "甲方邮政编码")
    private String contractFirstPostCode;
    /**乙方地址*/
    @Excel(name = "乙方地址", width = 15)
    @ApiModelProperty(value = "乙方地址")
    private String contractSecondAddress;
    /**乙方电话*/
    @Excel(name = "乙方电话", width = 15)
    @ApiModelProperty(value = "乙方电话")
    private String contractSecondTelphone;
    /**乙方传真*/
    @Excel(name = "乙方传真", width = 15)
    @ApiModelProperty(value = "乙方传真")
    private String contractSecondFax;
    /**乙方联系人*/
    @Excel(name = "乙方联系人", width = 15)
    @ApiModelProperty(value = "乙方联系人")
    private String contractSecondContact;
    /**乙方开户行*/
    @Excel(name = "乙方开户行", width = 15)
    @ApiModelProperty(value = "乙方开户行")
    private String contractSecondOpeningBank;
    /**乙方银行账号*/
    @Excel(name = "乙方银行账号", width = 15)
    @ApiModelProperty(value = "乙方银行账号")
    private String contractSecondBankAccount;
    /**乙方法人*/
    @Excel(name = "乙方法人", width = 15)
    @ApiModelProperty(value = "乙方法人")
    private String contractSecondLegalPerson;
    /**乙方代理人*/
    @Excel(name = "乙方代理人", width = 15)
    @ApiModelProperty(value = "乙方代理人")
    private String contractSecondAgent;
    /**乙方邮政编码*/
    @Excel(name = "乙方邮政编码", width = 15)
    @ApiModelProperty(value = "乙方邮政编码")
    private String contractSecondPostCode;
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
    /**合同word**/
    private String wordAttachment;
    /**其他附件**/
    private String otherAttachment;
    private String contractAttachment;
    private String specificationAttachment;
    /**来源**/
    private String source;
    /**补充协议**/
    private String isSag;
    /**招投标单号**/
    @TableField(exist = false)
    private String biddingNo;
    /**项目名称**/
    @TableField(exist = false)
    private String projName;
    /**项目编码**/
    @TableField(exist = false)
    private String projCode;
    /**采购类型**/
    @TableField(exist = false)
    private String reqType;
    /**询比价单号**/
    @TableField(exist = false)
    private String inquiryCode;
    /**审核人**/
    @TableField(exist = false)
    private String approver;
    /**审核备注**/
    @TableField(exist = false)
    private String approveComment;
    /**子项**/
    @TableField(exist = false)
    private String model;

    @TableField(exist = false)
    private BigDecimal payAmount;
    @TableField(exist = false)
    private BigDecimal remainPayAmount;
    @TableField(exist = false)
    private BigDecimal invoiceAmount;
    @TableField(exist = false)
    private BigDecimal remainInvoiceAmount;

    @TableField(exist = false)
    private String isTodo;

    private String mainId;
    @TableField(exist = false)
    private String prodName;
    @TableField(exist = false)
    private String qty;
    @TableField(exist = false)
    private String address;
}
