package org.jeecg.modules.srm.controller;

import java.math.BigDecimal;
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
import org.jeecg.modules.srm.entity.SupBargain;
import org.jeecg.modules.srm.entity.SupQuote;
import org.jeecg.modules.srm.entity.SupQuoteChild;
import org.jeecg.modules.srm.service.ISupBargainService;
import org.jeecg.modules.srm.service.ISupQuoteChildService;
import org.jeecg.modules.srm.service.ISupQuoteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.srm.vo.InquiryListPage;
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
 * @Description: sup_quote
 * @Author: jeecg-boot
 * @Date:   2022-09-27
 * @Version: V1.0
 */
@Api(tags="sup_quote")
@RestController
@RequestMapping("/srm/supQuote")
@Slf4j
public class SupQuoteController extends JeecgController<SupQuote, ISupQuoteService> {
	@Autowired
	private ISupQuoteService supQuoteService;
	@Autowired
	private ISupBargainService iSupBargainService;
	@Autowired
	private ISupQuoteChildService iSupQuoteChildService;
	
	/**
	 * 分页列表查询
	 *
	 * @param supQuote
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "sup_quote-分页列表查询")
	@ApiOperation(value="sup_quote-分页列表查询", notes="sup_quote-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SupQuote>> queryPageList(SupQuote supQuote,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SupQuote> queryWrapper = QueryGenerator.initQueryWrapper(supQuote, req.getParameterMap());
		Page<SupQuote> page = new Page<SupQuote>(pageNo, pageSize);
		IPage<SupQuote> pageList = supQuoteService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param page
	 * @return
	 */
	@AutoLog(value = "sup_quote-添加")
	@ApiOperation(value="sup_quote-添加", notes="sup_quote-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody InquiryListPage page) throws Exception {
		supQuoteService.saveQuote(page);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param supQuote
	 * @return
	 */
	@AutoLog(value = "sup_quote-编辑")
	@ApiOperation(value="sup_quote-编辑", notes="sup_quote-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody SupQuote supQuote) {
		supQuoteService.updateById(supQuote);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "sup_quote-通过id删除")
	@ApiOperation(value="sup_quote-通过id删除", notes="sup_quote-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		supQuoteService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "sup_quote-批量删除")
	@ApiOperation(value="sup_quote-批量删除", notes="sup_quote-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.supQuoteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "sup_quote-通过id查询")
	@ApiOperation(value="sup_quote-通过id查询", notes="sup_quote-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SupQuote> queryById(@RequestParam(name="id",required=true) String id) {
		SupQuote supQuote = supQuoteService.getById(id);
		if(supQuote==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(supQuote);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param supQuote
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SupQuote supQuote) {
        return super.exportXls(request, supQuote, SupQuote.class, "sup_quote");
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
        return super.importExcel(request, response, SupQuote.class);
    }

	 /**
	  * 通过id查询
	  *
	  * @param recordId
	  * @return
	  */
	 @GetMapping(value = "/queryQuoteByRecordId")
	 public Result<SupQuote> queryQuoteByRecordId(@RequestParam(name="recordId",required=true) String recordId) {
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String supplierId = loginUser.getSupplierId();

		 SupQuote supQuote = supQuoteService.getOne(Wrappers.<SupQuote>query().lambda().
				 eq(SupQuote :: getRecordId,recordId).
				 eq(SupQuote::getSuppId,supplierId));
		 if(supQuote==null) {
			 return Result.error("未找到对应数据");
		 }
		 List<SupQuoteChild> childList = iSupQuoteChildService.list(Wrappers.<SupQuoteChild>query().lambda().
				 eq(SupQuoteChild :: getQuoteId,supQuote.getId()).
				 eq(SupQuoteChild :: getDelFlag,CommonConstant.DEL_FLAG_0));
		 if(childList != null && childList.size() > 0){
			 supQuote.setChildList(childList);
		 }

		 //获取最新的议价记录
		 List<SupBargain> bargainList = iSupBargainService.list(Wrappers.<SupBargain>query().lambda().
				 eq(SupBargain :: getDelFlag, CommonConstant.DEL_FLAG_0).
				 eq(SupBargain :: getRecordId,recordId).
				 eq(SupBargain :: getSuppId,supplierId).
				 orderByDesc(SupBargain :: getCreateTime));

		 if(bargainList != null && bargainList.size() > 0){
		 	 //获取 中环 议价价格
			 SupBargain bargain = bargainList.get(0);
			 supQuote.setBgOrderPriceTax(bargain.getOrderPriceTax());
			 supQuote.setBgFareAmount(bargain.getFareAmount());
			 supQuote.setBargainId(bargain.getId());
			 //如果供应商已回复价格,则获取最新价格
			 for(SupBargain sbg : bargainList){
				if(sbg.getSuppOrderPriceTax() != null && sbg.getSuppOrderPriceTax().compareTo(BigDecimal.ZERO) == 1){
					supQuote.setOrderPriceTax(bargain.getSuppOrderPriceTax());
					supQuote.setOrderAmountTax(bargain.getSuppOrderAmountTax());
					supQuote.setOrderPrice(bargain.getSuppOrderPrice());
					supQuote.setOrderAmount(bargain.getSuppOrderAmount());
					supQuote.setFareAmount(bargain.getSuppFareAmount());
					break;
				}
			 }
		 }
		 return Result.OK(supQuote);
	 }


 }
