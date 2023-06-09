package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.SupBargain;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: sup_bargain
 * @Author: jeecg-boot
 * @Date:   2022-09-28
 * @Version: V1.0
 */
public interface ISupBargainService extends IService<SupBargain> {
    /**
     * 更新议价
     * @param supBargain
     */
    void editBargain(SupBargain supBargain) throws Exception;
}
