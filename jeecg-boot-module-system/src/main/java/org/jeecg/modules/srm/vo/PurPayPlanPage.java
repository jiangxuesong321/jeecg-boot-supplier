package org.jeecg.modules.srm.vo;

import java.util.List;
import org.jeecg.modules.srm.entity.PurPayPlan;
import org.jeecg.modules.srm.entity.PurPayPlanDetail;
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
 * @Description: 付款计划
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Data
@ApiModel(value="pur_pay_planPage对象", description="付款计划")
public class PurPayPlanPage {

	/**付款计划ID*/
	@ApiModelProperty(value = "付款计划ID")
    private String id;
	/**付款月份*/
	@Excel(name = "付款月份", width = 15)
	@ApiModelProperty(value = "付款月份")
    private String planMonth;
	/**应付款金额本币*/
	@Excel(name = "应付款金额本币", width = 15)
	@ApiModelProperty(value = "应付款金额本币")
    private java.math.BigDecimal payAmountCope;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private String comment;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	@ApiModelProperty(value = "创建人")
    private String createUser;
	/**修改人*/
	@Excel(name = "修改人", width = 15)
	@ApiModelProperty(value = "修改人")
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
	/**付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】*/
	@Excel(name = "付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】", width = 15)
	@ApiModelProperty(value = "付款状态【0：未付款；1：部分付款；2：已完成付款；3已关闭】")
    private String payStatus;
	/**付款比例*/
	@Excel(name = "付款比例", width = 15)
	@ApiModelProperty(value = "付款比例")
    private java.math.BigDecimal payRate;
	/**实际付款金额本币*/
	@Excel(name = "实际付款金额本币", width = 15)
	@ApiModelProperty(value = "实际付款金额本币")
    private java.math.BigDecimal payAmountPaid;
	/**应付款金额美元*/
	@Excel(name = "应付款金额美元", width = 15)
	@ApiModelProperty(value = "应付款金额美元")
    private java.math.BigDecimal payAmountUsd;
	/**应付款金额日元*/
	@Excel(name = "应付款金额日元", width = 15)
	@ApiModelProperty(value = "应付款金额日元")
    private java.math.BigDecimal payAmountJpy;
	/**应付款金额欧元*/
	@Excel(name = "应付款金额欧元", width = 15)
	@ApiModelProperty(value = "应付款金额欧元")
    private java.math.BigDecimal payAmountEur;

	@ExcelCollection(name="付款计划明细")
	@ApiModelProperty(value = "付款计划明细")
	private List<PurPayPlanDetail> purPayPlanDetailList;

}
