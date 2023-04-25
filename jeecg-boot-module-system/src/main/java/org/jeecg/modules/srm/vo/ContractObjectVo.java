package org.jeecg.modules.srm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.srm.entity.ContractObjectQty;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 合同标的
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
@Data
public class ContractObjectVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Excel(name = "合同编码", width = 30)
    private String contractNumber;
    @Excel(name = "合同名称", width = 30)
    private String contractName;
    @Excel(name = "合同签订日期", width = 30,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date contractSignDate;
    @Excel(name = "设备名称", width = 30)
    private String prodName;
    @Excel(name = "设备型号", width = 30)
    private String prodSpecType;
    @Excel(name = "数量", width = 30)
    private BigDecimal qty;
    @Excel(name = "单价", width = 30)
    private BigDecimal contractPriceTax;
    @Excel(name = "总额", width = 30)
    private BigDecimal contractAmountTax;
    @Excel(name = "付款方式", width = 30)
    private String payMethod;
    @Excel(name = "付款金额", width = 30)
    private String payAmount;
    @Excel(name = "付款比例(%)", width = 30)
    private String payRate;
    @Excel(name = "发货数量", width = 30)
    private BigDecimal sqty;
    @Excel(name = "开票金额", width = 30)
    private String invoiceAmount;
    @Excel(name = "开票号码", width = 40)
    private String invoiceNo;

    private String prodCode;

    private String contractCurrency;
}
