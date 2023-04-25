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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.service.*;
import org.jeecg.modules.srm.vo.BiddingRecordPage;
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
import org.jeecg.modules.srm.vo.BiddingMainPage;
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
 * @Description: 招标主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Api(tags="招标主表")
@RestController
@RequestMapping("/srm/biddingMain")
@Slf4j
public class BiddingMainController {
	@Autowired
	private IBiddingMainService biddingMainService;
	@Autowired
	private IBiddingRecordService biddingRecordService;
	@Autowired
	private IBiddingSupplierService biddingSupplierService;
	@Autowired
	private IBiddingProfessionalsService biddingProfessionalsService;
	@Autowired
	private IBiddingQuoteService iBiddingQuoteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param biddingMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "招标主表-分页列表查询")
	@ApiOperation(value="招标主表-分页列表查询", notes="招标主表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<BiddingMain>> queryPageList(BiddingMain biddingMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();
		biddingMain.setSuppId(suppId);
		Page<BiddingMain> page = new Page<BiddingMain>(pageNo, pageSize);
		IPage<BiddingMain> pageList = biddingMainService.queryPageList(page, biddingMain);
		return Result.OK(pageList);
	}

	 /**
	  * 中标议价列表
	  *
	  * @param biddingMain
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "招标主表-分页列表查询")
	 @ApiOperation(value="招标主表-分页列表查询", notes="招标主表-分页列表查询")
	 @GetMapping(value = "/fetchBarginList")
	 public Result<IPage<BiddingRecordPage>> fetchBarginList(BiddingMain biddingMain,
															 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															 HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();
		 biddingMain.setSuppId(suppId);
		 Page<BiddingMain> page = new Page<BiddingMain>(pageNo, pageSize);
		 IPage<BiddingRecordPage> pageList = biddingMainService.fetchBarginList(page, biddingMain);
		 return Result.OK(pageList);
	 }
	
	/**
	 *   添加
	 *
	 * @param biddingMainPage
	 * @return
	 */
	@AutoLog(value = "招标主表-添加")
	@ApiOperation(value="招标主表-添加", notes="招标主表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody BiddingMainPage biddingMainPage) throws Exception {
		biddingMainService.toQuote(biddingMainPage);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param biddingMainPage
	 * @return
	 */
	@AutoLog(value = "招标主表-编辑")
	@ApiOperation(value="招标主表-编辑", notes="招标主表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody BiddingMainPage biddingMainPage) {
		BiddingMain biddingMain = new BiddingMain();
		BeanUtils.copyProperties(biddingMainPage, biddingMain);
		BiddingMain biddingMainEntity = biddingMainService.getById(biddingMain.getId());
		if(biddingMainEntity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "招标主表-通过id删除")
	@ApiOperation(value="招标主表-通过id删除", notes="招标主表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		biddingMainService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "招标主表-批量删除")
	@ApiOperation(value="招标主表-批量删除", notes="招标主表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.biddingMainService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "招标主表-通过id查询")
	@ApiOperation(value="招标主表-通过id查询", notes="招标主表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<BiddingMain> queryById(@RequestParam(name="id",required=true) String id) {
		BiddingMain biddingMain = biddingMainService.getById(id);
		if(biddingMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(biddingMain);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "招标明细表通过主表ID查询")
	@ApiOperation(value="招标明细表主表ID查询", notes="招标明细表-通主表ID查询")
	@GetMapping(value = "/queryBiddingRecordByMainId")
	public Result<List<BiddingRecord>> queryBiddingRecordListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BiddingRecord> biddingRecordList = biddingRecordService.selectByMainId(id);
		return Result.OK(biddingRecordList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "招标邀请供应商通过主表ID查询")
	@ApiOperation(value="招标邀请供应商主表ID查询", notes="招标邀请供应商-通主表ID查询")
	@GetMapping(value = "/queryBiddingSupplierByMainId")
	public Result<List<BiddingSupplier>> queryBiddingSupplierListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BiddingSupplier> biddingSupplierList = biddingSupplierService.selectByMainId(id);
		return Result.OK(biddingSupplierList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "bidding_professionals通过主表ID查询")
	@ApiOperation(value="bidding_professionals主表ID查询", notes="bidding_professionals-通主表ID查询")
	@GetMapping(value = "/queryBiddingProfessionalsByMainId")
	public Result<List<BiddingProfessionals>> queryBiddingProfessionalsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BiddingProfessionals> biddingProfessionalsList = biddingProfessionalsService.selectByMainId(id);
		return Result.OK(biddingProfessionalsList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param biddingMain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BiddingMain biddingMain) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<BiddingMain> queryWrapper = QueryGenerator.initQueryWrapper(biddingMain, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<BiddingMain> queryList = biddingMainService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<BiddingMain> biddingMainList = new ArrayList<BiddingMain>();
      if(oConvertUtils.isEmpty(selections)) {
          biddingMainList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          biddingMainList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<BiddingMainPage> pageList = new ArrayList<BiddingMainPage>();
      for (BiddingMain main : biddingMainList) {
          BiddingMainPage vo = new BiddingMainPage();
          BeanUtils.copyProperties(main, vo);
          List<BiddingRecord> biddingRecordList = biddingRecordService.selectByMainId(main.getId());
          List<BiddingSupplier> biddingSupplierList = biddingSupplierService.selectByMainId(main.getId());
          List<BiddingProfessionals> biddingProfessionalsList = biddingProfessionalsService.selectByMainId(main.getId());
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "招标主表列表");
      mv.addObject(NormalExcelConstants.CLASS, BiddingMainPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("招标主表数据", "导出人:"+sysUser.getRealname(), "招标主表"));
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
              List<BiddingMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BiddingMainPage.class, params);
              for (BiddingMainPage page : list) {
                  BiddingMain po = new BiddingMain();
                  BeanUtils.copyProperties(page, po);
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
	  *  接受、放弃
	  *
	  * @param page
	  * @return
	  */
	 @RequestMapping(value = "/updStatus", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> updStatus(@RequestBody BiddingMainPage page) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 List<BiddingSupplier> suppList = biddingSupplierService.list(Wrappers.<BiddingSupplier>query().lambda().
				 eq(BiddingSupplier :: getDelFlag, CommonConstant.DEL_FLAG_0).
				 eq(BiddingSupplier :: getBiddingId,page.getId()).
				 eq(BiddingSupplier :: getSupplierId,suppId));
		 for(BiddingSupplier sup : suppList){
			 sup.setStatus(page.getStatus());
		 }
		 biddingSupplierService.updateBatchById(suppList);
		 return Result.OK("编辑成功!");
	 }

	 /**
	  * 招标明细
	  *
	  * @param biddingMain
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "招标主表-分页列表查询")
	 @ApiOperation(value="招标主表-分页列表查询", notes="招标主表-分页列表查询")
	 @GetMapping(value = "/fetchDetailList")
	 public Result<List<BiddingRecord>> fetchDetailList(BiddingMain biddingMain,
													 HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();
		 biddingMain.setSuppId(suppId);

		 List<BiddingRecord> pageList = biddingMainService.fetchDetailList(biddingMain);
		 return Result.OK(pageList);
	 }

	 /**
	  * 报价记录
	  *
	  * @param biddingMain
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "招标主表-分页列表查询")
	 @ApiOperation(value="招标主表-分页列表查询", notes="招标主表-分页列表查询")
	 @GetMapping(value = "/fetchQuote")
	 public Result<?> fetchQuote(BiddingMain biddingMain,HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();
		 biddingMain.setSuppId(suppId);

		 BiddingQuote pageList = iBiddingQuoteService.getOne(Wrappers.<BiddingQuote>query().lambda().
				 eq(BiddingQuote :: getBiddingId,biddingMain.getId()).
				 eq(BiddingQuote :: getDelFlag,CommonConstant.DEL_FLAG_0).
				 eq(BiddingQuote :: getSuppId,suppId));

		 return Result.OK(pageList);
	 }

	 /**
	  * 招标明细
	  *
	  * @param id
	  * @return
	  */
	 @GetMapping(value = "/queryRecordList")
	 public Result<List<BiddingRecord>> queryRecordList(@RequestParam(name="id",required=true) String id) {
		 List<BiddingRecord> biddingRecordList = biddingRecordService.queryRecordList(id);
		 return Result.OK(biddingRecordList);
	 }

	 /**
	  *  获取中标供应商信息
	  *
	  * @param page
	  * @return
	  */
	 @GetMapping(value = "/getSuppInfo")
	 public Result<BasSupplier> getSuppInfo(BiddingMain page) {
		 BasSupplier supp = biddingMainService.getSuppInfo(page);
		 return Result.OK(supp);
	 }

	 /**
	  * 交期排名
	  *
	  * @param id
	  * @return
	  */
	 @GetMapping(value = "/getLeadTimeRank")
	 public Result<String> getLeadTimeRank(@RequestParam(name="id",required=true) String id) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 String rank = biddingMainService.getLeadTimeRank(id,suppId);
		 return Result.OK(rank);

	 }

	 /**
	  * 报价排名
	  *
	  * @param id
	  * @return
	  */
	 @GetMapping(value = "/getPriceRank")
	 public Result<String> getPriceRank(@RequestParam(name="id",required=true) String id) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 String rank = biddingMainService.getPriceRank(id,suppId);
		 return Result.OK(rank);

	 }

	 /**
	  *   议价
	  *
	  * @param page
	  * @return
	  */
	 @AutoLog(value = "议价")
	 @PostMapping(value = "/submitBargin")
	 public Result<String> submitBargin(@RequestBody BiddingRecordPage page) throws Exception {
		 biddingMainService.submitBargin(page);
		 return Result.OK("提交成功！");
	 }

}
