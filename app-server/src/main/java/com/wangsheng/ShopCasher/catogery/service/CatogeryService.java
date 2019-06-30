package com.wangsheng.ShopCasher.catogery.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wangsheng.ShopCasher.Constant.CatogeryConstants;
import com.wangsheng.ShopCasher.catogery.dao.CatogeryDao;
import com.wangsheng.ShopCasher.catogery.data.Catogery;
import com.wangsheng.ShopCasher.catogery.info.CatogeryInfo;

@Service
public class CatogeryService {

	@Autowired
	private CatogeryDao catogeryDao;

	private static List<CatogeryInfo> catogeryInfoCache;
	
	public List<CatogeryInfo> getStructure() {

		if (CollectionUtils.isEmpty(catogeryInfoCache)) {
			Map<String, CatogeryInfo> tmpMap = new HashMap<>();

			// 初始化主类型
			List<Catogery> mainCatogeries = catogeryDao.queryByTypeAndStatus(CatogeryConstants.TYPE_BIG,
					CatogeryConstants.STATUS_EFFECTIVE);
			for (Catogery catogery : mainCatogeries) {
				CatogeryInfo catogeryInfo = new CatogeryInfo();
				catogeryInfo.setCatogery(catogery);
				tmpMap.put(catogery.getId(), catogeryInfo);
			}

			// 初始化子类型
			List<Catogery> childCatogeries = catogeryDao.queryByTypeAndStatus(CatogeryConstants.TYPE_SMALL,
					CatogeryConstants.STATUS_EFFECTIVE);
			for (Catogery catogery : childCatogeries) {
				CatogeryInfo catogeryInfo = tmpMap.get(catogery.getParentId());
				if (catogeryInfo == null) {
					continue;
				}
				List<Catogery> catogeries = catogeryInfo.getChildren();
				if (CollectionUtils.isEmpty(catogeries)) {
					catogeries = new ArrayList<>();
				}
				catogeries.add(catogery);
				catogeryInfo.setChildren(catogeries);
				tmpMap.put(catogery.getParentId(), catogeryInfo);
			}

			catogeryInfoCache = new ArrayList<>();
			for (String key : tmpMap.keySet()) {
				CatogeryInfo value = tmpMap.get(key);
				catogeryInfoCache.add(value);
			}
		}
		return catogeryInfoCache;
	}

	public List<Catogery> getByMainId(String mainId) {
		List<CatogeryInfo> catogeryInfos = getStructure();
		for (CatogeryInfo catogeryInfo : catogeryInfos) {
			if (catogeryInfo.getCatogery().getId().equals(mainId)) {
				return catogeryInfo.getChildren();
			}
		}
		return null;
	}
	
	public List<Catogery> getAll(){
		return catogeryDao.findAll();
	}
}
