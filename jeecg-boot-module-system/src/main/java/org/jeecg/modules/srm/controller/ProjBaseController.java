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
import org.jeecg.modules.srm.entity.ProjBase;
import org.jeecg.modules.srm.service.IProjBaseService;

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
 * @Description: proj_base
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Api(tags="proj_base")
@RestController
@RequestMapping("/srm/projBase")
@Slf4j
public class ProjBaseController extends JeecgController<ProjBase, IProjBaseService> {
	@Autowired
	private IProjBaseService projBaseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param projBase
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "proj_base-分页列表查询")
	@ApiOperation(value="proj_base-分页列表查询", notes="proj_base-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ProjBase>> queryPageList(ProjBase projBase,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProjBase> queryWrapper = QueryGenerator.initQueryWrapper(projBase, req.getParameterMap());
		Page<ProjBase> page = new Page<ProjBase>(pageNo, pageSize);
		IPage<ProjBase> pageList = projBaseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param projBase
	 * @return
	 */
	@AutoLog(value = "proj_base-添加")
	@ApiOperation(value="proj_base-添加", notes="proj_base-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ProjBase projBase) {
		projBaseService.save(projBase);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param projBase
	 * @return
	 */
	@AutoLog(value = "proj_base-编辑")
	@ApiOperation(value="proj_base-编辑", notes="proj_base-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ProjBase projBase) {
		projBaseService.updateById(projBase);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "proj_base-通过id删除")
	@ApiOperation(value="proj_base-通过id删除", notes="proj_base-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		projBaseService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "proj_base-批量删除")
	@ApiOperation(value="proj_base-批量删除", notes="proj_base-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.projBaseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "proj_base-通过id查询")
	@ApiOperation(value="proj_base-通过id查询", notes="proj_base-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ProjBase> queryById(@RequestParam(name="id",required=true) String id) {
		ProjBase projBase = projBaseService.getById(id);
		if(projBase==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(projBase);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param projBase
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProjBase projBase) {
        return super.exportXls(request, projBase, ProjBase.class, "proj_base");
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
        return super.importExcel(request, response, ProjBase.class);
    }

}
