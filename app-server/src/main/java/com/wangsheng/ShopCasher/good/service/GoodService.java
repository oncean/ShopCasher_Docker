package com.wangsheng.ShopCasher.good.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.wangsheng.ShopCasher.Constant.CatogeryConstants;
import com.wangsheng.ShopCasher.catogery.data.Catogery;
import com.wangsheng.ShopCasher.catogery.service.CatogeryService;
import com.wangsheng.ShopCasher.good.Dao.GoodAbandonHisDao;
import com.wangsheng.ShopCasher.good.Dao.GoodDao;
import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.data.GoodInfoAbandonHis;
import com.wangsheng.ShopCasher.good.info.GetByCatogeryReq;
import com.wangsheng.ShopCasher.good.info.GoodInfoSnap;
import com.wangsheng.ShopCasher.good.info.QueryGoodReq;
import com.wangsheng.ShopCasher.good.info.QueryListResult;
import com.wangsheng.ShopCasher.good.info.QuerySuggestGoodReq;
import com.wangsheng.ShopCasher.good.info.WxGoodInfo;
import com.wangsheng.ShopCasher.mybatis.GoodMapper;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.comm.utils.UUIDGenerator;
import com.wangsheng.exception.ServiceException;
import com.wangsheng.wx.auth.model.WxQueryEntity;
import com.wangsheng.wx.auth.service.WxAuthService;

@Service
public class GoodService {

	@Autowired
	private GoodDao goodDao;

	@Autowired
	private GoodAbandonHisDao goodAbandonHisDao;

	@Autowired
	private CatogeryService catogeryService;

	@Autowired
	private GoodMapper goodMapper;

	@Autowired
	private WxAuthService wxAuthService;

	/**
	 * 增加商品數量
	 * 
	 * @param goodCode
	 * @param num
	 * @param shopId
	 * @throws ServiceException
	 */
	public void addGoodNum(String goodCode, Integer num, String shopId) throws ServiceException {
		GoodInfo goodinfo = goodDao.getByCodeAndShopId(goodCode, shopId);
		if (goodinfo == null) {
			throw new ServiceException(ResultConstants.GOOD_NOT_FOUND);
		}
		goodinfo.setStock(goodinfo.getStock() + num);
		goodinfo.setUpdateTime(new Date());
		goodDao.save(goodinfo);
	}

	/**
	 * 创建商品信息，初始化数量为0
	 * 
	 * @param good
	 */
	public void createGood(GoodInfo good) {
		good.setId(UUIDGenerator.randomID());
		good.setStock(0);
		good.setCreateTime(new Date());
		goodDao.save(good);
	}

	/**
	 * 创建商品信息，初始化数量为0
	 * 
	 * @param good
	 */
	public void editGood(GoodInfo good) {
		good.setUpdateTime(new Date());
		goodDao.save(good);
	}

	/**
	 * 通过商店id和商品编码查询商品信息
	 * 
	 * @param code
	 * @param shopId
	 * @return
	 * @throws ServiceException
	 */
	public GoodInfo viewGoodByCodeAndShopId(String code, String shopId) throws ServiceException {
		GoodInfo goodInfo = goodDao.getByCodeAndShopId(code, shopId);
		if (goodInfo == null) {
			throw new ServiceException(ResultConstants.GOOD_NOT_FOUND);
		}
		return goodInfo;
	}

	/**
	 * 卖出商品
	 * 
	 * @param goodId
	 * @param num
	 */
	public void saleGood(String goodId, Integer num) {
		GoodInfo goodInfo = goodDao.findOne(goodId);
		if (goodInfo.getStock() < num) {
			goodInfo.setStock(0);
		} else {
			goodInfo.setStock(goodInfo.getStock() - num);
		}
		goodInfo.setUpdateTime(new Date());
		goodDao.save(goodInfo);
	}

	/**
	 * 获取本商店的所有商品信息快照
	 * 
	 * @param shopId
	 * @return
	 */
	public List<GoodInfoSnap> getGoodSnap(String shopId) {
		List<GoodInfo> goodInfos = goodDao.queryByShopId(shopId);
		List<GoodInfoSnap> goodInfoSnaps = new ArrayList<>();
		for (GoodInfo goodInfo : goodInfos) {
			GoodInfoSnap goodInfoSnap = new GoodInfoSnap();
			BeanUtils.copyProperties(goodInfo, goodInfoSnap);
			goodInfoSnaps.add(goodInfoSnap);
		}
		return goodInfoSnaps;
	}

	public QueryListResult<GoodInfo> queryByPage(QueryGoodReq req) {
		QueryListResult<GoodInfo> result = new QueryListResult<GoodInfo>();
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setShopId(req.getShopId());
		goodInfo.setCode(req.getGoodCode());
		goodInfo.setName(req.getGoodName());

		/*
		 * //创建匹配器，即如何使用查询条件 ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
		 * .withMatcher("name", GenericPropertyMatchers.contains()) //姓名采用“包含”的方式查询
		 * .withIgnoreNullValues() .withMatcher("code",
		 * GenericPropertyMatchers.contains()) .withIgnorePaths("price","bid");
		 * Example<GoodInfo> example = Example.of(goodInfo, matcher);
		 */
		result.setList(goodMapper.queryGood(req));
		result.setTotal(goodMapper.queryGoodCount(req));
		return result;
	}

	public List<GoodInfo> querySuggestion(QuerySuggestGoodReq req) {
		return goodMapper.querySuggestion(req);
	}

	/**
	 * 根据类别查询商品
	 * 
	 * @param req
	 * @return
	 */
	public List<GoodInfo> getByCatogery(GetByCatogeryReq req) {
		String goodName = req.getGoodName();
		if (goodName == null) {
			goodName = "";
		}
		List<String> catogeryIds = new ArrayList<>();

		if (CatogeryConstants.TYPE_BIG == req.getCatogeryType()) {
			catogeryIds.add(req.getCatogeryId());
		} else {
			// 否则根据父类别查询
			List<Catogery> catogeries = catogeryService.getByMainId(req.getCatogeryId());
			for (Catogery catogery : catogeries) {
				catogeryIds.add(catogery.getId());
			}
		}
		if (CollectionUtils.isEmpty(catogeryIds)) {
			return goodDao.findByNameContainingAndShopId(goodName, req.getShopId());
		}
		return goodDao.findByCategoryInAndNameContainingAndShopId(catogeryIds, goodName, req.getShopId());
	}

	@Transactional
	public void deleteGood(String goodId) throws ServiceException {
		GoodInfo goodInfo = goodDao.findOne(goodId);
		if (null == goodInfo) {
			throw new ServiceException(ResultConstants.GOOD_NOT_FOUND);
		}
		GoodInfoAbandonHis goodInfoAbandonHis = new GoodInfoAbandonHis();
		BeanUtils.copyProperties(goodInfo, goodInfoAbandonHis);
		goodInfoAbandonHis.setId(UUIDGenerator.ID());
		goodInfoAbandonHis.setFormerId(goodId);
		goodAbandonHisDao.save(goodInfoAbandonHis);
		goodDao.delete(goodId);

	}

	@Transactional
	public void updateGoodFromWeixinServer() {
		String queryString = "db.collection(\"goods\").limit(20).get()";
		String result = wxAuthService.queryDataBase(queryString);
		JSONObject resultJson = JSONObject.parseObject(result);
		WxQueryEntity<WxGoodInfo> collection = JSON.toJavaObject(resultJson, WxQueryEntity.class);
		List<WxGoodInfo> list = collection.getData();
		for (WxGoodInfo wxGoodInfo : list) {
			GoodInfo good = goodDao.getByCodeAndShopId(wxGoodInfo.getCode(), wxGoodInfo.getShopId());
			if (good == null) {
				good = new GoodInfo();
				good.setId(UUIDGenerator.randomID());
				BeanUtils.copyProperties(wxGoodInfo, good);
				good.setPicUrl(wxGoodInfo.getPicId());
				goodDao.save(good);
			} else if (good.getUpdateTime().before(wxGoodInfo.getUpdateTime())) {
				BeanUtils.copyProperties(wxGoodInfo, good);
				good.setPicUrl(wxGoodInfo.getPicId());
				goodDao.save(good);
			} else {
				BeanUtils.copyProperties(good, wxGoodInfo);
				String jsonString = JSON.toJSONString(wxGoodInfo);
				wxAuthService.updateDateBase(
						"db.collection(\"goods\").doc(" + wxGoodInfo.get_id() + ").update({data:" + jsonString + "})");
			}
		}

	}

}
