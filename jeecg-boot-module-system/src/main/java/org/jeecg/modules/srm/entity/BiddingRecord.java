package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description: 招标明细表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@ApiModel(value="bidding_record对象", description="招标明细表")
@Data
@TableName("bidding_record")
public class BiddingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**招标记录ID*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "招标记录ID")
    private String id;
    /**招标ID*/
    @ApiModelProperty(value = "招标ID")
    private String biddingId;
    /**招标物品ID*/
    @Excel(name = "招标物品ID", width = 15)
    @ApiModelProperty(value = "招标物品ID")
    private String prodId;
    /**招标物品名称*/
    @Excel(name = "招标物品名称", width = 15)
    @ApiModelProperty(value = "招标物品名称")
    private String prodName;
    /**招标物品编码*/
    @Excel(name = "招标物品编码", width = 15)
    @ApiModelProperty(value = "招标物品编码")
    private String prodCode;
    /**规格型号*/
    @Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private String speType;
    /**需求记录ID*/
    @Excel(name = "需求记录ID", width = 15)
    @ApiModelProperty(value = "需求记录ID")
    private String toRecordId;
    /**数量*/
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.math.BigDecimal qty;
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
    /**中标供应商ID*/
    @Excel(name = "中标供应商ID", width = 15)
    @ApiModelProperty(value = "中标供应商ID")
    private String suppId;
    /**驳回原因*/
    @Excel(name = "驳回原因", width = 15)
    @ApiModelProperty(value = "驳回原因")
    private String backReason;
    /**设备类型**/
    @TableField(exist = false)
    @Dict(dicCode = "model")
    private String model;
    /**招标供应商**/
    @TableField(exist = false)
    private String num;
    /**评标模板**/
    @TableField(exist = false)
    private List<BiddingProfessionals> templateList;
    /**设备产能**/
    @TableField(exist = false)
    private String capacity;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 小计
     */
    private BigDecimal amount;
    /**
     * 单价
     */
    private BigDecimal priceTax;
    /**
     * 小计
     */
    private BigDecimal amountTax;

    /**
     * 产能
     */
    @TableField(exist = false)
    private String suppCapacity;
    /**
     * 交期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @TableField(exist = false)
    private Date suppLeadTime;
    /**配套产品**/
    @TableField(exist = false)
    List<BiddingQuoteRecordChild> childList;
    /**报价明细ID*/
    @TableField(exist = false)
    private String recordId;
    /**供应商规格**/
    @TableField(exist = false)
    private String supSpeType;
    /**供应商品牌**/
    @TableField(exist = false)
    private String supBrandName;
    /**税率**/
    @TableField(exist = false)
    private BigDecimal taxRate;
}
