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
import org.jeecg.modules.srm.entity.PurchaseRequestDetail;
import org.jeecg.modules.srm.entity.PurchaseRequestMain;
import org.jeecg.modules.srm.vo.PurchaseRequestMainPage;
import org.jeecg.modules.srm.service.IPurchaseRequestMainService;
import org.jeecg.modules.srm.service.IPurchaseRequestDetailService;
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
 * @Description: purchase_request_main
 * @Author: jeecg-boot
 * @Date:   2022-06-17
 * @Version: V1.0
 */
@Api(tags="purchase_request_main")
@RestController
	@RequestMapping("/srm/purchaseRequestMain")
@Slf4j
public class PurchaseRequestMainController {
	@Autowired
	private IPurchaseRequestMainService purchaseRequestMainService;
	@Autowired
	private IPurchaseRequestDetailService purchaseRequestDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param purchaseRequestMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "purchase_request_main-分页列表查询")
	@ApiOperation(value="purchase_request_main-分页列表查询", notes="purchase_request_main-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PurchaseRequestMain>> queryPageList(PurchaseRequestMain purchaseRequestMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PurchaseRequestMain> queryWrapper = QueryGenerator.initQueryWrapper(purchaseRequestMain, req.getParameterMap());
		Page<PurchaseRequestMain> page = new Page<PurchaseRequestMain>(pageNo, pageSize);
		IPage<PurchaseRequestMain> pageList = purchaseRequestMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param purchaseRequestMainPage
	 * @return
	 */
	@AutoLog(value = "purchase_request_main-添加")
	@ApiOperation(value="purchase_request_main-添加", notes="purchase_request_main-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PurchaseRequestMainPage purchaseRequestMainPage) {
		PurchaseRequestMain purchaseRequestMain = new PurchaseRequestMain();
		BeanUtils.copyProperties(purchaseRequestMainPage, purchaseRequestMain);
		purchaseRequestMainService.saveMain(purchaseRequestMain, purchaseRequestMainPage.getPurchaseRequestDetailList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param purchaseRequestMainPage
	 * @return
	 */
	@AutoLog(value = "purchase_request_main-编辑")
	@ApiOperation(value="purchase_request_main-编辑", notes="purchase_request_main-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PurchaseRequestMainPage purchaseRequestMainPage) {
		PurchaseRequestMain purchaseRequestMain = new PurchaseRequestMain();
		BeanUtils.copyProperties(purchaseRequestMainPage, purchaseRequestMain);
		PurchaseRequestMain purchaseRequestMainEntity = purchaseRequestMainService.getById(purchaseRequestMain.getId());
		if(purchaseRequestMainEntity==null) {
			return Result.error("未找到对应数据");
		}
		purchaseRequestMainService.updateMain(purchaseRequestMain, purchaseRequestMainPage.getPurchaseRequestDetailList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "purchase_request_main-通过id删除")
	@ApiOperation(value="purchase_request_main-通过id删除", notes="purchase_request_main-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		purchaseRequestMainService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "purchase_request_main-批量删除")
	@ApiOperation(value="purchase_request_main-批量删除", notes="purchase_request_main-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.purchaseRequestMainService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "purchase_request_main-通过id查询")
	@ApiOperation(value="purchase_request_main-通过id查询", notes="purchase_request_main-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PurchaseRequestMain> queryById(@RequestParam(name="id",required=true) String id) {
		PurchaseRequestMain purchaseRequestMain = purchaseRequestMainService.getById(id);
		if(purchaseRequestMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(purchaseRequestMain);

	}

	 /**
	  * 通过id查询
	  *
	  * @param contractId
	  * @return
	  */
	 @GetMapping(value = "/getPurchaseRequest")
	 public Result<PurchaseRequestMain> getPurchaseRequest(@RequestParam(name="contractId",required=true) String contractId) {
		 PurchaseRequestMain purchaseRequestMain = purchaseRequestMainService.getPurchaseRequest(contractId);
		 if(purchaseRequestMain==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(purchaseRequestMain);

	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "purchase_request_detail通过主表ID查询")
	@ApiOperation(value="purchase_request_detail主表ID查询", notes="purchase_request_detail-通主表ID查询")
	@GetMapping(value = "/queryPurchaseRequestDetailByMainId")
	public Result<List<PurchaseRequestDetail>> queryPurchaseRequestDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<PurchaseRequestDetail> purchaseRequestDetailList = purchaseRequestDetailService.selectByMainId(id);
		return Result.OK(purchaseRequestDetailList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param purchaseRequestMain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PurchaseRequestMain purchaseRequestMain) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<PurchaseRequestMain> queryWrapper = QueryGenerator.initQueryWrapper(purchaseRequestMain, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<PurchaseRequestMain> queryList = purchaseRequestMainService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<PurchaseRequestMain> purchaseRequestMainList = new ArrayList<PurchaseRequestMain>();
      if(oConvertUtils.isEmpty(selections)) {
          purchaseRequestMainList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          purchaseRequestMainList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<PurchaseRequestMainPage> pageList = new ArrayList<PurchaseRequestMainPage>();
      for (PurchaseRequestMain main : purchaseRequestMainList) {
          PurchaseRequestMainPage vo = new PurchaseRequestMainPage();
          BeanUtils.copyProperties(main, vo);
          List<PurchaseRequestDetail> purchaseRequestDetailList = purchaseRequestDetailService.selectByMainId(main.getId());
          vo.setPurchaseRequestDetailList(purchaseRequestDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "purchase_request_main列表");
      mv.addObject(NormalExcelConstants.CLASS, PurchaseRequestMainPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("purchase_request_main数据", "导出人:"+sysUser.getRealname(), "purchase_request_main"));
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
              List<PurchaseRequestMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), PurchaseRequestMainPage.class, params);
              for (PurchaseRequestMainPage page : list) {
                  PurchaseRequestMain po = new PurchaseRequestMain();
                  BeanUtils.copyProperties(page, po);
                  purchaseRequestMainService.saveMain(po, page.getPurchaseRequestDetailList());
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
