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
 * @Description: bidding_quote_record
 * @Author: jeecg-boot
 * @Date:   2022-10-07
 * @Version: V1.0
 */
@Data
@TableName("bidding_quote_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bidding_quote_record对象", description="bidding_quote_record")
public class BiddingQuoteRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
    /**报价ID*/
    @Excel(name = "报价ID", width = 15)
    @ApiModelProperty(value = "报价ID")
    private java.lang.String quoteId;
	/**报价供应商*/
	@Excel(name = "报价供应商", width = 15)
    @ApiModelProperty(value = "报价供应商")
    private java.lang.String suppId;
	/**招标ID*/
	@Excel(name = "招标ID", width = 15)
    @ApiModelProperty(value = "招标ID")
    private java.lang.String biddingId;
	/**招标明细ID*/
	@Excel(name = "招标明细ID", width = 15)
    @ApiModelProperty(value = "招标明细ID")
    private java.lang.String recordId;
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
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal price;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.math.BigDecimal priceTax;
	/**小计*/
	@Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal amount;
	/**小计*/
	@Excel(name = "小计", width = 15)
    @ApiModelProperty(value = "小计")
    private java.math.BigDecimal amountTax;
    /**
     * 单价
     */
    private BigDecimal priceLocal;
    /**
     * 小计
     */
    private BigDecimal amountLocal;
    /**
     * 单价
     */
    private BigDecimal priceTaxLocal;
    /**
     * 小计
     */
    private BigDecimal amountTaxLocal;
	/**产能*/
	@Excel(name = "产能", width = 15)
    @ApiModelProperty(value = "产能")
    private java.lang.String capacity;
	/**交期*/
	@Excel(name = "交期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "交期")
    private java.util.Date leadTime;
	/**数量**/
	private BigDecimal qty;

	private Integer sort;
	/**品牌**/
	private String brandName;
    /**规格型号**/
    private String speType;

    private BigDecimal taxRate;
}
