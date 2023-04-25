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
 * @Description: 询价供应商表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@ApiModel(value="inquiry_supplier对象", description="询价供应商表")
@Data
@TableName("inquiry_supplier")
public class InquirySupplier implements Serializable {
    private static final long serialVersionUID = 1L;

	/**询价供应商ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "询价供应商ID")
    private String id;
	/**询比价ID*/
    @ApiModelProperty(value = "询比价ID")
    private String inquiryId;
	/**询价记录ID*/
	@Excel(name = "询价记录ID", width = 15)
    @ApiModelProperty(value = "询价记录ID")
    private String recordId;
	/**是否推荐*/
	@Excel(name = "是否推荐", width = 15)
    @ApiModelProperty(value = "是否推荐")
    private String isRecommend;
	/**状态(0:已受理、1:放弃、2未受理、3过期,4:淘汰)*/
	@Excel(name = "状态(0:已受理、1:放弃、2未受理、3过期,4:淘汰)", width = 15)
    @ApiModelProperty(value = "状态(0:已受理、1:放弃、2未受理、3过期,4:淘汰)")
    private String status;
	/**驳回供应商不能再次选择(0:正常、2:重新询价)*/
	@Excel(name = "驳回供应商不能再次选择(0:正常、2:重新询价)", width = 15)
    @ApiModelProperty(value = "驳回供应商不能再次选择(0:正常、2:重新询价)")
    private String delFlag;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String comment;
    /**询价供应商ID*/
    @ApiModelProperty(value = "询价供应商ID")
    private String supplierId;

    private String isBargin;
}
