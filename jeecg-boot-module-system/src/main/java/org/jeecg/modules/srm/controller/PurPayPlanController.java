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
import org.jeecg.modules.srm.entity.PurPayPlanDetail;
import org.jeecg.modules.srm.entity.PurPayPlan;
import org.jeecg.modules.srm.vo.PurPayPlanPage;
import org.jeecg.modules.srm.service.IPurPayPlanService;
import org.jeecg.modules.srm.service.IPurPayPlanDetailService;
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
 * @Description: 付款计划
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Api(tags="付款计划")
@RestController
@RequestMapping("/srm/purPayPlan")
@Slf4j
public class PurPayPlanController {
	@Autowired
	private IPurPayPlanService purPayPlanService;
	@Autowired
	private IPurPayPlanDetailService purPayPlanDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param purPayPlan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "付款计划-分页列表查询")
	@ApiOperation(value="付款计划-分页列表查询", notes="付款计划-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PurPayPlan>> queryPageList(PurPayPlan purPayPlan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PurPayPlan> queryWrapper = QueryGenerator.initQueryWrapper(purPayPlan, req.getParameterMap());
		Page<PurPayPlan> page = new Page<PurPayPlan>(pageNo, pageSize);
		IPage<PurPayPlan> pageList = purPayPlanService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param purPayPlanPage
	 * @return
	 */
	@AutoLog(value = "付款计划-添加")
	@ApiOperation(value="付款计划-添加", notes="付款计划-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PurPayPlanPage purPayPlanPage) {
		PurPayPlan purPayPlan = new PurPayPlan();
		BeanUtils.copyProperties(purPayPlanPage, purPayPlan);
		purPayPlanService.saveMain(purPayPlan, purPayPlanPage.getPurPayPlanDetailList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param purPayPlanPage
	 * @return
	 */
	@AutoLog(value = "付款计划-编辑")
	@ApiOperation(value="付款计划-编辑", notes="付款计划-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PurPayPlanPage purPayPlanPage) {
		PurPayPlan purPayPlan = new PurPayPlan();
		BeanUtils.copyProperties(purPayPlanPage, purPayPlan);
		PurPayPlan purPayPlanEntity = purPayPlanService.getById(purPayPlan.getId());
		if(purPayPlanEntity==null) {
			return Result.error("未找到对应数据");
		}
		purPayPlanService.updateMain(purPayPlan, purPayPlanPage.getPurPayPlanDetailList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "付款计划-通过id删除")
	@ApiOperation(value="付款计划-通过id删除", notes="付款计划-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		purPayPlanService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "付款计划-批量删除")
	@ApiOperation(value="付款计划-批量删除", notes="付款计划-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.purPayPlanService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "付款计划-通过id查询")
	@ApiOperation(value="付款计划-通过id查询", notes="付款计划-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PurPayPlan> queryById(@RequestParam(name="id",required=true) String id) {
		PurPayPlan purPayPlan = purPayPlanService.getById(id);
		if(purPayPlan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(purPayPlan);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "付款计划明细通过主表ID查询")
	@ApiOperation(value="付款计划明细主表ID查询", notes="付款计划明细-通主表ID查询")
	@GetMapping(value = "/queryPurPayPlanDetailByMainId")
	public Result<List<PurPayPlanDetail>> queryPurPayPlanDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PurPayPlanDetail> purPayPlanDetailList = purPayPlanDetailService.selectByMainId(id);
		return Result.OK(purPayPlanDetailList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param purPayPlan
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PurPayPlan purPayPlan) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PurPayPlan> queryWrapper = QueryGenerator.initQueryWrapper(purPayPlan, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PurPayPlan> queryList = purPayPlanService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PurPayPlan> purPayPlanList = new ArrayList<PurPayPlan>();
      if(oConvertUtils.isEmpty(selections)) {
          purPayPlanList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          purPayPlanList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PurPayPlanPage> pageList = new ArrayList<PurPayPlanPage>();
      for (PurPayPlan main : purPayPlanList) {
          PurPayPlanPage vo = new PurPayPlanPage();
          BeanUtils.copyProperties(main, vo);
          List<PurPayPlanDetail> purPayPlanDetailList = purPayPlanDetailService.selectByMainId(main.getId());
          vo.setPurPayPlanDetailList(purPayPlanDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "付款计划列表");
      mv.addObject(NormalExcelConstants.CLASS, PurPayPlanPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("付款计划数据", "导出人:"+sysUser.getRealname(), "付款计划"));
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
              List<PurPayPlanPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PurPayPlanPage.class, params);
              for (PurPayPlanPage page : list) {
                  PurPayPlan po = new PurPayPlan();
                  BeanUtils.copyProperties(page, po);
                  purPayPlanService.saveMain(po, page.getPurPayPlanDetailList());
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

}
