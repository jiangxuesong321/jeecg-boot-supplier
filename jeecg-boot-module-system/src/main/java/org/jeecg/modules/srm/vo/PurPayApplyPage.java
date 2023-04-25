package org.jeecg.modules.srm.vo;

import java.util.List;
import org.jeecg.modules.srm.entity.PurPayApply;
import org.jeecg.modules.srm.entity.PurPayApplyDetail;
import lombok.Data;
import org.jeecg.modules.srm.entity.PurchaseApplyInvoice;
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
 * @Description: 付款申请
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Data
@ApiModel(value="pur_pay_applyPage对象", description="付款申请")
public class PurPayApplyPage extends PurPayApply{

	private List<PurPayApplyDetail> purPayApplyDetailList;

	private List<PurchaseApplyInvoice> applyInvoiceList;
}
