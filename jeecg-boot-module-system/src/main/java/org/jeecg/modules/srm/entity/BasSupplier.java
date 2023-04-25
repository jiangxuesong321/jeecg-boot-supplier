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
import java.util.Date;

/**
 * @Description: 供应商基本信息
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@ApiModel(value="bas_supplier对象", description="供应商基本信息")
@Data
@TableName("bas_supplier")
public class BasSupplier implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;
	/**编码*/
	@Excel(name = "编码", width = 15)
    @ApiModelProperty(value = "编码")
    private String code;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**简称*/
	@Excel(name = "简称", width = 15)
    @ApiModelProperty(value = "简称")
    private String shortName;
	/**供应商分类*/
	@Excel(name = "供应商分类", width = 15)
    @ApiModelProperty(value = "供应商分类")
    private String supplierCategory;
	/**业务地域*/
	@Excel(name = "业务地域", width = 15)
    @ApiModelProperty(value = "业务地域")
    private String bizArea;
	/**网站*/
	@Excel(name = "网站", width = 15)
    @ApiModelProperty(value = "网站")
    private String website;
	/**法人代表*/
	@Excel(name = "法人代表", width = 15)
    @ApiModelProperty(value = "法人代表")
    private String corporate;
	/**法人电话*/
	@Excel(name = "法人电话", width = 15)
    @ApiModelProperty(value = "法人电话")
    private String corporatePhone;
	/**供应商地址*/
	@Excel(name = "供应商地址", width = 15)
    @ApiModelProperty(value = "供应商地址")
    private String address;
	/**供应商电话*/
	@Excel(name = "供应商电话", width = 15)
    @ApiModelProperty(value = "供应商电话")
    private String telephone;
	/**供应商邮箱*/
	@Excel(name = "供应商邮箱", width = 15)
    @ApiModelProperty(value = "供应商邮箱")
    private String email;
	/**收件信息传真*/
	@Excel(name = "供应商传真", width = 15)
    @ApiModelProperty(value = "供应商传真")
    private String fax;
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private String attachment;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**是否启用*/
	@Excel(name = "是否启用", width = 15)
    @ApiModelProperty(value = "是否启用")
    private Integer isEnabled;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**版本*/
	@Excel(name = "版本", width = 15)
    @ApiModelProperty(value = "版本")
    private Integer version;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private String taxRate;
	/**支付方式*/
	@Excel(name = "支付方式", width = 15)
    @ApiModelProperty(value = "支付方式")
    private String payMethod;
	/**付款方式ID*/
	@Excel(name = "付款方式ID", width = 15)
    @ApiModelProperty(value = "付款方式ID")
    private String paymentMethodId;
	/**付款方式名称*/
	@Excel(name = "付款方式名称", width = 15)
    @ApiModelProperty(value = "付款方式名称")
    private String paymentMethodName;
	/**是否月结供应商*/
	@Excel(name = "是否月结供应商", width = 15)
    @ApiModelProperty(value = "是否月结供应商")
    private String isMonth;
	/**供应商性质*/
	@Excel(name = "供应商性质", width = 15)
    @Dict(dicCode = "supplier_type")
    @ApiModelProperty(value = "供应商性质")
    private String supplierType;
	/**系统账号*/
	@Excel(name = "系统账号", width = 15)
    @ApiModelProperty(value = "系统账号")
    private String sysAccount;
	/**系统账号初始密码*/
	@Excel(name = "系统账号初始密码", width = 15)
    @ApiModelProperty(value = "系统账号初始密码")
    private String sysPwd;
	/**代理等级*/
	@Excel(name = "代理等级", width = 15)
    @ApiModelProperty(value = "代理等级")
    private String level;
	/**logo*/
	@Excel(name = "logo", width = 15)
    @ApiModelProperty(value = "logo")
    private String logoUrl;
	/**营业执照*/
	@Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private String supplierAttachment;
	/**授权人身份证号*/
	@Excel(name = "授权人身份证号", width = 15)
    @ApiModelProperty(value = "授权人身份证号")
    private String userCardNum;
	/**统一社会信用代码**/
	private String socialCode;

    /**
     * 供应商属性
     */
	private String supplierProp;
    /**注册资本**/
    private String registerCurrency;
    /**注册资本**/
	private String registerCapital;
    /**注册地址**/
    private String registerArea;
	/**状态**/
	@Dict(dicCode = "supp_status")
	private String status;
    /**是否收费**/
    private String isCharge;

    /**冻结开始时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fnStartTime;

    /**冻结结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fnEndTime;

    /**开票地址**/
    private String invoiceAddress;
    /**开户银行**/
    private String bankName;
    /**开户行**/
    private String bankBranch;
    /**银行账号**/
    private String bankAccount;


}
