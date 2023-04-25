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
 * @Description: 招标主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@ApiModel(value="bidding_main对象", description="招标主表")
@Data
@TableName("bidding_main")
public class BiddingMain implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**招标名称*/
    @Excel(name = "招标名称", width = 15)
    @ApiModelProperty(value = "招标名称")
    private String biddingName;
    /**招标编码*/
    @Excel(name = "招标编码", width = 15)
    @ApiModelProperty(value = "招标编码")
    private String biddingNo;
    /**招标截至时间*/
    @Excel(name = "招标截至时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "招标截至时间")
    private Date biddingDeadline;
    /**招标项目ID*/
    @Excel(name = "招标项目ID", width = 15)
    @ApiModelProperty(value = "招标项目ID")
    private String projectId;
    /**招标需求ID*/
    @Excel(name = "招标需求ID", width = 15)
    @ApiModelProperty(value = "招标需求ID")
    private String requestId;
    /**评标模板ID*/
    @Excel(name = "评标模板ID", width = 15)
    @ApiModelProperty(value = "评标模板ID")
    private String biddingTemplateId;
    /**招标类型*/
    @Excel(name = "招标类型", width = 15)
    @ApiModelProperty(value = "招标类型")
    @Dict(dicCode = "bidding_type")
    private String biddingType;
    /**招标说明*/
    @Excel(name = "招标说明", width = 15)
    @ApiModelProperty(value = "招标说明")
    private String biddingDescription;
    /**招标状态*/
    @Excel(name = "招标状态", width = 15)
    @ApiModelProperty(value = "招标状态")
    @Dict(dicCode = "bidding_status")
    private String biddingStatus;
    /**投标保证金*/
    @Excel(name = "投标保证金", width = 15)
    @ApiModelProperty(value = "投标保证金")
    private java.math.BigDecimal depositCash;
    /**投标保证金截至时间*/
    @Excel(name = "投标保证金截至时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "投标保证金截至时间")
    private Date depositCashDeadline;
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
    /**开标时间*/
    @Excel(name = "开标时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开标时间")
    private Date openBiddingDate;
    /**招标时间*/
    @Excel(name = "招标时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "招标时间")
    private Date biddingDate;
    /**招标人*/
    @Excel(name = "招标人", width = 15)
    @ApiModelProperty(value = "招标人")
    private String biddingStartUser;
    /**中标时间*/
    @Excel(name = "中标时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "中标时间")
    private Date winDate;
    /**中标供应商*/
    @Excel(name = "中标供应商", width = 15)
    @ApiModelProperty(value = "中标供应商")
    private String winSupplierId;
    /**终止或者作废时间*/
    @Excel(name = "终止或者作废时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "终止或者作废时间")
    private Date voidDate;
    /**中标金额*/
    @Excel(name = "中标金额", width = 15)
    @ApiModelProperty(value = "中标金额")
    private java.math.BigDecimal biddingAmount;
    /**最终金额*/
    @Excel(name = "最终金额", width = 15)
    @ApiModelProperty(value = "最终金额")
    private java.math.BigDecimal finalAmount;
    /**报价币种*/
    @Excel(name = "报价币种", width = 15)
    @ApiModelProperty(value = "报价币种")
    private String biddingCurrency;
    /**是否含税报价*/
    @Excel(name = "是否含税报价", width = 15)
    @ApiModelProperty(value = "是否含税报价")
    private String biddingIsIncludeTax;
    /**附件**/
    private String attachment;
    /**是否已生成合同**/
    private String isContract;
    /**开标方式**/
    private String openBiddingType;
    /**评标方式**/
    private String biddingMethod;
    /**开标地址**/
    private String openBiddingAddress;
    /**评标开始时间**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date biddingStartTime;
    /**评标结束时间**/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date biddingEndTime;
    /**是否显示供应商排名**/
    private String isSort;
    /**中标公告**/
    private String isNotice;
    /**是否多次报价**/
    private String isQuotes;
    /**审核状态**/
    private String approveStatus;
    /**项目**/
    @TableField(exist = false)
    private String projName;
    /**项目编码**/
    @TableField(exist = false)
    private String projCode;
    @TableField(exist = false)
    private String reqType;

    @TableField(exist = false)
    private String reqComment;
    /**废标原因**/
    private String reason;
    /**专家评标状态**/
    @TableField(exist = false)
    private String status;

    /**开始时间**/
    @TableField(exist = false)
    private String startTime;
    /**结束时间**/
    @TableField(exist = false)
    private String endTime;
    /**操作状态**/
    @TableField(exist = false)
    private String operation;
    /**
     * 是否显示操作按钮
     */
    @TableField(exist = false)
    private String isAction;
    /**
     *供应商
     */
    @TableField(exist = false)
    private String suppId;

    /**
     *税率
     */
    @TableField(exist = false)
    private BigDecimal taxRate;
    /**
     *首付款协议
     */
    @TableField(exist = false)
    private String payMethod;

    /**
     * 币种
     */
    @TableField(exist = false)
    private String currency;
    /**
     * 贸易方式
     */
    @TableField(exist = false)
    private String tradeType;
    @TableField(exist = false)
    private String otherAttachment;

}
