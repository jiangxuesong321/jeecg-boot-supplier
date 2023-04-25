package org.jeecg.modules.srm.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.srm.entity.SupplierAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.SysUser;

/**
 * @Description: 供应商账号
 * @Author: jeecg-boot
 * @Date:   2022-06-19
 * @Version: V1.0
 */
public interface ISupplierAccountService extends IService<SupplierAccount> {
    /**
     * 重置密码
     *
     * @param username
     * @param oldpassword
     * @param newpassword
     * @param confirmpassword
     * @return
     */
    public Result<?> resetPassword(String username, String oldpassword, String newpassword, String confirmpassword);

    /**
     * 修改密码
     *
     * @param sysUser
     * @return
     */
    public Result<?> changePassword(SupplierAccount sysUser);

    /**
     * 校验用户是否有效
     * @param sysUser
     * @return
     */
    Result checkUserIsEffective(SupplierAccount sysUser);

}
