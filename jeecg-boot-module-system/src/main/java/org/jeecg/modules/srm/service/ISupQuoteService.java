package org.jeecg.modules.srm.service;

import org.jeecg.modules.srm.entity.SupQuote;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.srm.vo.InquiryListPage;

/**
 * @Description: sup_quote
 * @Author: jeecg-boot
 * @Date:   2022-09-27
 * @Version: V1.0
 */
public interface ISupQuoteService extends IService<SupQuote> {
    /**
     * 报价
     * @param page
     */
    void saveQuote(InquiryListPage page) throws Exception;
}
