package org.jeecg.modules.srm.vo;

import java.util.List;
import org.jeecg.modules.srm.entity.BiddingEvaluateTemplate;
import org.jeecg.modules.srm.entity.BiddingEvaluateTemplateItem;
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
 * @Description: 评标模板表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Data
@ApiModel(value="bidding_evaluate_templatePage对象", description="评标模板表")
public class BiddingEvaluateTemplatePage {

	/**id*/
	@ApiModelProperty(value = "id")
    private String id;
	/**模板名称*/
	@Excel(name = "模板名称", width = 15)
	@ApiModelProperty(value = "模板名称")
    private String templateName;
	/**模板编码*/
	@Excel(name = "模板编码", width = 15)
	@ApiModelProperty(value = "模板编码")
    private String templateCode;
	/**模板状态*/
	@Excel(name = "模板状态", width = 15)
	@ApiModelProperty(value = "模板状态")
    private String templateStatus;
	/**模板生效开始时间*/
	@Excel(name = "模板生效开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "模板生效开始时间")
    private Date templateValidDate;
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
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
	@ApiModelProperty(value = "租户ID")
    private String tenantId;

	@ExcelCollection(name="评标模板评分项表")
	@ApiModelProperty(value = "评标模板评分项表")
	private List<BiddingEvaluateTemplateItem> biddingEvaluateTemplateItemList;

}
