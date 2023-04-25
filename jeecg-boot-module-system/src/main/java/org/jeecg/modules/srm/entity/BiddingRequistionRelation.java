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
 * @Description: bidding_requistion_relation
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Data
@TableName("bidding_requistion_relation")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bidding_requistion_relation对象", description="bidding_requistion_relation")
public class BiddingRequistionRelation implements Serializable {
    private static final long serialVersionUID = 1L;

	/**招标记录ID*/
	@Excel(name = "招标记录ID", width = 15)
    @ApiModelProperty(value = "招标记录ID")
    private String biddingRecordId;
	/**请购记录ID*/
	@Excel(name = "请购记录ID", width = 15)
    @ApiModelProperty(value = "请购记录ID")
    private String requistionRecordId;
}
