package org.jeecg.modules.srm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.srm.entity.BasRateMain;
import org.jeecg.modules.srm.entity.BasSupplier;
import org.jeecg.modules.srm.entity.BasTrade;
import org.jeecg.modules.srm.entity.BiddingBarginRecord;
import org.jeecg.modules.srm.entity.BiddingMain;
import org.jeecg.modules.srm.entity.BiddingProfessionals;
import org.jeecg.modules.srm.entity.BiddingQuote;
import org.jeecg.modules.srm.entity.BiddingQuoteRecord;
import org.jeecg.modules.srm.entity.BiddingQuoteRecordChild;
import org.jeecg.modules.srm.entity.BiddingRecord;
import org.jeecg.modules.srm.entity.BiddingRecordSupplier;
import org.jeecg.modules.srm.entity.BiddingSupplier;
import org.jeecg.modules.srm.entity.ProjectExchangeRate;
import org.jeecg.modules.srm.mapper.BiddingMainMapper;
import org.jeecg.modules.srm.mapper.BiddingProfessionalsMapper;
import org.jeecg.modules.srm.mapper.BiddingRecordMapper;
import org.jeecg.modules.srm.mapper.BiddingSupplierMapper;
import org.jeecg.modules.srm.service.IBasRateMainService;
import org.jeecg.modules.srm.service.IBasTradeService;
import org.jeecg.modules.srm.service.IBiddingBarginRecordService;
import org.jeecg.modules.srm.service.IBiddingMainService;
import org.jeecg.modules.srm.service.IBiddingQuoteRecordChildService;
import org.jeecg.modules.srm.service.IBiddingQuoteRecordService;
import org.jeecg.modules.srm.service.IBiddingQuoteService;
import org.jeecg.modules.srm.service.IBiddingRecordSupplierService;
import org.jeecg.modules.srm.service.IBiddingSupplierService;
import org.jeecg.modules.srm.service.IProjectExchangeRateService;
import org.jeecg.modules.srm.vo.BiddingMainPage;
import org.jeecg.modules.srm.vo.BiddingRecordPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 招标主表
 * @Author: jeecg-boot
 * @Date:   2022-06-18
 * @Version: V1.0
 */
@Service
public class BiddingMainServiceImpl extends ServiceImpl<BiddingMainMapper, BiddingMain> implements IBiddingMainService {

	@Autowired
	private BiddingMainMapper biddingMainMapper;
	@Autowired
	private BiddingRecordMapper biddingRecordMapper;
	@Autowired
	private BiddingSupplierMapper biddingSupplierMapper;
	@Autowired
	private BiddingProfessionalsMapper biddingProfessionalsMapper;
	@Autowired
	private IBiddingSupplierService iBiddingSupplierService;
	@Autowired
	private IBiddingQuoteService iBiddingQuoteService;
	@Autowired
	private IBiddingQuoteRecordService iBiddingQuoteRecordService;
	@Autowired
	private IBasRateMainService iBasRateMainService;
	@Autowired
	private IBiddingQuoteRecordChildService iBiddingQuoteRecordChildService;
	@Autowired
	private IProjectExchangeRateService iProjectExchangeRateService;
	@Autowired
	private IBasTradeService iBasTradeService;
	@Autowired
	private IBiddingBarginRecordService iBiddingBarginRecordService;
	@Autowired
	private IBiddingRecordSupplierService iBiddingRecordSupplierService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(BiddingMain biddingMain, List<BiddingRecord> biddingRecordList,List<BiddingSupplier> biddingSupplierList,List<BiddingProfessionals> biddingProfessionalsList) {
		biddingMainMapper.insert(biddingMain);
		if(biddingRecordList!=null && biddingRecordList.size()>0) {
			for(BiddingRecord entity:biddingRecordList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingRecordMapper.insert(entity);
			}
		}
		if(biddingSupplierList!=null && biddingSupplierList.size()>0) {
			for(BiddingSupplier entity:biddingSupplierList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingSupplierMapper.insert(entity);
			}
		}
		if(biddingProfessionalsList!=null && biddingProfessionalsList.size()>0) {
			for(BiddingProfessionals entity:biddingProfessionalsList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingProfessionalsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(BiddingMain biddingMain,List<BiddingRecord> biddingRecordList,List<BiddingSupplier> biddingSupplierList,List<BiddingProfessionals> biddingProfessionalsList) {
		biddingMainMapper.updateById(biddingMain);
		
		//1.先删除子表数据
		biddingRecordMapper.deleteByMainId(biddingMain.getId());
		biddingSupplierMapper.deleteByMainId(biddingMain.getId());
		biddingProfessionalsMapper.deleteByMainId(biddingMain.getId());
		
		//2.子表数据重新插入
		if(biddingRecordList!=null && biddingRecordList.size()>0) {
			for(BiddingRecord entity:biddingRecordList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingRecordMapper.insert(entity);
			}
		}
		if(biddingSupplierList!=null && biddingSupplierList.size()>0) {
			for(BiddingSupplier entity:biddingSupplierList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingSupplierMapper.insert(entity);
			}
		}
		if(biddingProfessionalsList!=null && biddingProfessionalsList.size()>0) {
			for(BiddingProfessionals entity:biddingProfessionalsList) {
				//外键设置
				entity.setBiddingId(biddingMain.getId());
				biddingProfessionalsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		biddingRecordMapper.deleteByMainId(id);
		biddingSupplierMapper.deleteByMainId(id);
		biddingProfessionalsMapper.deleteByMainId(id);
		biddingMainMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			biddingRecordMapper.deleteByMainId(id.toString());
			biddingSupplierMapper.deleteByMainId(id.toString());
			biddingProfessionalsMapper.deleteByMainId(id.toString());
			biddingMainMapper.deleteById(id);
		}
	}

	/**
	 * 招标列表
	 * @param page
	 * @param biddingMain
	 * @return
	 */
	@Override
	public IPage<BiddingMain> queryPageList(Page<BiddingMain> page, BiddingMain biddingMain) {
		return baseMapper.queryPageList(page,biddingMain);
	}

	/**
	 * 招标明细
	 * @param biddingMain
	 * @return
	 */
	@Override
	public List<BiddingRecord> fetchDetailList(BiddingMain biddingMain) {
		List<BiddingRecord> recordList = baseMapper.fetchDetailList(biddingMain);
		List<String> ids = new ArrayList<>();
		for(BiddingRecord br : recordList){
			ids.add(br.getRecordId());
		}
		List<BiddingQuoteRecordChild> childList = iBiddingQuoteRecordChildService.list(Wrappers.<BiddingQuoteRecordChild>query().lambda().
				in(BiddingQuoteRecordChild :: getRecordId,ids).
				eq(BiddingQuoteRecordChild :: getDelFlag,CommonConstant.DEL_FLAG_0));

		for(BiddingRecord br : recordList){
			List<BiddingQuoteRecordChild> bcList = new ArrayList<>();
			for(BiddingQuoteRecordChild bc : childList){
				if(br.getRecordId().equals(bc.getRecordId())){
					bcList.add(bc);
				}
			}
			br.setChildList(bcList);
		}
		return recordList;
	}

	/**
	 * 报价
	 * @param page
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void toQuote(BiddingMainPage page) throws Exception {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String suppId = sysUser.getSupplierId();
		String username = sysUser.getUsername();
		Date nowTime = new Date();

		//更新报价状态
		BiddingSupplier supp = iBiddingSupplierService.getOne(Wrappers.<BiddingSupplier>query().lambda().
				eq(BiddingSupplier :: getBiddingId,page.getId()).
				eq(BiddingSupplier :: getDelFlag, CommonConstant.DEL_FLAG_0).
				eq(BiddingSupplier :: getSupplierId,suppId));
		//招标
		BiddingMain main = biddingMainMapper.selectById(page.getId());
		//是否已报价
		Boolean flag = false;
		if("3".equals(supp.getStatus())){
			flag = true;
		}
		supp.setStatus("3");
		iBiddingSupplierService.updateById(supp);

		//生成报价记录
		BiddingQuote quote = new BiddingQuote();
		if(!flag){
			quote.setId(String.valueOf(IdWorker.getId()));
			quote.setCreateTime(nowTime);
			quote.setCreateUser(username);
		}else{
			quote = iBiddingQuoteService.getOne(Wrappers.<BiddingQuote>query().lambda().
					eq(BiddingQuote :: getDelFlag,CommonConstant.DEL_FLAG_0).
					eq(BiddingQuote :: getBiddingId,page.getId()).
					eq(BiddingQuote :: getSuppId,suppId));
		}
		quote.setSuppId(suppId);
		quote.setBiddingId(page.getId());
		quote.setTaxRate(page.getTaxRate());
		quote.setPayMethod(page.getPayMethod());
		quote.setCurrency(page.getCurrency());
		quote.setDelFlag(CommonConstant.NO_READ_FLAG);
		quote.setUpdateTime(nowTime);
		quote.setUpdateUser(username);
		quote.setAttachment(page.getAttachment());
		quote.setComment(page.getComment());
		quote.setTradeType(page.getTradeType());
		quote.setOtherAttachment(page.getOtherAttachment());

		//币种
		String currency = page.getCurrency();
		BigDecimal exchangeRate = BigDecimal.ZERO;
		if("RMB".equals(currency)){
			exchangeRate = BigDecimal.ONE;
		}else{
			//判断项目是否存在汇率,如果不存在则取全局汇率
			ProjectExchangeRate rate = iProjectExchangeRateService.getOne(Wrappers.<ProjectExchangeRate>query().lambda().
					eq(ProjectExchangeRate :: getCurrencyB,currency).
					eq(ProjectExchangeRate :: getDelFlag,CommonConstant.DEL_FLAG_0).
					eq(ProjectExchangeRate :: getProjectId,main.getProjectId()));
			if(rate != null){
				exchangeRate = rate.getValueB();
			}else{
				String month = DateUtils.toPrevMonth().substring(0,7);
//				String month = "";
				List<BasRateMain> mainList = iBasRateMainService.list(Wrappers.<BasRateMain>query().lambda().
						eq(BasRateMain :: getMonth,month).
						eq(BasRateMain :: getCurrencyB,currency));
				if(mainList == null || mainList.size() == 0){
					throw new Exception("请检查上月汇率是否存在");
				}
				BasRateMain bMain = mainList.get(0);
				exchangeRate = bMain.getValueB();
			}
		}

		//删除明细
		UpdateWrapper<BiddingQuoteRecord> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("del_flag",CommonConstant.ACT_SYNC_1);
		updateWrapper.eq("quote_id",quote.getId());
		iBiddingQuoteRecordService.update(updateWrapper);

		//删除配套产品
		iBiddingQuoteRecordChildService.remove(Wrappers.<BiddingQuoteRecordChild>query().lambda().eq(BiddingQuoteRecordChild :: getQuoteId,quote.getId()));

		//判断是否存在贸易方式对应的公式
		BigDecimal addTax = BigDecimal.ONE;
		BigDecimal customsTax = BigDecimal.ONE;
		BigDecimal otherAmount = BigDecimal.ZERO;
		List<BasTrade> tradeList = iBasTradeService.list(Wrappers.<BasTrade>query().lambda().eq(BasTrade :: getTradeType,page.getTradeType()));
		if((tradeList == null || tradeList.size() == 0) && !"RMB".equals(currency)){
			throw new Exception("请联系中环领先配置相应的贸易方式计算公式");
		}else{
			if(!"RMB".equals(currency)){
				BasTrade trade = tradeList.get(0);
				if("EXW".equals(trade.getTradeType()) || "FCA".equals(trade.getTradeType())
						|| "CIP".equals(trade.getTradeType()) || "CIF".equals(trade.getTradeType())){
					addTax = trade.getAddTax().divide(new BigDecimal(100));
					customsTax = trade.getCustomsTax().divide(new BigDecimal(100));
					otherAmount = trade.getAmount();
				}else if("DAP".equals(trade.getTradeType())){
					addTax = trade.getAddTax().divide(new BigDecimal(100));
					customsTax = trade.getCustomsTax().divide(new BigDecimal(100));
				}
			}
//			addTax = trade.getAddTax().divide(new BigDecimal(100));
//			customsTax = trade.getCustomsTax().divide(new BigDecimal(100));
//			otherAmount = trade.getAmount();

			quote.setAmount(otherAmount);
			quote.setAddTax(addTax);
			quote.setCustomsTax(customsTax);
		}

		//生成报价明细
		List<BiddingQuoteRecord> recordList = new ArrayList<>();
		List<BiddingQuoteRecordChild> childList = new ArrayList<>();
		for(BiddingRecord br : page.getDetailList()){
			BiddingQuoteRecord bqr = new BiddingQuoteRecord();
			bqr.setId(String.valueOf(IdWorker.getId()));
			bqr.setQuoteId(quote.getId());
			bqr.setBiddingId(page.getId());
			bqr.setSuppId(suppId);
			bqr.setRecordId(br.getId());
			bqr.setDelFlag(CommonConstant.NO_READ_FLAG);
			bqr.setCreateTime(nowTime);
			bqr.setCreateUser(username);
			bqr.setUpdateTime(nowTime);
			bqr.setUpdateUser(username);
			bqr.setComment(br.getComment());
			bqr.setQty(br.getQty());
			bqr.setPriceTax(br.getPrice());
			bqr.setSpeType(br.getSupSpeType());
			bqr.setBrandName(br.getSupBrandName());
			bqr.setTaxRate(br.getTaxRate());
			//计算含税总额
			BigDecimal amountTax = bqr.getPriceTax().multiply(bqr.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
			bqr.setAmountTax(amountTax);
			//计算未税总额
			BigDecimal taxRate = bqr.getTaxRate();
			if(taxRate.compareTo(new BigDecimal(100)) == 0){
				taxRate = BigDecimal.ZERO;
			}
			taxRate = taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));
			BigDecimal amount = amountTax.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
			bqr.setAmount(amount);
			//计算未税总额
			BigDecimal price = amount.divide(bqr.getQty(),4,BigDecimal.ROUND_HALF_UP);
			bqr.setPrice(price);
			bqr.setLeadTime(br.getSuppLeadTime());
			bqr.setCapacity(br.getSuppCapacity());

			//计算本币金额
			BigDecimal priceTaxLocal = bqr.getPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
			priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
			priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
			bqr.setPriceTaxLocal(priceTaxLocal);

			BigDecimal amountTaxLocal = priceTaxLocal.multiply(bqr.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
			bqr.setAmountTaxLocal(amountTaxLocal);

			BigDecimal amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
			bqr.setAmountLocal(amountLocal);

			BigDecimal priceLocal = amountLocal.divide(bqr.getQty(),4,BigDecimal.ROUND_HALF_UP);
			bqr.setPriceLocal(priceLocal);

			if(br.getChildList() != null && br.getChildList().size() > 0){

				BigDecimal cPrice = BigDecimal.ZERO;
				BigDecimal cAmount = BigDecimal.ZERO;

				BigDecimal cPriceLocal = BigDecimal.ZERO;
				BigDecimal cAmountLocal = BigDecimal.ZERO;


				for(BiddingQuoteRecordChild bc : br.getChildList()){
					bc.setId(String.valueOf(IdWorker.getId()));
					bc.setRecordId(bqr.getId());
					bc.setQuoteId(quote.getId());
					bc.setDelFlag(CommonConstant.NO_READ_FLAG);
					bc.setCreateTime(nowTime);
					bc.setCreateUser(username);
					bc.setUpdateTime(nowTime);
					bc.setUpdateUser(username);
					BigDecimal fareAmount = BigDecimal.ZERO;
					//计算含税总额
					amountTax = bc.getPriceTax().multiply(bc.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
					bc.setAmountTax(amountTax.add(fareAmount));
					//计算未税总额
					BigDecimal cTaxRate = bc.getTaxRate();
					if(cTaxRate.compareTo(new BigDecimal(100)) == 0){
						cTaxRate = BigDecimal.ZERO;
					}
					taxRate = cTaxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));
					amount = amountTax.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
					bc.setAmount(amount);
					//计算未税总额
					price = amount.divide(bc.getQty(),4,BigDecimal.ROUND_HALF_UP);
					bc.setPrice(price);

					//计算本币金额
					priceTaxLocal = bc.getPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
					priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
					priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
					bc.setPriceTaxLocal(priceTaxLocal);

					amountTaxLocal = priceTaxLocal.multiply(bc.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
					bc.setAmountTaxLocal(amountTaxLocal);

					amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
					bc.setAmountLocal(amountLocal);

					priceLocal = amountLocal.divide(bc.getQty(),4,BigDecimal.ROUND_HALF_UP);
					bc.setPriceLocal(priceLocal);
					childList.add(bc);

				}
			}else{
				BiddingQuoteRecordChild bc = new BiddingQuoteRecordChild();
				bc.setId(String.valueOf(IdWorker.getId()));
				bc.setRecordId(bqr.getId());
				bc.setQuoteId(quote.getId());
				bc.setDelFlag(CommonConstant.NO_READ_FLAG);
				bc.setCreateTime(nowTime);
				bc.setCreateUser(username);
				bc.setUpdateTime(nowTime);
				bc.setUpdateUser(username);
				bc.setQty(bqr.getQty());
				bc.setTaxRate(bqr.getTaxRate());
				bc.setPriceTax(bqr.getPriceTax());
				bc.setAmountTax(bqr.getAmountTax());
				bc.setPrice(bqr.getPrice());
				bc.setAmount(bqr.getAmount());
				bc.setProdName(br.getProdName());
				bc.setBrandName(bqr.getBrandName());
				bc.setSpeType(bqr.getSpeType());

				bc.setPriceTaxLocal(bqr.getPriceTaxLocal());
				bc.setAmountTaxLocal(bqr.getAmountTaxLocal());
				bc.setPriceLocal(bqr.getPriceLocal());
				bc.setAmountLocal(bqr.getAmountLocal());

				childList.add(bc);
			}

			recordList.add(bqr);
		}
		iBiddingQuoteService.saveOrUpdate(quote);
		iBiddingQuoteRecordService.saveOrUpdateBatch(recordList);
		if(childList != null && childList.size() > 0){
			iBiddingQuoteRecordChildService.saveBatch(childList);
		}
	}

	/**
	 * 获取中标供应商
	 * @param page
	 * @return
	 */
	@Override
	public BasSupplier getSuppInfo(BiddingMain page) {
		return baseMapper.getSuppInfo(page);
	}

	/**
	 * 交期排名
	 * @param id
	 * @param suppId
	 * @return
	 */
	@Override
	public String getLeadTimeRank(String id, String suppId) {
		return baseMapper.getLeadTimeRank(id,suppId);
	}

	/**
	 * 交期排名
	 * @param id
	 * @param suppId
	 * @return
	 */
	@Override
	public String getPriceRank(String id, String suppId) {
		return baseMapper.getPriceRank(id,suppId);
	}

	/**
	 * 中标议价列表
	 * @param page
	 * @param biddingMain
	 * @return
	 */
	@Override
	public IPage<BiddingRecordPage> fetchBarginList(Page<BiddingMain> page, BiddingMain biddingMain) {
		return baseMapper.fetchBarginList(page,biddingMain);
	}

	/**
	 * 议价
	 * @param page
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void submitBargin(BiddingRecordPage page) throws Exception {
		LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		String username = loginUser.getUsername();
		String supplierId = loginUser.getSupplierId();
		Date nowTime = new Date();

		//反馈议价
		BigDecimal suppBgPriceTax = page.getSuppBgPriceTax();

		//更新议价记录
		BiddingBarginRecord entity = iBiddingBarginRecordService.getById(page.getBbrId());
		entity.setUpdateTime(nowTime);
		entity.setUpdateUser(username);

		entity.setAttachment(page.getAttachment());
		//议价含税单价
		entity.setSuppBgPriceTax(suppBgPriceTax);
		BigDecimal orderPriceTax = suppBgPriceTax;
		//数量
		BigDecimal qty = entity.getQty();
		//议价含税总额
		BigDecimal orderAmountTax = orderPriceTax.multiply(qty);
		entity.setSuppBgAmountTax(orderAmountTax);

		//税率
		BigDecimal taxRate = page.getTaxRate();
		if(taxRate.compareTo(new BigDecimal(100)) == 0){
			taxRate = BigDecimal.ZERO;
		}
		taxRate = taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1));

		//议价未税总额
		BigDecimal orderAmount = orderAmountTax.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgAmount(orderAmount);

		//议价未税单价
		BigDecimal orderPrice = orderAmount.divide(qty,4,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgPrice(orderPrice);

		//币种
		String currency = page.getCurrency();
		BigDecimal exchangeRate = BigDecimal.ZERO;
		if("RMB".equals(currency)){
			exchangeRate = BigDecimal.ONE;
		}else{
			//获取项目对应的币种汇率
			ProjectExchangeRate rate = iProjectExchangeRateService.getOne(Wrappers.<ProjectExchangeRate>query().lambda().
					eq(ProjectExchangeRate :: getProjectId,page.getProjectId()).
					eq(ProjectExchangeRate :: getCurrencyB,currency));
			if(rate == null){
				String month = DateUtils.toPrevMonth().substring(0,7);
//				String month = "";
				List<BasRateMain> mainList = iBasRateMainService.list(Wrappers.<BasRateMain>query().lambda().
						eq(BasRateMain :: getMonth,month).
						eq(BasRateMain :: getCurrencyB,currency));
				if(mainList == null || mainList.size() == 0){
					throw new Exception("请检查上月汇率是否存在");
				}
				BasRateMain bMain = mainList.get(0);
				exchangeRate = bMain.getValueB();
			}else{
				exchangeRate = rate.getValueB();
			}
		}
		BigDecimal addTax = BigDecimal.ONE;
		BigDecimal customsTax = BigDecimal.ONE;
		BigDecimal otherAmount = BigDecimal.ZERO;

		if(!"RMB".equals(currency)){
			addTax = page.getAddTax();
			customsTax = page.getCustomsTax();
			otherAmount = page.getAmount();
		}
		//计算本币金额
		BigDecimal priceTaxLocal = entity.getSuppBgPriceTax().divide(exchangeRate,4,BigDecimal.ROUND_HALF_UP);
		priceTaxLocal = priceTaxLocal.multiply(addTax).multiply(customsTax).add(otherAmount);
		priceTaxLocal = priceTaxLocal.setScale(4,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgPriceTaxLocal(priceTaxLocal);

		BigDecimal amountTaxLocal = priceTaxLocal.multiply(entity.getQty()).setScale(2,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgAmountTaxLocal(amountTaxLocal);

		BigDecimal amountLocal = amountTaxLocal.divide(taxRate,2,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgAmountLocal(amountLocal);

		BigDecimal priceLocal = amountLocal.divide(entity.getQty(),4,BigDecimal.ROUND_HALF_UP);
		entity.setSuppBgPriceLocal(priceLocal);

		iBiddingBarginRecordService.updateById(entity);

		//更新状态
		BiddingRecordSupplier rs = iBiddingRecordSupplierService.getById(page.getBrsId());
		rs.setIsBargin("2");
		iBiddingRecordSupplierService.updateById(rs);

		//更新报价
		BiddingQuoteRecord record = iBiddingQuoteRecordService.getById(page.getQuoteRecordId());

		BiddingBarginRecord entity1 = new BiddingBarginRecord();
		entity1.setId(String.valueOf(IdWorker.getId()));
		entity1.setQuoteRecordId(record.getId());
		entity1.setSuppId(supplierId);
		entity1.setBiddingId(page.getBiddingId());
		entity1.setRecordId(page.getRecordId());
		entity1.setBrsId(page.getBrsId());
		entity1.setDelFlag(CommonConstant.NO_READ_FLAG);
		entity1.setCreateTime(record.getCreateTime());
		entity1.setCreateUser(record.getCreateUser());
		entity1.setUpdateTime(record.getCreateTime());
		entity1.setUpdateUser(record.getUpdateUser());
		entity1.setQty(page.getQty());

		entity1.setSuppBgPrice(record.getPrice());
		entity1.setSuppBgPriceTax(record.getPriceTax());
		entity1.setSuppBgAmount(record.getAmount());
		entity1.setSuppBgAmountTax(record.getAmountTax());
		entity1.setSuppBgPriceLocal(record.getPriceLocal());
		entity1.setSuppBgPriceTaxLocal(record.getPriceTaxLocal());
		entity1.setSuppBgAmountLocal(record.getAmountLocal());
		entity1.setSuppBgAmountTaxLocal(record.getAmountTaxLocal());
		iBiddingBarginRecordService.save(entity1);

		//反馈最新价格
		record.setPrice(entity.getSuppBgPrice());
		record.setPriceTax(entity.getSuppBgPriceTax());
		record.setAmount(entity.getSuppBgAmount());
		record.setAmountTax(entity.getSuppBgAmountTax());
		record.setPriceLocal(entity.getSuppBgPriceLocal());
		record.setPriceTaxLocal(entity.getSuppBgPriceTaxLocal());
		record.setAmountLocal(entity.getSuppBgAmountLocal());
		record.setAmountTaxLocal(entity.getSuppBgAmountTaxLocal());
		iBiddingQuoteRecordService.updateById(record);
	}

}
