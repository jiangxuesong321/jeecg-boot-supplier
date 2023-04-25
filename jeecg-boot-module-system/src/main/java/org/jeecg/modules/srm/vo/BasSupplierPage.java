package org.jeecg.modules.srm.vo;

import java.util.List;

import org.jeecg.modules.srm.entity.*;
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
 * @Description: 供应商基本信息
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Data
@ApiModel(value="bas_supplierPage对象", description="供应商基本信息")
public class BasSupplierPage extends BasSupplier {

	@ExcelCollection(name="供应商联系人")
	@ApiModelProperty(value = "供应商联系人")
	private List<BasSupplierContact> basSupplierContactList;
	@ExcelCollection(name="供应商资质证书")
	@ApiModelProperty(value = "供应商资质证书")
	private List<BasSupplierQualification> basSupplierQualificationList;
	@ExcelCollection(name="银行账号")
	@ApiModelProperty(value = "银行账号")
	private List<BasSupplierBank> basSupplierBankList;
	@ExcelCollection(name="寄件人地区")
	@ApiModelProperty(value = "寄件人地区")
	private List<BasSupplierFast> basSupplierFastList;
}
