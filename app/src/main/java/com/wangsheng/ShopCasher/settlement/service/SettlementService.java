package com.wangsheng.ShopCasher.settlement.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wangsheng.ShopCasher.Constant.DateFormatConstants;
import com.wangsheng.ShopCasher.mybatis.SettlementMapper;
import com.wangsheng.ShopCasher.order.dao.OrderDao;
import com.wangsheng.ShopCasher.order.data.Order;
import com.wangsheng.ShopCasher.settlement.Dao.SettlementDao;
import com.wangsheng.ShopCasher.settlement.data.SettlementEntity;
import com.wangsheng.ShopCasher.settlement.info.QuerySettlementReq;
import com.wangsheng.ShopCasher.settlement.info.QuerySettlementRsp;
import com.wangsheng.ShopCasher.settlement.info.SettlementByCatogeryReq;
import com.wangsheng.ShopCasher.settlement.info.SettlementCatogeryCount;
import com.wangsheng.auth.comm.ResultConstants;
import com.wangsheng.comm.utils.UUIDGenerator;
import com.wangsheng.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettlementService {

	@Autowired
	private SettlementDao settlementDao;

	@Autowired
	private SettlementMapper settlementMapper;

	@Autowired
	private OrderDao orderDao;

	public String createSettlement(SettlementEntity settlementEntity) throws ServiceException {
		// 查询是否已存在结算实例
		SettlementEntity ifExist = settlementDao.getBySettlementDateAndShopId(settlementEntity.getSettlementDate(),
				settlementEntity.getShopId());
		if (ifExist != null) {
			log.error("create settlement failed,exsit entity = {}", ifExist);
			throw new ServiceException(ResultConstants.SETTLEMNT_EXIST);
		}
		settlementEntity.setSettlementId(UUIDGenerator.randomID());
		SettlementEntity result = settlementDao.save(settlementEntity);
		return result.getSettlementId();
	}

	public SettlementEntity get(String settlementDate, String shopId) {
		SettlementEntity settlementEntity = settlementDao.getBySettlementDateAndShopId(settlementDate, shopId);
		if (settlementEntity == null) {
			settlementEntity = createNewSettlement(settlementDate, shopId);
		}
		return settlementEntity;
	}

	public SettlementEntity createNewSettlement(String settlementDate, String shopId) {
		SettlementEntity settlementEntity = new SettlementEntity();
		settlementEntity.setSettlementId(UUIDGenerator.randomID());
		settlementEntity.setShopId(shopId);
		settlementEntity.setSettlementDate(settlementDate);
		settlementEntity.setOrderNum(0);
		settlementEntity.setSales(0);
		settlementEntity.setProfit(0);
		return settlementDao.save(settlementEntity);
	}

	public void settlementToday(String shopId) {
		Date date = new Date();
		String today = DateFormatUtils.format(date, "yyyy-MM-dd");
		SettlementEntity settlementEntity = get(today, shopId);
		List<Order> orders = orderDao.queryDayOrders(shopId, today);
		double sales = 0;
		double profit = 0;
		Integer num = 0;
		for (Order order : orders) {
			sales += order.getTotalPrice();
			profit += order.getProfit();
			num++;
		}
		settlementEntity.setProfit(profit);
		settlementEntity.setSales(sales);
		settlementEntity.setOrderNum(num);
		settlementEntity.setUpdateTime(date);
	}

	/**
	 * 分页查询账期
	 * 
	 * @param req
	 * @return
	 */
	public QuerySettlementRsp querySettlement(QuerySettlementReq req) {
		QuerySettlementRsp querySettlementRsp = new QuerySettlementRsp();
		Specification<SettlementEntity> specification = new Specification<SettlementEntity>() {
			@Override
			public Predicate toPredicate(Root<SettlementEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.equal(root.get("shopId"), req.getShopId()));
				if (!CollectionUtils.isEmpty(req.getSettlementDates())) {
					In<String> in = cb.in(root.get("settlementDate"));
					Iterator<String> iterator = req.getSettlementDates().iterator();
					while (iterator.hasNext()) {
						in.value(iterator.next());
					}
					predicates.add(in);
				}
				if (StringUtils.isNotEmpty(req.getStartDay()) && StringUtils.isNotEmpty(req.getEndDay())) {
					predicates.add(cb.between(root.get("settlementDate"), req.getStartDay(), req.getEndDay()));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		};

		if (req.getPage() != null) {
			Pageable pageable = new PageRequest(req.getPage().getPageNo(), req.getPage().getPageSize(), Direction.DESC,
					"settlementDate");
			Page<SettlementEntity> result = settlementDao.findAll(specification, pageable);
			querySettlementRsp.setSettlements(result.getContent());
			querySettlementRsp.setTotalNum(result.getTotalElements());
		} else {
			querySettlementRsp.setSettlements(
					settlementDao.findAll(specification, new Sort(Sort.Direction.ASC, "settlementDate")));
		}
		return querySettlementRsp;
	}
	
	public List<SettlementCatogeryCount> settlementByCatogery(SettlementByCatogeryReq req) throws ParseException {
		SimpleDateFormat myFmt=new SimpleDateFormat(DateFormatConstants.COMMON_DATE_FORMATTER);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(myFmt.parse(req.getEndDay()));
        calendar.add(Calendar.DATE, 1);
		return settlementMapper.settlementByCatogery(req.getStartDay(), myFmt.format(calendar.getTime()), req.getShopId());
	}
}
