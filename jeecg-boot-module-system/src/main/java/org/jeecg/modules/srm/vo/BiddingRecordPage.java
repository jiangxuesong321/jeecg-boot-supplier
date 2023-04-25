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
@Data
public class BiddingRecordPage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**中标供应商表ID**/
    private String brsId;
    /**招标ID**/
    private String biddingId;
    /**招标明细ID**/
    private String recordId;
    /**招标编码**/
    private String biddingNo;
    /**招标名称**/
    private String biddingName;
    /**标的**/
    private String prodCode;
    /**标的**/
    private String prodName;
    /**数量**/
    private BigDecimal qty;
    /**币种**/
    @Dict(dicCode = "currency_type")
    private String currency;
    /**单价**/
    private BigDecimal priceTax;
    /**总价**/
    private BigDecimal amountTax;
    /**议价标识**/
    private String isBargin;

    private BigDecimal taxRate;

    private String tradeType;

    private BigDecimal bgPriceTax;

    private String bbrId;

    private BigDecimal suppBgPriceTax;

    private String projectId;
    /**杂费**/
    private BigDecimal amount;
    /**增值税**/
    private BigDecimal addTax;
    /****/
    private BigDecimal customsTax;

    private String quoteRecordId;

    private String attachment;
}
