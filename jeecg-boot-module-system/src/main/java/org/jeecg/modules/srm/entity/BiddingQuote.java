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
 * @Description: bidding_quote
 * @Author: jeecg-boot
 * @Date:   2022-10-07
 * @Version: V1.0
 */
@Data
@TableName("bidding_quote")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bidding_quote对象", description="bidding_quote")
public class BiddingQuote implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**报价供应商*/
	@Excel(name = "报价供应商", width = 15)
    @ApiModelProperty(value = "报价供应商")
    private java.lang.String suppId;
	/**招标ID*/
	@Excel(name = "招标ID", width = 15)
    @ApiModelProperty(value = "招标ID")
    private java.lang.String biddingId;
	/**税率*/
	@Excel(name = "税率", width = 15)
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal taxRate;
	/**收付款协议*/
	@Excel(name = "收付款协议", width = 15)
    @ApiModelProperty(value = "收付款协议")
    private java.lang.String payMethod;
	/**币种*/
	@Excel(name = "币种", width = 15)
    @ApiModelProperty(value = "币种")
    private java.lang.String currency;
	/**是否含税*/
	@Excel(name = "是否含税", width = 15)
    @ApiModelProperty(value = "是否含税")
    private java.lang.String isTax;
	/**删除标志*/
	@Excel(name = "删除标志", width = 15)
    @ApiModelProperty(value = "删除标志")
    private java.lang.String delFlag;
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
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String attachment;
	/**贸易方式**/
	private String tradeType;
    /**运费及通过杂费总计*/
    @Excel(name = "运费及通过杂费总计", width = 15)
    @ApiModelProperty(value = "运费及通过杂费总计")
    private java.math.BigDecimal amount;
    /**增值税税率*/
    @Excel(name = "增值税税率", width = 15)
    @ApiModelProperty(value = "增值税税率")
    private java.math.BigDecimal addTax;
    /**关税税率*/
    @Excel(name = "关税税率", width = 15)
    @ApiModelProperty(value = "关税税率")
    private java.math.BigDecimal customsTax;

    private String otherAttachment;
}
