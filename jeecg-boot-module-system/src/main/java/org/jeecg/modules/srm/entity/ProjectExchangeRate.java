package org.jeecg.modules.srm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: project_exchange_rate
 * @Author: jeecg-boot
 * @Date:   2022-09-30
 * @Version: V1.0
 */
@Data
@TableName("project_exchange_rate")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="project_exchange_rate对象", description="project_exchange_rate")
public class ProjectExchangeRate implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**源币种*/
	@Excel(name = "源币种", width = 15)
    @ApiModelProperty(value = "源币种")
    private String currencyA;
	/**源币种基数*/
	@Excel(name = "源币种基数", width = 15)
    @ApiModelProperty(value = "源币种基数")
    private BigDecimal valueA;
	/**目标币种*/
	@Excel(name = "目标币种", width = 15)
    @ApiModelProperty(value = "目标币种")
    private String currencyB;
	/**目标币种汇率*/
	@Excel(name = "目标币种汇率", width = 15)
    @ApiModelProperty(value = "目标币种汇率")
    private BigDecimal valueB;
	/**设备ID*/
	@Excel(name = "设备ID", width = 15)
    @ApiModelProperty(value = "设备ID")
    private String projectId;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
	/**删除标志位*/
	@Excel(name = "删除标志位", width = 15)
    @ApiModelProperty(value = "删除标志位")
    private String delFlag;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createUser;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
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
}
