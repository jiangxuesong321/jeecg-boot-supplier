package org.jeecg.modules.srm.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.srm.entity.ClickMenuHistory;
import org.jeecg.modules.srm.entity.SupplierPermission;
import org.jeecg.modules.srm.service.IClickMenuHistoryService;
import org.jeecg.modules.srm.service.ISupplierPermissionService;
import org.jeecg.modules.system.entity.SysPermission;
import org.jeecg.modules.system.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @Description: click_menu_history
* @Author: jeecg-boot
* @Date:   2022-10-21
* @Version: V1.0
*/
@Api(tags="click_menu_history")
@RestController
@RequestMapping("/srm/clickMenuHistory")
@Slf4j
public class ClickMenuHistoryController extends JeecgController<ClickMenuHistory, IClickMenuHistoryService> {
   @Autowired
   private IClickMenuHistoryService clickMenuHistoryService;
   @Autowired
   private ISysPermissionService iSysPermissionService;
   @Autowired
   private ISupplierPermissionService iSupplierPermissionService;

   /**
    * 分页列表查询
    *
    * @param clickMenuHistory
    * @param req
    * @return
    */
   @GetMapping(value = "/list")
   public Result<List<ClickMenuHistory>> queryList(ClickMenuHistory clickMenuHistory,
                                  HttpServletRequest req) {
       LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
       String username = loginUser.getUsername();
       clickMenuHistory.setUsername(username);
       List<ClickMenuHistory> pageList = clickMenuHistoryService.queryList(clickMenuHistory);
       return Result.OK(pageList);
   }


   /**
    *   添加
    *
    * @param clickMenuHistory
    * @return
    */
   @AutoLog(value = "click_menu_history-添加")
   @ApiOperation(value="click_menu_history-添加", notes="click_menu_history-添加")
   @PostMapping(value = "/add")
   public Result<String> add(@RequestBody ClickMenuHistory clickMenuHistory) {
       List<SupplierPermission> permissions = iSupplierPermissionService.list(Wrappers.<SupplierPermission>query().lambda().
               eq(SupplierPermission :: getDelFlag, CommonConstant.DEL_FLAG_0).
               eq(SupplierPermission :: getUrl,clickMenuHistory.getUrl()));

       LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
       String username = loginUser.getUsername();
       Date nowTime = new Date();

       if(permissions != null && permissions.size() > 0){
           SupplierPermission permission = permissions.get(0);
           if(!"首页".equals(permission)){
               clickMenuHistory.setId(String.valueOf(IdWorker.getId()));
               clickMenuHistory.setUsername(username);
               clickMenuHistory.setName(permission.getName());
               clickMenuHistory.setUrl(clickMenuHistory.getUrl());
               clickMenuHistory.setDelFlag(CommonConstant.NO_READ_FLAG);
               clickMenuHistory.setUpdateTime(nowTime);
               clickMenuHistory.setUpdateUser(username);
               clickMenuHistory.setCreateTime(nowTime);
               clickMenuHistory.setCreateUser(username);
//               clickMenuHistory.setIconName(permission.getIconName());
               clickMenuHistoryService.save(clickMenuHistory);
           }
       }

       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param clickMenuHistory
    * @return
    */
   @AutoLog(value = "click_menu_history-编辑")
   @ApiOperation(value="click_menu_history-编辑", notes="click_menu_history-编辑")
   @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
   public Result<String> edit(@RequestBody ClickMenuHistory clickMenuHistory) {
       clickMenuHistoryService.updateById(clickMenuHistory);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "click_menu_history-通过id删除")
   @ApiOperation(value="click_menu_history-通过id删除", notes="click_menu_history-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<String> delete(@RequestParam(name="id",required=true) String id) {
       clickMenuHistoryService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "click_menu_history-批量删除")
   @ApiOperation(value="click_menu_history-批量删除", notes="click_menu_history-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.clickMenuHistoryService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   //@AutoLog(value = "click_menu_history-通过id查询")
   @ApiOperation(value="click_menu_history-通过id查询", notes="click_menu_history-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<ClickMenuHistory> queryById(@RequestParam(name="id",required=true) String id) {
       ClickMenuHistory clickMenuHistory = clickMenuHistoryService.getById(id);
       if(clickMenuHistory==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(clickMenuHistory);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param clickMenuHistory
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, ClickMenuHistory clickMenuHistory) {
       return super.exportXls(request, clickMenuHistory, ClickMenuHistory.class, "click_menu_history");
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
       return super.importExcel(request, response, ClickMenuHistory.class);
   }

}
