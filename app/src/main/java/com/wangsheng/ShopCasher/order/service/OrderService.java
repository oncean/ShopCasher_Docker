package com.wangsheng.ShopCasher.order.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangsheng.ShopCasher.Constant.DateFormatConstants;
import com.wangsheng.ShopCasher.good.Dao.GoodDao;
import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.service.GoodService;
import com.wangsheng.ShopCasher.mybatis.OrderMapper;
import com.wangsheng.ShopCasher.order.dao.OrderDao;
import com.wangsheng.ShopCasher.order.dao.OrderItemDao;
import com.wangsheng.ShopCasher.order.data.Order;
import com.wangsheng.ShopCasher.order.data.OrderItem;
import com.wangsheng.ShopCasher.order.info.OrderItemInfo;
import com.wangsheng.ShopCasher.order.info.QueryOrderReq;
import com.wangsheng.ShopCasher.order.info.QueryOrderRsp;
import com.wangsheng.ShopCasher.settlement.service.SettlementService;
import com.wangsheng.comm.CountResponse;
import com.wangsheng.comm.utils.UUIDGenerator;
import com.wangsheng.model.OrderModel;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemDao orderItemDao;
	
	@Autowired
	private GoodDao goodDao;
	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private SettlementService settlementService;
	
	@Transactional
	public void addOrder(OrderModel orderModel,String userId){
		String orderId = UUIDGenerator.randomID();
		Order order = orderModel.getOrder();
		order.setOrderId(orderId);
		order.setSettlementTime(new Date());
		order.setSettlementUserId(userId);
		double profit = 0;
		List<OrderItem> orderItems = orderModel.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			String orderItemId = UUIDGenerator.randomID();
			orderItem.setOrderId(orderId);
			orderItem.setItemId(orderItemId);
			orderItemDao.save(orderItem);
			
			GoodInfo goodInfo = goodDao.findOne(orderItem.getGoodId());
			goodService.saleGood(goodInfo.getId(), orderItem.getNumber());
			profit += (goodInfo.getPrice()-goodInfo.getBid()) * orderItem.getNumber();
		}
		order.setProfit(profit);
		orderDao.save(order);
		
		settlementService.settlementToday(order.getShopId());
	}
	

	/**
	 * 分页查询订单
	 * req day yyyy-MM-dd
	 * @param req
	 * @return
	 * @throws ParseException 
	 */
	public QueryOrderRsp queryOrderGroup(QueryOrderReq req) throws ParseException {
		Pageable pageable = new PageRequest(req.getPage().getPageNo(), req.getPage().getPageSize(), Direction.DESC,"settlementTime");
		Date startDate = DateUtils.parseDate(req.getDay()+" 00:00:00", new String[]{DateFormatConstants.COMMON_DATE_TIME_FORMATTER});
		Date endDate = DateUtils.parseDate(req.getDay()+" 23:59:59", new String[]{DateFormatConstants.COMMON_DATE_TIME_FORMATTER});
		Specification<Order> specification = new Specification<Order>() {
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("shopId"), req.getShopId()));
                predicates.add(cb.between(root.get("settlementTime"), startDate, endDate));
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
        };
		Page<Order> result =orderDao.findAll(specification,pageable);
		QueryOrderRsp queryOrderRsp = new QueryOrderRsp();
		queryOrderRsp.setOrders(result.getContent());
		queryOrderRsp.setTotalNum(result.getNumber());
		return queryOrderRsp;
	}
	
	public CountResponse query(QueryOrderReq req) throws ParseException {
		List<Order> orders = orderMapper.queryOrder(req);
		Integer total = orderMapper.queryOrderCount(req);
		return new CountResponse(orders, total);
	}
	
	public List<OrderItemInfo> getOrderItems(String orderId){
		List<OrderItemInfo> orders = orderMapper.getOrderItems(orderId);
		return orders;
	}
}
