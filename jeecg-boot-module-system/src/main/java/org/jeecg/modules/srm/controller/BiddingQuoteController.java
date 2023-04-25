package org.jeecg.modules.srm.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.srm.entity.BiddingQuote;
import org.jeecg.modules.srm.service.IBiddingQuoteService;

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
 * @Description: bidding_quote
 * @Author: jeecg-boot
 * @Date:   2022-10-07
 * @Version: V1.0
 */
@Api(tags="bidding_quote")
@RestController
@RequestMapping("/srm/biddingQuote")
@Slf4j
public class BiddingQuoteController extends JeecgController<BiddingQuote, IBiddingQuoteService> {
	@Autowired
	private IBiddingQuoteService biddingQuoteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param biddingQuote
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "bidding_quote-分页列表查询")
	@ApiOperation(value="bidding_quote-分页列表查询", notes="bidding_quote-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BiddingQuote>> queryPageList(BiddingQuote biddingQuote,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BiddingQuote> queryWrapper = QueryGenerator.initQueryWrapper(biddingQuote, req.getParameterMap());
		Page<BiddingQuote> page = new Page<BiddingQuote>(pageNo, pageSize);
		IPage<BiddingQuote> pageList = biddingQuoteService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param biddingQuote
	 * @return
	 */
	@AutoLog(value = "bidding_quote-添加")
	@ApiOperation(value="bidding_quote-添加", notes="bidding_quote-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BiddingQuote biddingQuote) {
		biddingQuoteService.save(biddingQuote);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param biddingQuote
	 * @return
	 */
	@AutoLog(value = "bidding_quote-编辑")
	@ApiOperation(value="bidding_quote-编辑", notes="bidding_quote-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BiddingQuote biddingQuote) {
		biddingQuoteService.updateById(biddingQuote);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bidding_quote-通过id删除")
	@ApiOperation(value="bidding_quote-通过id删除", notes="bidding_quote-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		biddingQuoteService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bidding_quote-批量删除")
	@ApiOperation(value="bidding_quote-批量删除", notes="bidding_quote-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biddingQuoteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "bidding_quote-通过id查询")
	@ApiOperation(value="bidding_quote-通过id查询", notes="bidding_quote-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BiddingQuote> queryById(@RequestParam(name="id",required=true) String id) {
		BiddingQuote biddingQuote = biddingQuoteService.getById(id);
		if(biddingQuote==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(biddingQuote);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biddingQuote
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiddingQuote biddingQuote) {
        return super.exportXls(request, biddingQuote, BiddingQuote.class, "bidding_quote");
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
        return super.importExcel(request, response, BiddingQuote.class);
    }

}
