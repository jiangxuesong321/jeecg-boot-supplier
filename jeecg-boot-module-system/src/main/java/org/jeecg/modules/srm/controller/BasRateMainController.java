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
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.srm.entity.BasRateMain;
import org.jeecg.modules.srm.entity.ProjectExchangeRate;
import org.jeecg.modules.srm.service.IBasRateMainService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.srm.service.IProjectExchangeRateService;
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
 * @Description: bas_rate_main
 * @Author: jeecg-boot
 * @Date:   2022-10-19
 * @Version: V1.0
 */
@Api(tags="bas_rate_main")
@RestController
@RequestMapping("/srm/basRateMain")
@Slf4j
public class BasRateMainController extends JeecgController<BasRateMain, IBasRateMainService> {
	@Autowired
	private IBasRateMainService basRateMainService;
	@Autowired
	private IProjectExchangeRateService iProjectExchangeRateService;

	
	/**
	 * 分页列表查询
	 *
	 * @param basRateMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "bas_rate_main-分页列表查询")
	@ApiOperation(value="bas_rate_main-分页列表查询", notes="bas_rate_main-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BasRateMain>> queryPageList(BasRateMain basRateMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BasRateMain> queryWrapper = QueryGenerator.initQueryWrapper(basRateMain, req.getParameterMap());
		Page<BasRateMain> page = new Page<BasRateMain>(pageNo, pageSize);
		IPage<BasRateMain> pageList = basRateMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param basRateMain
	 * @return
	 */
	@AutoLog(value = "bas_rate_main-添加")
	@ApiOperation(value="bas_rate_main-添加", notes="bas_rate_main-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BasRateMain basRateMain) {
		basRateMainService.save(basRateMain);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param basRateMain
	 * @return
	 */
	@AutoLog(value = "bas_rate_main-编辑")
	@ApiOperation(value="bas_rate_main-编辑", notes="bas_rate_main-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BasRateMain basRateMain) {
		basRateMainService.updateById(basRateMain);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bas_rate_main-通过id删除")
	@ApiOperation(value="bas_rate_main-通过id删除", notes="bas_rate_main-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		basRateMainService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bas_rate_main-批量删除")
	@ApiOperation(value="bas_rate_main-批量删除", notes="bas_rate_main-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.basRateMainService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "bas_rate_main-通过id查询")
	@ApiOperation(value="bas_rate_main-通过id查询", notes="bas_rate_main-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BasRateMain> queryById(@RequestParam(name="id",required=true) String id) {
		BasRateMain basRateMain = basRateMainService.getById(id);
		if(basRateMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(basRateMain);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param basRateMain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BasRateMain basRateMain) {
        return super.exportXls(request, basRateMain, BasRateMain.class, "bas_rate_main");
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
        return super.importExcel(request, response, BasRateMain.class);
    }


	 /**
	  * 获取汇率
	  *
	  * @return
	  */
	 @ApiOperation(value="bas_rate_main-通过id查询", notes="bas_rate_main-通过id查询")
	 @GetMapping(value = "/fetchRate")
	 public Result<BigDecimal> fetchRate(BasRateMain basRateMain) throws Exception {
	 	 String currency = basRateMain.getCurrencyB();
	 	 String projectId = basRateMain.getProjectId();
		 BigDecimal exchangeRate = BigDecimal.ZERO;
		 //判断项目是否存在汇率,如果不存在则取全局汇率
		 ProjectExchangeRate rate = iProjectExchangeRateService.getOne(Wrappers.<ProjectExchangeRate>query().lambda().
				 eq(ProjectExchangeRate :: getCurrencyB,currency).
				 eq(ProjectExchangeRate :: getDelFlag, CommonConstant.DEL_FLAG_0).
				 eq(ProjectExchangeRate :: getProjectId,projectId));
		 if(rate != null){
			 exchangeRate = rate.getValueB();
		 }else{
			 String month = DateUtils.toPrevMonth().substring(0,7);
			 List<BasRateMain> mainList = basRateMainService.list(Wrappers.<BasRateMain>query().lambda().
					 eq(BasRateMain :: getMonth,month).
					 eq(BasRateMain :: getCurrencyB,currency));
			 if(mainList == null || mainList.size() == 0){
				 throw new Exception("请检查上月汇率是否存在");
			 }
			 BasRateMain bMain = mainList.get(0);
			 exchangeRate = bMain.getValueB();
		 }
		 return Result.OK(exchangeRate);
	 }

}
