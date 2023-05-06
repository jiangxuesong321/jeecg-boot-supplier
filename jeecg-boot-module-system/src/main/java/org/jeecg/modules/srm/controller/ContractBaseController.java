package org.jeecg.modules.srm.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.srm.entity.*;
import org.jeecg.modules.srm.service.*;
import org.jeecg.modules.srm.vo.ContractObjectVo;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysDepartService;
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
import org.jeecg.modules.srm.vo.ContractBasePage;
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
 * @Description: 合同基本信息表
 * @Author: jeecg-boot
 * @Date:   2022-06-21
 * @Version: V1.0
 */
@Api(tags="合同基本信息表")
@RestController
@RequestMapping("/srm/contractBase")
@Slf4j
public class ContractBaseController {
	@Autowired
	private IContractBaseService contractBaseService;
	@Autowired
	private IContractObjectService contractObjectService;
	@Autowired
	private IContractPayStepService contractPayStepService;
	@Autowired
	private IContractTermsService contractTermsService;
	@Autowired
	private IApproveRecordService iApproveRecordService;
	@Autowired
	private ISysDepartService iSysDepartService;

	/**
	 * 分页列表查询
	 *
	 * @param contractBase
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "合同基本信息表-分页列表查询")
	@ApiOperation(value="合同基本信息表-分页列表查询", notes="合同基本信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ContractBase>> queryPageList(ContractBase contractBase,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();

		QueryWrapper<ContractBase> queryWrapper = QueryGenerator.initQueryWrapper(contractBase, req.getParameterMap());
		if("1".equals(contractBase.getIsTodo())) {
			queryWrapper.lambda().ne(ContractBase::getContractStatus, CommonConstant.STATUS_0);
		} else if("0".equals(contractBase.getIsTodo())) {
			queryWrapper.lambda().in(ContractBase::getContractStatus, CommonConstant.STATUS_1);
		}
		queryWrapper.lambda().eq(ContractBase :: getDelFlag,CommonConstant.DEL_FLAG_0);
		queryWrapper.lambda().eq(ContractBase :: getContractSecondPartyId,suppId);
//		queryWrapper.lambda().isNull(ContractBase :: getMainId);
		queryWrapper.lambda().and(i -> i.isNull(ContractBase::getMainId).or().eq(ContractBase::getIsSag, "1"));
		Page<ContractBase> page = new Page<ContractBase>(pageNo, pageSize);
		IPage<ContractBase> pageList = contractBaseService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contractBasePage
	 * @return
	 */
	@AutoLog(value = "合同基本信息表-添加")
	@ApiOperation(value="合同基本信息表-添加", notes="合同基本信息表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ContractBasePage contractBasePage) {
		ContractBase contractBase = new ContractBase();
		BeanUtils.copyProperties(contractBasePage, contractBase);
		contractBaseService.saveMain(contractBase, contractBasePage.getContractObjectList(),contractBasePage.getContractPayStepList(),contractBasePage.getContractTermsList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contractBasePage
	 * @return
	 */
	@AutoLog(value = "合同基本信息表-编辑")
	@ApiOperation(value="合同基本信息表-编辑", notes="合同基本信息表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ContractBasePage contractBasePage) {
		ContractBase contractBase = new ContractBase();
		BeanUtils.copyProperties(contractBasePage, contractBase);
		ContractBase contractBaseEntity = contractBaseService.getById(contractBase.getId());
		if(contractBaseEntity==null) {
			return Result.error("未找到对应数据");
		}
		contractBaseService.updateMain(contractBase, contractBasePage.getContractObjectList(),contractBasePage.getContractPayStepList(),contractBasePage.getContractTermsList());
		return Result.OK("编辑成功!");
	}

	 /**
	  *  发货
	  *
	  * @param contractBasePage
	  * @return
	  */
	 @RequestMapping(value = "/toSend", method = {RequestMethod.PUT,RequestMethod.POST})
	 public Result<String> toSend(@RequestBody ContractBasePage contractBasePage) {
		 List<ContractObject> contractObjectList = contractBasePage.getContractObjectList();
		 for(ContractObject co : contractObjectList){
		 	co.setToSendQty(co.getToSendQty().add(co.getSendQty()));
		 }
		 contractObjectService.updateBatchById(contractObjectList);
		 return Result.OK("编辑成功!");
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "合同基本信息表-通过id删除")
	@ApiOperation(value="合同基本信息表-通过id删除", notes="合同基本信息表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		contractBaseService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "合同基本信息表-批量删除")
	@ApiOperation(value="合同基本信息表-批量删除", notes="合同基本信息表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.contractBaseService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "合同基本信息表-通过id查询")
	@ApiOperation(value="合同基本信息表-通过id查询", notes="合同基本信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ContractBase> queryById(@RequestParam(name="id",required=true) String id) {
		ContractBase contractBase = contractBaseService.getById(id);
		if(contractBase==null) {
			return Result.error("未找到对应数据");
		}
		//获取配置地址
		SysDepart depart = iSysDepartService.getById(contractBase.getContractFirstPartyId());
		if(depart != null){
			contractBase.setAddress(depart.getAddress());
		}
		return Result.OK(contractBase);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "合同标的通过主表ID查询")
	@ApiOperation(value="合同标的主表ID查询", notes="合同标的-通主表ID查询")
	@GetMapping(value = "/queryContractObjectByMainId")
	public Result<List<ContractObject>> queryContractObjectListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ContractObject> contractObjectList = contractObjectService.selectByMainId(id);
		return Result.OK(contractObjectList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "合同付款阶段通过主表ID查询")
	@ApiOperation(value="合同付款阶段主表ID查询", notes="合同付款阶段-通主表ID查询")
	@GetMapping(value = "/queryContractPayStepByMainId")
	public Result<List<ContractPayStep>> queryContractPayStepListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ContractPayStep> contractPayStepList = contractPayStepService.selectByMainId(id);
		return Result.OK(contractPayStepList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "合同条款通过主表ID查询")
	@ApiOperation(value="合同条款主表ID查询", notes="合同条款-通主表ID查询")
	@GetMapping(value = "/queryContractTermsByMainId")
	public Result<List<ContractTerms>> queryContractTermsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ContractTerms> contractTermsList = contractTermsService.selectByMainId(id);
		return Result.OK(contractTermsList);
	}

	 /**
	  * 获取审核原因
	  *
	  * @param id
	  * @return
	  */
	 @GetMapping(value = "/queryApprove")
	 public Result<ApproveRecord> queryApprove(@RequestParam(name="id",required=true) String id,
											   @RequestParam(name="type",required=false) String type) {
		 ApproveRecord record = new ApproveRecord();
		 QueryWrapper<ApproveRecord> queryWrapper = new QueryWrapper<>();
		 queryWrapper.lambda().eq(ApproveRecord :: getBusinessId,id);
		 queryWrapper.lambda().eq(ApproveRecord :: getDelFlag, CommonConstant.DEL_FLAG_0);
		 if(StringUtils.isNotEmpty(type)){
			 queryWrapper.lambda().eq(ApproveRecord :: getType,type);
		 }
		 queryWrapper.lambda().orderByDesc(ApproveRecord :: getCreateTime);

		 List<ApproveRecord> recordList = iApproveRecordService.list(queryWrapper);
		 if(recordList != null && recordList.size() > 0){
			 record = recordList.get(0);
		 }else{
			 record = null;
		 }
		 return Result.OK(record);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param contractBase
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ContractBase contractBase) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ContractBase> queryWrapper = QueryGenerator.initQueryWrapper(contractBase, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<ContractBase> queryList = contractBaseService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<ContractBase> contractBaseList = new ArrayList<ContractBase>();
      if(oConvertUtils.isEmpty(selections)) {
          contractBaseList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          contractBaseList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<ContractBasePage> pageList = new ArrayList<ContractBasePage>();
      for (ContractBase main : contractBaseList) {
          ContractBasePage vo = new ContractBasePage();
          BeanUtils.copyProperties(main, vo);
          List<ContractObject> contractObjectList = contractObjectService.selectByMainId(main.getId());
          vo.setContractObjectList(contractObjectList);
          List<ContractPayStep> contractPayStepList = contractPayStepService.selectByMainId(main.getId());
          vo.setContractPayStepList(contractPayStepList);
          List<ContractTerms> contractTermsList = contractTermsService.selectByMainId(main.getId());
          vo.setContractTermsList(contractTermsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "合同基本信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, ContractBasePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("合同基本信息表数据", "导出人:"+sysUser.getRealname(), "合同基本信息表"));
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
              List<ContractBasePage> list = ExcelImportUtil.importExcel(file.getInputStream(), ContractBasePage.class, params);
              for (ContractBasePage page : list) {
                  ContractBase po = new ContractBase();
                  BeanUtils.copyProperties(page, po);
                  contractBaseService.saveMain(po, page.getContractObjectList(),page.getContractPayStepList(),page.getContractTermsList());
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
	  * 合同明细
	  *
	  * @param contractBase
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "合同基本信息表-分页列表查询")
	 @ApiOperation(value="合同基本信息表-分页列表查询", notes="合同基本信息表-分页列表查询")
	 @GetMapping(value = "/getContractDetailList")
	 public Result<List<ContractObject>> getContractDetailList(ContractBase contractBase,
															   HttpServletRequest req) {
		 List<ContractObject> pageList = contractObjectService.getContractDetailList(contractBase);
		 return Result.OK(pageList);
	 }

	 /**
	  * 根据对象里面的属性值作in查询 属性可能会变 用户组件用到
	  * @param obj
	  * @return
	  */
	 @GetMapping("/getMultiContract")
	 public List<ContractBase> getMultiContract(ContractBase obj){
		 List<ContractBase> pageList = contractBaseService.list(Wrappers.<ContractBase>query().lambda().
				 eq(ContractBase :: getDelFlag,CommonConstant.DEL_FLAG_0).
				 in(ContractBase :: getId,obj.getId().split(",")).
				 eq(ContractBase :: getContractStatus,"4"));
		 for(ContractBase cb : pageList){
		 	if(cb.getContractName().contains("-") && cb.getContractName().split("-").length > 2){
				String contractName = cb.getContractName().split("-")[1] + "-" + cb.getContractName().split("-")[2];
				cb.setContractName(contractName);
			}
		 }
		 return pageList;
	 }

	 /**
	  * 分页列表查询
	  *
	  * @param obj
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "合同基本信息表-分页列表查询")
	 @ApiOperation(value="合同基本信息表-分页列表查询", notes="合同基本信息表-分页列表查询")
	 @GetMapping(value = "/listByPage")
	 public Result<IPage<ContractBase>> listByPage(ContractBase obj,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 QueryWrapper<ContractBase> queryWrapper = QueryGenerator.initQueryWrapper(obj, req.getParameterMap());
		 Page<ContractBase> page = new Page<ContractBase>(pageNo, pageSize);
		 queryWrapper.lambda().eq(ContractBase :: getContractStatus,"4");

		 queryWrapper.lambda().eq(ContractBase :: getContractSecondPartyId,suppId);
//		 queryWrapper.lambda().isNull(ContractBase :: getMainId);
		 queryWrapper.lambda().and(i -> i.isNull(ContractBase::getMainId).or().eq(ContractBase::getIsSag, "1"));
		 IPage<ContractBase> pageList = contractBaseService.page(page, queryWrapper);
		 List<ContractBase> cbList = pageList.getRecords();
		 List<String> ids = new ArrayList<>();
		 for(ContractBase cb : cbList){
			 ids.add(cb.getId());
		 }
		 List<ContractObject> objList = contractObjectService.list(Wrappers.<ContractObject>query().lambda().
				 eq(ContractObject :: getDelFlag,CommonConstant.DEL_FLAG_0).
				 in(ContractObject :: getContractId,ids));
		 for(ContractBase cb : cbList){
			 if(cb.getContractName().contains("-") && cb.getContractName().split("-").length > 2){
				 String contractName = cb.getContractName().split("-")[1] + "-" + cb.getContractName().split("-")[2];
				 cb.setContractName(contractName);
			 }
			 String prodName = "";
			 String qty = "";
			 for(ContractObject co : objList){
			 	if(cb.getId().equals(co.getContractId())){
					prodName = co.getProdName() + ";";
					qty = co.getQty().stripTrailingZeros().toPlainString() + ";";
				}
			 }
			 cb.setProdName(prodName);
			 cb.setQty(qty);
		 }
		 return Result.OK(pageList);
	 }

	 /**
	  * 分页列表查询
	  *
	  * @param obj
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "合同基本信息表-分页列表查询")
	 @ApiOperation(value="合同基本信息表-分页列表查询", notes="合同基本信息表-分页列表查询")
	 @GetMapping(value = "/listByDetailList")
	 public Result<IPage<ContractObject>> listByDetailList(ContractObject obj,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		 Page<ContractObject> page = new Page<ContractObject>(pageNo, pageSize);
		 IPage<ContractObject> pageList = contractObjectService.listByDetailList(page, obj);
		 return Result.OK(pageList);
	 }


	 /**
	  * 供应商中标率
	  *
	  * @param obj
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchBiddingList")
	 public Result<Map<String,Object>> fetchBiddingList(BiddingMain obj,HttpServletRequest req) {
		 Map<String,Object> map = contractObjectService.fetchBiddingList(obj);
		 return Result.OK(map);
	 }

	 /**
	  * 合同执行率
	  *
	  * @param obj
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchContractList")
	 public Result<Map<String,Object>> fetchContractList(ContractBase obj,HttpServletRequest req) {
		 Map<String,Object> map = contractBaseService.fetchContractList(obj);
		 return Result.OK(map);
	 }

	 /**
	  * 合同付款进度
	  *
	  * @param obj
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchPayList")
	 public Result<List<Map<String,Object>>> fetchPayList(ContractBase obj,HttpServletRequest req) {
		 List<Map<String,Object>> map = contractBaseService.fetchPayList(obj);
		 return Result.OK(map);
	 }

	 /**
	  * 设备到厂数量
	  *
	  * @param obj
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchEqpList")
	 public Result<List<Map<String,Object>>> fetchEqpList(BasMaterial obj,HttpServletRequest req) {
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String supplierId = loginUser.getSupplierId();
		 obj.setSupplierId(supplierId);
		 List<Map<String,Object>> map = contractBaseService.fetchEqpList(obj);
		 return Result.OK(map);
	 }

	 /**
	  * 合同报表
	  *
	  * @param contractBase
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchContractReport")
	 public Result<IPage<ContractObjectVo>> fetchContractReport(ContractBase contractBase,
																@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
																@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
																HttpServletRequest req) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 contractBase.setContractSecondPartyId(suppId);
		 Page<ContractBase> page = new Page<ContractBase>(pageNo, pageSize);
		 IPage<ContractObjectVo> pageList = contractBaseService.fetchContractReport(page, contractBase);
		 return Result.OK(pageList);
	 }

	 /**
	  * 合同报表
	  *
	  * @param contractBase
	  * @return
	  */
	 @GetMapping(value = "/exportContractReport")
	 public ModelAndView exportContractReport(HttpServletRequest request, ContractBase contractBase) {
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String suppId = sysUser.getSupplierId();

		 contractBase.setContractSecondPartyId(suppId);
		 return contractBaseService.exportContractReport(request, contractBase, ContractObjectVo.class, "合同报表");
	 }

	 /**
	  * 合同总额、付款总额、开票总额
	  *
	  * @param req
	  * @return
	  */
	 @GetMapping(value = "/fetchAmount")
	 public Result<Map<String,BigDecimal>> fetchAmount(HttpServletRequest req) {
		 LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 String supplierId = loginUser.getSupplierId();
		 Map<String, BigDecimal> map = contractBaseService.fetchAmount(supplierId);
		 return Result.OK(map);
	 }

}
