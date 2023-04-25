package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description: 询价单主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@ApiModel(value="inquiry_list对象", description="询价单主表")
@Data
@TableName("inquiry_list")
public class InquiryList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**询价单ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "询价单ID")
    private String id;
	/**询价单号*/
	@Excel(name = "询价单号", width = 15)
    @ApiModelProperty(value = "询价单号")
    private String inquiryCode;
	/**是否公开*/
	@Excel(name = "是否公开", width = 15)
    @ApiModelProperty(value = "是否公开")
    private String isPublic;
	/**价格密闭*/
	@Excel(name = "价格密闭", width = 15)
    @ApiModelProperty(value = "价格密闭")
    private String priceClosed;
	/**报价截止日期*/
	@Excel(name = "报价截止日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "报价截止日期")
    private Date quotationDeadline;
	/**询价人*/
	@Excel(name = "询价人", width = 15)
    @ApiModelProperty(value = "询价人")
    @Dict(dictTable = "sys_user", dicCode = "username", dicText="realname")
    private String inquirer;
	/**联系方式*/
	@Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
    private String inquirerTel;
	/**询价日期*/
	@Excel(name = "询价日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "询价日期")
    private Date inquiryDate;
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
	/**询价单状态（0）*/
	@Excel(name = "询价单状态（0）", width = 15)
    @ApiModelProperty(value = "询价单状态（0）")
    @Dict(dictTable = "sys_user", dicCode = "username", dicText="realname")
    private String inquiryStatus;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
    @ApiModelProperty(value = "审核人")
    private String auditUser;
	/**是否开标*/
	@Excel(name = "是否开标", width = 15)
    @ApiModelProperty(value = "是否开标")
    private String isOpening;
	/**是否自动开标*/
	@Excel(name = "是否自动开标", width = 15)
    @ApiModelProperty(value = "是否自动开标")
    private String isAutomaticOpen;
	/**是否反向竞标*/
	@Excel(name = "是否反向竞标", width = 15)
    @ApiModelProperty(value = "是否反向竞标")
    private String isReverseBidding;
	/**选择供应商原因*/
	@Excel(name = "选择供应商原因", width = 15)
    @ApiModelProperty(value = "选择供应商原因")
    private String selectSupplierReason;
	/**中标日期*/
	@Excel(name = "中标日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "中标日期")
    private Date winningDate;
	/**驳回原因*/
	@Excel(name = "驳回原因", width = 15)
    @ApiModelProperty(value = "驳回原因")
    private String rejectComment;
	/**当前报价轮次*/
	@Excel(name = "当前报价轮次", width = 15)
    @ApiModelProperty(value = "当前报价轮次")
    private Integer roundNum;
	/**流程实例ID*/
	@Excel(name = "流程实例ID", width = 15)
    @ApiModelProperty(value = "流程实例ID")
    private String processInstanceId;

    /**项目**/
    private String projectId;
}
