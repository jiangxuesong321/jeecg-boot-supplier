package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: proj_base
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Data
@TableName("proj_base")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="proj_base对象", description="proj_base")
public class ProjBase implements Serializable {
    private static final long serialVersionUID = 1L;

	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
    @ApiModelProperty(value = "项目ID")
    private String Id;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    private String projName;
	/**项目编码*/
	@Excel(name = "项目编码", width = 15)
    @ApiModelProperty(value = "项目编码")
    private String projCode;
	/**项目类别*/
	@Excel(name = "项目类别", width = 15)
    @Dict(dicCode = "project_type")
    @ApiModelProperty(value = "项目类别")
    private String projType;
	/**项目简介*/
	@Excel(name = "项目简介", width = 15)
    @ApiModelProperty(value = "项目简介")
    private String projDescription;
	/**项目背景*/
	@Excel(name = "项目背景", width = 15)
    @ApiModelProperty(value = "项目背景")
    private String projBackground;
	/**项目立项日期*/
	@Excel(name = "项目立项日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "项目立项日期")
    private Date projInitiationDate;
	/**申请部门ID*/
	@Excel(name = "申请部门ID", width = 15)
    @ApiModelProperty(value = "申请部门ID")
    private String applyOrgId;
	/**项目状态 */
	@Excel(name = "项目状态 ", width = 15)
    @ApiModelProperty(value = "项目状态 ")
    private String projStatus;
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
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**项目经理*/
	@Excel(name = "项目经理", width = 15)
    @ApiModelProperty(value = "项目经理")
    private String projectManager;
	/**申请人ID*/
	@Excel(name = "申请人ID", width = 15)
    @ApiModelProperty(value = "申请人ID")
    private String applyUserId;
	/**申请人名称*/
	@Excel(name = "申请人名称", width = 15)
    @ApiModelProperty(value = "申请人名称")
    private String applyUserName;
	/**申请部门名称*/
	@Excel(name = "申请部门名称", width = 15)
    @ApiModelProperty(value = "申请部门名称")
    private String applyOrgName;
	/**预算金额*/
	@Excel(name = "预算金额", width = 15)
    @ApiModelProperty(value = "预算金额")
    private BigDecimal budgetAmount;
	/**冻结金额*/
	@Excel(name = "冻结金额", width = 15)
    @ApiModelProperty(value = "冻结金额")
    private BigDecimal freezeAmount;
	/**已用金额*/
	@Excel(name = "已用金额", width = 15)
    @ApiModelProperty(value = "已用金额")
    private BigDecimal usedAmount;
}
