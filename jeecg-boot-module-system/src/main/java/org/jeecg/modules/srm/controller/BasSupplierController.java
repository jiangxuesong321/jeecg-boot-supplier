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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.service.*;
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
import org.jeecg.modules.srm.vo.BasSupplierPage;
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
 * @Description: 供应商基本信息
 * @Author: jeecg-boot
 * @Date:   2022-06-16
 * @Version: V1.0
 */
@Api(tags="供应商基本信息")
@RestController
@RequestMapping("/srm/basSupplier")
@Slf4j
public class BasSupplierController {
	@Autowired
	private IBasSupplierService basSupplierService;
	@Autowired
	private IBasSupplierContactService basSupplierContactService;
	@Autowired
	private IBasSupplierQualificationService basSupplierQualificationService;
	@Autowired
	private ISupplierAccountService supplierAccountService;
	@Autowired
	private IBasSupplierBankService basSupplierBankService;
	@Autowired
	private IBasSupplierFastService iBasSupplierFastService;
	
	/**
	 * 分页列表查询
	 *
	 * @param basSupplier
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "供应商基本信息-分页列表查询")
	@ApiOperation(value="供应商基本信息-分页列表查询", notes="供应商基本信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BasSupplier>> queryPageList(BasSupplier basSupplier,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BasSupplier> queryWrapper = QueryGenerator.initQueryWrapper(basSupplier, req.getParameterMap());
		Page<BasSupplier> page = new Page<BasSupplier>(pageNo, pageSize);
		IPage<BasSupplier> pageList = basSupplierService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param basSupplierPage
	 * @return
	 */
	@AutoLog(value = "供应商基本信息-添加")
	@ApiOperation(value="供应商基本信息-添加", notes="供应商基本信息-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BasSupplierPage basSupplierPage) {
		BasSupplier basSupplier = new BasSupplier();
		BeanUtils.copyProperties(basSupplierPage, basSupplier);
		basSupplierService.saveMain(basSupplier, basSupplierPage.getBasSupplierContactList(),basSupplierPage.getBasSupplierQualificationList(),
				basSupplierPage.getBasSupplierBankList(),basSupplierPage.getBasSupplierFastList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param basSupplierPage
	 * @return
	 */
	@AutoLog(value = "供应商基本信息-编辑")
	@ApiOperation(value="供应商基本信息-编辑", notes="供应商基本信息-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BasSupplierPage basSupplierPage) {
		BasSupplier basSupplier = new BasSupplier();
		BeanUtils.copyProperties(basSupplierPage, basSupplier);
		BasSupplier basSupplierEntity = basSupplierService.getById(basSupplier.getId());
		if(basSupplierEntity==null) {
			return Result.error("未找到对应数据");
		}
		basSupplierService.updateMain(basSupplier, basSupplierPage.getBasSupplierContactList(),basSupplierPage.getBasSupplierQualificationList(),
				basSupplierPage.getBasSupplierBankList(),basSupplierPage.getBasSupplierFastList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "供应商基本信息-通过id删除")
	@ApiOperation(value="供应商基本信息-通过id删除", notes="供应商基本信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		basSupplierService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "供应商基本信息-批量删除")
	@ApiOperation(value="供应商基本信息-批量删除", notes="供应商基本信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.basSupplierService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "供应商基本信息-通过id查询")
	@ApiOperation(value="供应商基本信息-通过id查询", notes="供应商基本信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BasSupplier> queryById(@RequestParam(name="id",required=true) String id) {
		SupplierAccount acount = supplierAccountService.getById(id);
		BasSupplier basSupplier = basSupplierService.getById(acount.getSupplierId());
		if(basSupplier==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(basSupplier);

	}

	 /**
	  * 通过id查询
	  *
	  * @return
	  */
	 //@AutoLog(value = "供应商基本信息-通过id查询")
	 @ApiOperation(value="供应商基本信息-通过id查询", notes="供应商基本信息-通过id查询")
	 @GetMapping(value = "/queryBySocialCode")
	 public Result<?> queryBySocialCode(@RequestParam(name="socialCode",required=true) String socialCode) {
		 List<BasSupplier> basSupplier = basSupplierService.list(Wrappers.<BasSupplier>query().lambda().
				 eq(BasSupplier :: getSocialCode,socialCode).
				 eq(BasSupplier :: getStatus,"2"));
		 return Result.OK(basSupplier);

	 }
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "供应商联系人通过主表ID查询")
	@ApiOperation(value="供应商联系人主表ID查询", notes="供应商联系人-通主表ID查询")
	@GetMapping(value = "/queryBasSupplierContactByMainId")
	public Result<List<BasSupplierContact>> queryBasSupplierContactListByMainId(@RequestParam(name="id",required=true) String id) {
		SupplierAccount acount = supplierAccountService.getById(id);
		List<BasSupplierContact> basSupplierContactList = basSupplierContactService.selectByMainId(acount.getSupplierId());
		return Result.OK(basSupplierContactList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "供应商资质证书通过主表ID查询")
	@ApiOperation(value="供应商资质证书主表ID查询", notes="供应商资质证书-通主表ID查询")
	@GetMapping(value = "/queryBasSupplierQualificationByMainId")
	public Result<List<BasSupplierQualification>> queryBasSupplierQualificationListByMainId(@RequestParam(name="id",required=true) String id) {
		SupplierAccount acount = supplierAccountService.getById(id);
		List<BasSupplierQualification> basSupplierQualificationList = basSupplierQualificationService.selectByMainId(acount.getSupplierId());
		return Result.OK(basSupplierQualificationList);
	}

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="银行账号", notes="银行账号")
	 @GetMapping(value = "/queryBasSupplierBankByMainId")
	 public Result<List<BasSupplierBank>> queryBasSupplierBankByMainId(@RequestParam(name="id",required=true) String id) {
		 SupplierAccount acount = supplierAccountService.getById(id);
		 List<BasSupplierBank> basSupplierBankList = basSupplierBankService.selectByMainId(acount.getSupplierId());
		 return Result.OK(basSupplierBankList);
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="寄件信息", notes="寄件信息")
	 @GetMapping(value = "/queryBasSupplierFastByMainId")
	 public Result<List<BasSupplierFast>> queryBasSupplierFastByMainId(@RequestParam(name="id",required=true) String id) {
		 SupplierAccount acount = supplierAccountService.getById(id);
		 List<BasSupplierFast> basSupplierFastList = iBasSupplierFastService.list(Wrappers.<BasSupplierFast>query().lambda().
				 eq(BasSupplierFast :: getSuppId,acount.getSupplierId()).
				 eq(BasSupplierFast :: getDelFlag, CommonConstant.DEL_FLAG_0));
		 for(BasSupplierFast fast : basSupplierFastList){
			 fast.setAreaList(Arrays.asList(fast.getArea().split(",")));
		 }
		 return Result.OK(basSupplierFastList);
	 }


	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "供应商联系人通过主表ID查询")
	 @ApiOperation(value="供应商联系人主表ID查询", notes="供应商联系人-通主表ID查询")
	 @GetMapping(value = "/queryContactByMainId")
	 public Result<List<BasSupplierContact>> queryContactByMainId(@RequestParam(name="id",required=true) String id) {
		 List<BasSupplierContact> basSupplierContactList = basSupplierContactService.selectByMainId(id);
		 return Result.OK(basSupplierContactList);
	 }
	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "供应商资质证书通过主表ID查询")
	 @ApiOperation(value="供应商资质证书主表ID查询", notes="供应商资质证书-通主表ID查询")
	 @GetMapping(value = "/queryQualificationByMainId")
	 public Result<List<BasSupplierQualification>> queryQualificationByMainId(@RequestParam(name="id",required=true) String id) {
		 List<BasSupplierQualification> basSupplierQualificationList = basSupplierQualificationService.selectByMainId(id);
		 return Result.OK(basSupplierQualificationList);
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="银行账号", notes="银行账号")
	 @GetMapping(value = "/queryBankByMainId")
	 public Result<List<BasSupplierBank>> queryBankByMainId(@RequestParam(name="id",required=true) String id) {
		 List<BasSupplierBank> basSupplierBankList = basSupplierBankService.selectByMainId(id);
		 return Result.OK(basSupplierBankList);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param basSupplier
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BasSupplier basSupplier) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<BasSupplier> queryWrapper = QueryGenerator.initQueryWrapper(basSupplier, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<BasSupplier> queryList = basSupplierService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<BasSupplier> basSupplierList = new ArrayList<BasSupplier>();
      if(oConvertUtils.isEmpty(selections)) {
          basSupplierList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          basSupplierList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<BasSupplierPage> pageList = new ArrayList<BasSupplierPage>();
      for (BasSupplier main : basSupplierList) {
          BasSupplierPage vo = new BasSupplierPage();
          BeanUtils.copyProperties(main, vo);
          List<BasSupplierContact> basSupplierContactList = basSupplierContactService.selectByMainId(main.getId());
          vo.setBasSupplierContactList(basSupplierContactList);
          List<BasSupplierQualification> basSupplierQualificationList = basSupplierQualificationService.selectByMainId(main.getId());
          vo.setBasSupplierQualificationList(basSupplierQualificationList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "供应商基本信息列表");
      mv.addObject(NormalExcelConstants.CLASS, BasSupplierPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("供应商基本信息数据", "导出人:"+sysUser.getRealname(), "供应商基本信息"));
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
              List<BasSupplierPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BasSupplierPage.class, params);
              for (BasSupplierPage page : list) {
                  BasSupplier po = new BasSupplier();
                  BeanUtils.copyProperties(page, po);
                  basSupplierService.saveMain(po, page.getBasSupplierContactList(),page.getBasSupplierQualificationList(),page.getBasSupplierBankList(),page.getBasSupplierFastList());
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
