package org.jeecg.modules.srm.vo;

import java.util.List;
import org.jeecg.modules.srm.entity.StkIoBill;
import org.jeecg.modules.srm.entity.StkIoBillEntry;
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
 * @Description: 入库单
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Data
@ApiModel(value="stk_io_billPage对象", description="入库单")
public class StkIoBillPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**单据编号*/
	@Excel(name = "单据编号", width = 15)
	@ApiModelProperty(value = "单据编号")
    private String billNo;
	/**出入库类型(00:托管入库,01:期初导入,10:盘盈入库)*/
	@Excel(name = "出入库类型(00:托管入库,01:期初导入,10:盘盈入库)", width = 15)
	@ApiModelProperty(value = "出入库类型(00:托管入库,01:期初导入,10:盘盈入库)")
    private String stockIoType;
	/**是否自动生成*/
	@Excel(name = "是否自动生成", width = 15)
	@ApiModelProperty(value = "是否自动生成")
    private Integer isAuto;
	/**是否红字*/
	@Excel(name = "是否红字", width = 15)
	@ApiModelProperty(value = "是否红字")
    private Integer isRubric;
	/**单据日期*/
	@Excel(name = "单据日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "单据日期")
    private Date billDate;
	/**源单类型*/
	@Excel(name = "源单类型", width = 15)
	@ApiModelProperty(value = "源单类型")
    private String sourceType;
	/**源单id*/
	@Excel(name = "源单id", width = 15)
	@ApiModelProperty(value = "源单id")
    private String sourceId;
	/**源单号*/
	@Excel(name = "源单号", width = 15)
	@ApiModelProperty(value = "源单号")
    private String sourceNo;
	/**业务员*/
	@Excel(name = "业务员", width = 15)
	@ApiModelProperty(value = "业务员")
    private String clerkId;
	/**出入库经办*/
	@Excel(name = "出入库经办", width = 15)
	@ApiModelProperty(value = "出入库经办")
    private String handlerId;
	/**是否有往来*/
	@Excel(name = "是否有往来", width = 15)
	@ApiModelProperty(value = "是否有往来")
    private Integer hasRp;
	/**供应商*/
	@Excel(name = "供应商", width = 15)
	@ApiModelProperty(value = "供应商")
    private String supplierId;
	/**附件*/
	@Excel(name = "附件", width = 15)
	@ApiModelProperty(value = "附件")
    private String attachment;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
    private String remark;
	/**单据处理状态*/
	@Excel(name = "单据处理状态", width = 15)
	@ApiModelProperty(value = "单据处理状态")
    private String billProcStatus;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
	@ApiModelProperty(value = "审核人")
    private String approverId;
	/**流程id*/
	@Excel(name = "流程id", width = 15)
	@ApiModelProperty(value = "流程id")
    private String flowId;
	/**是否通过*/
	@Excel(name = "是否通过", width = 15)
	@ApiModelProperty(value = "是否通过")
    private Integer isApproved;
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
	/**版本*/
	@Excel(name = "版本", width = 15)
	@ApiModelProperty(value = "版本")
    private Integer version;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
	@ApiModelProperty(value = "租户ID")
    private String tenantId;
	/**仓库ID*/
	@Excel(name = "仓库ID", width = 15)
	@ApiModelProperty(value = "仓库ID")
    private String whId;
	/**是否启用*/
	@Excel(name = "是否启用", width = 15)
	@ApiModelProperty(value = "是否启用")
    private Integer isEnabled;
	/**供应商名称*/
	@Excel(name = "供应商名称", width = 15)
	@ApiModelProperty(value = "供应商名称")
    private String supplierName;
	/**合同ID*/
	@Excel(name = "合同ID", width = 15)
	@ApiModelProperty(value = "合同ID")
    private String contractId;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
	@ApiModelProperty(value = "项目ID")
    private String projectId;
	/**合同名称*/
	@Excel(name = "合同名称", width = 15)
	@ApiModelProperty(value = "合同名称")
    private String contractName;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
	@ApiModelProperty(value = "项目名称")
    private String projectName;

	@ExcelCollection(name="入库单明细")
	@ApiModelProperty(value = "入库单明细")
	private List<StkIoBillEntry> stkIoBillEntryList;

}
