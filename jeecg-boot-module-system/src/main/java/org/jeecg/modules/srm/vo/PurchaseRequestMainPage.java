package org.jeecg.modules.srm.vo;

import java.util.List;
import org.jeecg.modules.srm.entity.PurchaseRequestMain;
import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: purchase_request_main
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Data
@ApiModel(value="purchase_request_mainPage对象", description="purchase_request_main")
public class PurchaseRequestMainPage {

	/**请购ID*/
	@ApiModelProperty(value = "请购ID")
    private java.lang.String id;
	/**请购单号*/
	@Excel(name = "请购单号", width = 15)
	@ApiModelProperty(value = "请购单号")
    private java.lang.String reqCode;
	/**需求部门名称*/
	@Excel(name = "需求部门名称", width = 15)
	@ApiModelProperty(value = "需求部门名称")
    private java.lang.String reqOrgName;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
	@ApiModelProperty(value = "租户ID")
    private java.lang.String tenantId;
	/**排序*/
	@Excel(name = "排序", width = 15)
	@ApiModelProperty(value = "排序")
    private java.lang.Integer sort;
	/**删除标志*/
	@Excel(name = "删除标志", width = 15)
	@ApiModelProperty(value = "删除标志")
    private java.lang.String delFlag;
	/**请购日期*/
	@Excel(name = "请购日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "请购日期")
    private java.util.Date reqDate;
	/**创建用户*/
	@Excel(name = "创建用户", width = 15)
	@ApiModelProperty(value = "创建用户")
    private java.lang.String createUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
	@ApiModelProperty(value = "更新用户")
    private java.lang.String updateUser;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private java.lang.String comment;
	/**交期*/
	@Excel(name = "交期", width = 15)
	@ApiModelProperty(value = "交期")
    private java.lang.Integer leadTime;
	/**请购单状态 0：草稿 1：待审核 2：已审核 3：驳回*/
	@Excel(name = "请购单状态 0：草稿 1：待审核 2：已审核 3：驳回", width = 15)
	@ApiModelProperty(value = "请购单状态 0：草稿 1：待审核 2：已审核 3：驳回")
    private java.lang.String reqStatus;
	/**需求部门*/
	@Excel(name = "需求部门", width = 15)
	@ApiModelProperty(value = "需求部门")
    private java.lang.String reqOrgId;
	/**需求人名称*/
	@Excel(name = "需求人名称", width = 15)
	@ApiModelProperty(value = "需求人名称")
    private java.lang.String reqUserName;
	/**流程实例ID*/
	@Excel(name = "流程实例ID", width = 15)
	@ApiModelProperty(value = "流程实例ID")
    private java.lang.String processInstanceId;
	/**项目*/
	@Excel(name = "项目", width = 15)
	@ApiModelProperty(value = "项目")
    private java.lang.String projectId;
	/**请购人ID*/
	@Excel(name = "请购人ID", width = 15)
	@ApiModelProperty(value = "请购人ID")
    private java.lang.String applyUserId;
	/**需求人ID*/
	@Excel(name = "需求人ID", width = 15)
	@ApiModelProperty(value = "需求人ID")
    private java.lang.String reqUserId;
	/**请购人名称*/
	@Excel(name = "请购人名称", width = 15)
	@ApiModelProperty(value = "请购人名称")
    private java.lang.String applyUserName;
	/**请购类型*/
	@Excel(name = "请购类型", width = 15)
	@ApiModelProperty(value = "请购类型")
    private java.lang.String reqType;
	/**预算费用*/
	@Excel(name = "预算费用", width = 15)
	@ApiModelProperty(value = "预算费用")
    private java.math.BigDecimal budgetaryCost;
	/**处理类型 0:招投标 1：询价比价*/
	@Excel(name = "处理类型 0:招投标 1：询价比价", width = 15)
	@ApiModelProperty(value = "处理类型 0:招投标 1：询价比价")
    private java.lang.String dealType;

	@ExcelCollection(name="purchase_request_detail")
	@ApiModelProperty(value = "purchase_request_detail")
	private List<PurchaseRequestDetail> purchaseRequestDetailList;

}
