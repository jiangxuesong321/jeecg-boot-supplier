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
import org.jeecg.modules.srm.entity.BiddingRequistionRelation;
import org.jeecg.modules.srm.service.IBiddingRequistionRelationService;

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
 * @Description: bidding_requistion_relation
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Api(tags="bidding_requistion_relation")
@RestController
@RequestMapping("/srm/biddingRequistionRelation")
@Slf4j
public class BiddingRequistionRelationController extends JeecgController<BiddingRequistionRelation, IBiddingRequistionRelationService> {
	@Autowired
	private IBiddingRequistionRelationService biddingRequistionRelationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param biddingRequistionRelation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "bidding_requistion_relation-分页列表查询")
	@ApiOperation(value="bidding_requistion_relation-分页列表查询", notes="bidding_requistion_relation-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BiddingRequistionRelation>> queryPageList(BiddingRequistionRelation biddingRequistionRelation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BiddingRequistionRelation> queryWrapper = QueryGenerator.initQueryWrapper(biddingRequistionRelation, req.getParameterMap());
		Page<BiddingRequistionRelation> page = new Page<BiddingRequistionRelation>(pageNo, pageSize);
		IPage<BiddingRequistionRelation> pageList = biddingRequistionRelationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param biddingRequistionRelation
	 * @return
	 */
	@AutoLog(value = "bidding_requistion_relation-添加")
	@ApiOperation(value="bidding_requistion_relation-添加", notes="bidding_requistion_relation-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BiddingRequistionRelation biddingRequistionRelation) {
		biddingRequistionRelationService.save(biddingRequistionRelation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param biddingRequistionRelation
	 * @return
	 */
	@AutoLog(value = "bidding_requistion_relation-编辑")
	@ApiOperation(value="bidding_requistion_relation-编辑", notes="bidding_requistion_relation-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BiddingRequistionRelation biddingRequistionRelation) {
		biddingRequistionRelationService.updateById(biddingRequistionRelation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "bidding_requistion_relation-通过id删除")
	@ApiOperation(value="bidding_requistion_relation-通过id删除", notes="bidding_requistion_relation-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		biddingRequistionRelationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "bidding_requistion_relation-批量删除")
	@ApiOperation(value="bidding_requistion_relation-批量删除", notes="bidding_requistion_relation-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biddingRequistionRelationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "bidding_requistion_relation-通过id查询")
	@ApiOperation(value="bidding_requistion_relation-通过id查询", notes="bidding_requistion_relation-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BiddingRequistionRelation> queryById(@RequestParam(name="id",required=true) String id) {
		BiddingRequistionRelation biddingRequistionRelation = biddingRequistionRelationService.getById(id);
		if(biddingRequistionRelation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(biddingRequistionRelation);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biddingRequistionRelation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiddingRequistionRelation biddingRequistionRelation) {
        return super.exportXls(request, biddingRequistionRelation, BiddingRequistionRelation.class, "bidding_requistion_relation");
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
        return super.importExcel(request, response, BiddingRequistionRelation.class);
    }

}
