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
 * @Description: pay_to_invoice
 * @Author: jeecg-boot
 * @Date:   2022-11-02
 * @Version: V1.0
 */
@Data
@TableName("pay_to_invoice")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pay_to_invoice对象", description="pay_to_invoice")
public class PayToInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**付款申请ID*/
	@Excel(name = "付款申请ID", width = 15)
    @ApiModelProperty(value = "付款申请ID")
    private java.lang.String payApplyId;
	/**发票ID*/
	@Excel(name = "发票ID", width = 15)
    @ApiModelProperty(value = "发票ID")
    private java.lang.String invoiceId;
}
