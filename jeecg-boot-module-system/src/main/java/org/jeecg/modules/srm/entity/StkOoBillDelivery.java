package org.jeecg.modules.srm.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 出库明细
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@ApiModel(value="stk_oo_bill_delivery对象", description="出库明细")
@Data
@TableName("stk_oo_bill_delivery")
public class StkOoBillDelivery implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;
	/**主表*/
    @ApiModelProperty(value = "主表")
    private String mid;
	/**单据号*/
	@Excel(name = "单据号", width = 15)
    @ApiModelProperty(value = "单据号")
    private String billNo;
	/**分录号*/
	@Excel(name = "分录号", width = 15)
    @ApiModelProperty(value = "分录号")
    private Integer entryNo;
	/**源单类型*/
	@Excel(name = "源单类型", width = 15)
    @ApiModelProperty(value = "源单类型")
    private String sourceType;
	/**源单分录id*/
	@Excel(name = "源单分录id", width = 15)
    @ApiModelProperty(value = "源单分录id")
    private String sourceEntryId;
	/**源单分录号*/
	@Excel(name = "源单分录号", width = 15)
    @ApiModelProperty(value = "源单分录号")
    private String sourceEntryNo;
	/**物料*/
	@Excel(name = "物料", width = 15)
    @ApiModelProperty(value = "物料")
    private String materialId;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15)
    @ApiModelProperty(value = "计量单位")
    private String unitId;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.math.BigDecimal qty;
	/**库存成本*/
	@Excel(name = "库存成本", width = 15)
    @ApiModelProperty(value = "库存成本")
    private java.math.BigDecimal cost;
	/**成本是否含税*/
	@Excel(name = "成本是否含税", width = 15)
    @ApiModelProperty(value = "成本是否含税")
    private Integer isInclTax;
	/**批次号*/
	@Excel(name = "批次号", width = 15)
    @ApiModelProperty(value = "批次号")
    private String batchNo;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
    @ApiModelProperty(value = "仓库")
    private String warehouseId;
	/**调入仓库*/
	@Excel(name = "调入仓库", width = 15)
    @ApiModelProperty(value = "调入仓库")
    private String inWarehouseId;
	/**supplierId*/
	@Excel(name = "supplierId", width = 15)
    @ApiModelProperty(value = "supplierId")
    private String supplierId;
	/**结算数量*/
	@Excel(name = "结算数量", width = 15)
    @ApiModelProperty(value = "结算数量")
    private java.math.BigDecimal settleQty;
	/**含税单价*/
	@Excel(name = "含税单价", width = 15)
    @ApiModelProperty(value = "含税单价")
    private java.math.BigDecimal settlePrice;
	/**结算金额*/
	@Excel(name = "结算金额", width = 15)
    @ApiModelProperty(value = "结算金额")
    private java.math.BigDecimal settleAmt;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**修改人*/
    @ApiModelProperty(value = "修改人")
    private String updateBy;
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
	/**库区*/
	@Excel(name = "库区", width = 15)
    @ApiModelProperty(value = "库区")
    private String whaId;
	/**库位*/
	@Excel(name = "库位", width = 15)
    @ApiModelProperty(value = "库位")
    private String whlId;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private String prodName;
	/**设备编码*/
	@Excel(name = "设备编码", width = 15)
    @ApiModelProperty(value = "设备编码")
    private String prodCode;
	/**设备分类ID*/
	@Excel(name = "设备分类ID", width = 15)
    @ApiModelProperty(value = "设备分类ID")
    private String catalogId;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private String catalogName;
	/**订单ID*/
	@Excel(name = "订单ID", width = 15)
    @ApiModelProperty(value = "订单ID")
    private String orderId;
	/**订单编码*/
	@Excel(name = "订单编码", width = 15)
    @ApiModelProperty(value = "订单编码")
    private String orderNumber;
	/**订单明细ID*/
	@Excel(name = "订单明细ID", width = 15)
    @ApiModelProperty(value = "订单明细ID")
    private String orderDetailId;
	/**已退货数量*/
	@Excel(name = "已退货数量", width = 15)
    @ApiModelProperty(value = "已退货数量")
    private java.math.BigDecimal hasReturnQty;
	/**对账数量*/
	@Excel(name = "对账数量", width = 15)
    @ApiModelProperty(value = "对账数量")
    private java.math.BigDecimal verifyQty;
	/**是否已对账*/
	@Excel(name = "是否已对账", width = 15)
    @ApiModelProperty(value = "是否已对账")
    private String isVerify;
}
