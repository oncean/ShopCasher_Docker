package com.wangsheng.ShopCasher.shop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangsheng.ShopCasher.Constant.ShopStatusConstants;
import com.wangsheng.ShopCasher.Constant.ShopUserRelationType;
import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;
import com.wangsheng.ShopCasher.settlement.service.SettlementService;
import com.wangsheng.ShopCasher.shop.Dao.ShopDao;
import com.wangsheng.ShopCasher.shop.Dao.ShopRelationDao;
import com.wangsheng.ShopCasher.shop.data.ShopEntity;
import com.wangsheng.ShopCasher.shop.data.ShopRelationEntity;
import com.wangsheng.ShopCasher.shop.info.ShopRelationDetail;
import com.wangsheng.comm.utils.UUIDGenerator;

@Service
public class ShopService {

	@Autowired
	private ShopDao shopDao;

	@Autowired
	private ShopRelationDao shopRelationDao;

	@Autowired
	private SettlementService settlementService;

	/**
	 * 创建商店，并且更新用户为拥有者和店长
	 * 
	 * @param shopEntity
	 */
	@Transactional
	public String createShop(String userId, ShopEntity shopEntity) {
		String shopId = UUIDGenerator.randomID();
		shopEntity.setShopId(shopId);
		shopEntity.setCreateTime(new Date());
		shopDao.save(shopEntity);

		// 创建用户和商店关系
		createRelation(userId, shopId, ShopUserRelationType.OWNER);
		createRelation(userId, shopId, ShopUserRelationType.MANAGER);
		return shopId;
	}

	public ShopEntity getShop(String shopId) {
		return shopDao.getOne(shopId);
	}

	public List<ShopRelationDetail> getShopsByUserId(String userId) {
		List<ShopRelationDetail> shopRelationDetails = new ArrayList<>();
		List<ShopRelationEntity> relations = shopRelationDao.getShopsByUserId(userId);
		for (ShopRelationEntity relation : relations) {
			boolean flag = true;
			for (ShopRelationDetail shopRelationDetail : shopRelationDetails) {
				if (shopRelationDetail.getShopInfo().getShopId().equals(relation.getShopId())) {
					List<ShopRelationEntity> shopRelationEntities = shopRelationDetail.getRelations();
					shopRelationEntities.add(relation);
					shopRelationDetail.setRelations(shopRelationEntities);
					flag = false;
					continue;
				}
			}
			if (flag) {
				ShopRelationDetail shopRelationDetail = new ShopRelationDetail();
				List<ShopRelationEntity> shopRelationEntities = new ArrayList<>();
				shopRelationEntities.add(relation);
				shopRelationDetail.setRelations(shopRelationEntities);
				shopRelationDetail.setUserId(userId);
				ShopEntity shopEntity = getShop(relation.getShopId());
				if(ShopStatusConstants.OFF_LINE.equals(shopEntity.getStatus()))
				{
					continue;
				}
				shopRelationDetail.setShopInfo(shopEntity);
				SettlementEntity settlementEntity = settlementService
						.get(DateFormatUtils.format(new Date(), "yyyy-MM-dd"), relation.getShopId());
				shopRelationDetail.setToday(settlementEntity);
				shopRelationDetails.add(shopRelationDetail);
			}
		}
		return shopRelationDetails;
	}

	/**
	 * 创建用户和商店关系
	 * 
	 * @param userId
	 * @param shopId
	 * @param type
	 */
	public void createRelation(String userId, String shopId, Integer type) {
		ShopRelationEntity relationEntity = new ShopRelationEntity();
		relationEntity.setCreateTime(new Date());
		relationEntity.setRelationId(UUIDGenerator.randomID());
		relationEntity.setRelationType(type);
		relationEntity.setShopId(shopId);
		relationEntity.setUpdateTime(new Date());
		relationEntity.setUserId(userId);
		shopRelationDao.save(relationEntity);
	}

	public List<ShopEntity> getAllShop() {
		return shopDao.findAll();
	}
	
	/**
	 * 查询用户和商店之间的对应关系
	 * @param userId
	 * @param shopId
	 * @param type
	 * @return
	 */
	public List<ShopRelationEntity> getRelation(String userId, String shopId, Integer type) {
		return shopRelationDao.getShopsByUserIdAndShopIdAndRelationType(userId, shopId, type);
	}

	public void deleteShopByShopId(String shopId) {
		ShopEntity shopEntity = shopDao.findOne(shopId);
		shopEntity.setStatus(ShopStatusConstants.OFF_LINE);
		shopDao.save(shopEntity);
	}
}
