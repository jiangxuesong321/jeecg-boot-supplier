package org.jeecg.modules.srm.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.srm.entity.StkIoBillEntry;
import org.jeecg.modules.srm.entity.StkIoBill;
import org.jeecg.modules.srm.vo.StkIoBillPage;
import org.jeecg.modules.srm.service.IStkIoBillService;
import org.jeecg.modules.srm.service.IStkIoBillEntryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 入库单
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Api(tags="入库单")
@RestController
@RequestMapping("/srm/stkIoBill")
@Slf4j
public class StkIoBillController {
	@Autowired
	private IStkIoBillService stkIoBillService;
	@Autowired
	private IStkIoBillEntryService stkIoBillEntryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param stkIoBill
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "入库单-分页列表查询")
	@ApiOperation(value="入库单-分页列表查询", notes="入库单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StkIoBill>> queryPageList(StkIoBill stkIoBill,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = loginUser.getSupplierId();

		QueryWrapper<StkIoBill> queryWrapper = QueryGenerator.initQueryWrapper(stkIoBill, req.getParameterMap());
		Page<StkIoBill> page = new Page<StkIoBill>(pageNo, pageSize);
		queryWrapper.lambda().eq(StkIoBill :: getSuppId,suppId);
		IPage<StkIoBill> pageList = stkIoBillService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param stkIoBill
	 * @return
	 */
	@AutoLog(value = "入库单-添加")
	@ApiOperation(value="入库单-添加", notes="入库单-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StkIoBill stkIoBill) {
		stkIoBillService.saveMain(stkIoBill, stkIoBill.getStkIoBillEntryList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param stkIoBill
	 * @return
	 */
	@AutoLog(value = "入库单-编辑")
	@ApiOperation(value="入库单-编辑", notes="入库单-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StkIoBill stkIoBill) {
		stkIoBillService.updateMain(stkIoBill, stkIoBill.getStkIoBillEntryList());
		return Result.OK("编辑成功!");
	}

	 /**
	  *  编辑
	  *
	  * @param stkIoBill
	  * @return
	  */
	 @AutoLog(value = "入库单-承运单位编辑")
	 @ApiOperation(value="入库单-承运单位编辑", notes="入库单-承运单位编辑")
	 @RequestMapping(value = "/editFast", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> editFast(@RequestBody StkIoBill stkIoBill) {
		 stkIoBillService.updateById(stkIoBill);
		 return Result.OK("编辑成功!");
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "入库单-通过id删除")
	@ApiOperation(value="入库单-通过id删除", notes="入库单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		stkIoBillService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "入库单-批量删除")
	@ApiOperation(value="入库单-批量删除", notes="入库单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.stkIoBillService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "入库单-通过id查询")
	@ApiOperation(value="入库单-通过id查询", notes="入库单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StkIoBill> queryById(@RequestParam(name="id",required=true) String id) {
		StkIoBill stkIoBill = stkIoBillService.getById(id);
		if(stkIoBill==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(stkIoBill);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "入库单明细通过主表ID查询")
	@ApiOperation(value="入库单明细主表ID查询", notes="入库单明细-通主表ID查询")
	@GetMapping(value = "/queryStkIoBillEntryByMainId")
	public Result<List<StkIoBillEntry>> queryStkIoBillEntryListByMainId(@RequestParam(name="id",required=true) String id) {
		List<StkIoBillEntry> stkIoBillEntryList = stkIoBillEntryService.selectByMainId(id);
		return Result.OK(stkIoBillEntryList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param stkIoBill
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StkIoBill stkIoBill) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<StkIoBill> queryWrapper = QueryGenerator.initQueryWrapper(stkIoBill, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<StkIoBill> queryList = stkIoBillService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<StkIoBill> stkIoBillList = new ArrayList<StkIoBill>();
      if(oConvertUtils.isEmpty(selections)) {
          stkIoBillList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          stkIoBillList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<StkIoBillPage> pageList = new ArrayList<StkIoBillPage>();
      for (StkIoBill main : stkIoBillList) {
          StkIoBillPage vo = new StkIoBillPage();
          BeanUtils.copyProperties(main, vo);
          List<StkIoBillEntry> stkIoBillEntryList = stkIoBillEntryService.selectByMainId(main.getId());
          vo.setStkIoBillEntryList(stkIoBillEntryList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "入库单列表");
      mv.addObject(NormalExcelConstants.CLASS, StkIoBillPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("入库单数据", "导出人:"+sysUser.getRealname(), "入库单"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<StkIoBillPage> list = ExcelImportUtil.importExcel(file.getInputStream(), StkIoBillPage.class, params);
              for (StkIoBillPage page : list) {
                  StkIoBill po = new StkIoBill();
                  BeanUtils.copyProperties(page, po);
                  stkIoBillService.saveMain(po, page.getStkIoBillEntryList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

	 /**
	  *  签收单上传
	  *
	  * @param stkIoBill
	  * @return
	  */
	 @RequestMapping(value = "/uploadAttachment", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> uploadAttachment(@RequestBody StkIoBill stkIoBill) {
//	 	if(StringUtils.isEmpty(stkIoBill.getStatus())){
		stkIoBill.setStatus("0");
		stkIoBill.setSendStatus("2");
		stkIoBill.setApproverId("prod_line");
		stkIoBill.setStockIoType("0");
//		}
	 	stkIoBillService.updateById(stkIoBill);
	 	return Result.OK("编辑成功!");
	 }

}
