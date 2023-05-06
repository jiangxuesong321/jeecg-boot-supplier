package org.jeecg.modules.srm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.srm.entity.ContractBase;
import org.jeecg.modules.srm.entity.ContractObject;
import org.jeecg.modules.srm.entity.ContractObjectQty;
import org.jeecg.modules.srm.entity.PurchasePayInovice;
import org.jeecg.modules.srm.service.IContractBaseService;
import org.jeecg.modules.srm.service.IPurchasePayInoviceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 发票登记
 * @Author: jeecg-boot
 * @Date:   2022-06-20
 * @Version: V1.0
 */
@Api(tags="发票登记")
@RestController
@RequestMapping("/srm/purchasePayInovice")
@Slf4j
public class PurchasePayInoviceController extends JeecgController<PurchasePayInovice, IPurchasePayInoviceService> {
	@Autowired
	private IPurchasePayInoviceService purchasePayInoviceService;
	@Autowired
	private IContractBaseService iContractBaseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param purchasePayInovice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "发票登记-分页列表查询")
	@ApiOperation(value="发票登记-分页列表查询", notes="发票登记-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PurchasePayInovice>> queryPageList(PurchasePayInovice purchasePayInovice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = loginUser.getSupplierId();

		QueryWrapper<PurchasePayInovice> queryWrapper = QueryGenerator.initQueryWrapper(purchasePayInovice, req.getParameterMap());
		Page<PurchasePayInovice> page = new Page<PurchasePayInovice>(pageNo, pageSize);
		queryWrapper.lambda().eq(PurchasePayInovice :: getDelFlag,0);
		queryWrapper.lambda().eq(PurchasePayInovice :: getSupplierId,suppId);
		IPage<PurchasePayInovice> pageList = purchasePayInoviceService.page(page, queryWrapper);
		//查询合同编码
//		List<PurchasePayInovice> ppiList = pageList.getRecords();
//		List<ContractBase> cbList = iContractBaseService.list(Wrappers.<ContractBase>query().lambda().
//				eq(ContractBase :: getDelFlag, CommonConstant.DEL_FLAG_0).
//				eq(ContractBase :: getContractSecondPartyId,suppId));
//		for(PurchasePayInovice ppi : ppiList){
//			for(ContractBase cb : cbList){
//				if(ppi.getContractId().equals(cb.getId())){
//					ppi.setContractNumber(cb.getContractNumber());
//					break;
//				}
//			}
//		}

		return Result.OK(pageList);
	}

	 /**
	  * 分页列表查询
	  *
	  * @param purchasePayInovice
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "发票登记-分页列表查询")
	 @ApiOperation(value="发票登记-分页列表查询", notes="发票登记-分页列表查询")
	 @GetMapping(value = "/fetchInvoiceList")
	 public Result<IPage<PurchasePayInovice>> fetchInvoiceList(PurchasePayInovice purchasePayInovice,
															@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															HttpServletRequest req) {
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = loginUser.getSupplierId();

		 Page<PurchasePayInovice> page = new Page<PurchasePayInovice>(pageNo, pageSize);
//		 queryWrapper.lambda().eq(PurchasePayInovice :: getDelFlag,0);
//		 queryWrapper.lambda().eq(PurchasePayInovice :: getSupplierId,suppId);
		 purchasePayInovice.setSupplierId(suppId);
		 IPage<PurchasePayInovice> ipage = purchasePayInoviceService.fetchInvoiceList(page, purchasePayInovice);
		 List<PurchasePayInovice> pageList = ipage.getRecords();
		 for(PurchasePayInovice ppi : pageList){
		 	ppi.setHasInvoiceAmount(ppi.getHasInvoiceAmount().subtract(ppi.getApplyInvoiceAmount()));
		 	ppi.setHasInvoiceAmountTax(ppi.getHasInvoiceAmountTax().subtract(ppi.getApplyInvoiceAmountTax()));
		 	ppi.setHasInvoiceAmountLocal(ppi.getHasInvoiceAmountLocal().subtract(ppi.getApplyInvoiceAmountLocal()));
		 	ppi.setHasInvoiceAmountTaxLocal(ppi.getHasInvoiceAmountTaxLocal().subtract(ppi.getApplyInvoiceAmountTaxLocal()));
		 }
		 return Result.OK(ipage);
	 }
	
	/**
	 *   添加
	 *
	 * @param purchasePayInovice
	 * @return
	 */
	@AutoLog(value = "发票登记-添加")
	@ApiOperation(value="发票登记-添加", notes="发票登记-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PurchasePayInovice purchasePayInovice) {
		purchasePayInoviceService.saveInvoice(purchasePayInovice);
		return Result.OK("添加成功！");
	}

	 /**
	  *   添加
	  *
	  * @param purchasePayInovice
	  * @return
	  */
	 @AutoLog(value = "发票登记-添加")
	 @ApiOperation(value="发票登记-添加", notes="发票登记-添加")
	 @PostMapping(value = "/draft")
	 public Result<String> draft(@RequestBody PurchasePayInovice purchasePayInovice) {
		 purchasePayInoviceService.draftInvoice(purchasePayInovice);
		 return Result.OK("添加成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param purchasePayInovice
	 * @return
	 */
	@AutoLog(value = "发票登记-编辑")
	@ApiOperation(value="发票登记-编辑", notes="发票登记-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PurchasePayInovice purchasePayInovice) {
		purchasePayInoviceService.editInvoice(purchasePayInovice);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "发票登记-通过id删除")
	@ApiOperation(value="发票登记-通过id删除", notes="发票登记-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) throws Exception {
		purchasePayInoviceService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "发票登记-批量删除")
	@ApiOperation(value="发票登记-批量删除", notes="发票登记-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.purchasePayInoviceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "发票登记-通过id查询")
	@ApiOperation(value="发票登记-通过id查询", notes="发票登记-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PurchasePayInovice> queryById(@RequestParam(name="id",required=true) String id) {
		PurchasePayInovice purchasePayInovice = purchasePayInoviceService.getById(id);
		if(purchasePayInovice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(purchasePayInovice);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param purchasePayInovice
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PurchasePayInovice purchasePayInovice) {
        return super.exportXls(request, purchasePayInovice, PurchasePayInovice.class, "发票登记");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PurchasePayInovice.class);
    }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "发票登记-通过id查询")
	 @ApiOperation(value="发票登记-通过id查询", notes="发票登记-通过id查询")
	 @GetMapping(value = "/queryPurPayInvoiceDetailByMainId")
	 public Result<?> queryPurPayInvoiceDetailByMainId(@RequestParam(name="id",required=true) String id) {
		 List<ContractObjectQty> pageList = purchasePayInoviceService.queryPurPayInvoiceDetailByMainId(id);
		 if(pageList==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(pageList);
	 }

}
