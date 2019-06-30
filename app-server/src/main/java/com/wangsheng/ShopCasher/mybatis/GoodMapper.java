package com.wangsheng.ShopCasher.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wangsheng.ShopCasher.good.data.GoodInfo;
import com.wangsheng.ShopCasher.good.info.QueryGoodReq;
import com.wangsheng.ShopCasher.good.info.QuerySuggestGoodReq;

public interface GoodMapper {

	List<GoodInfo> queryGood(@Param("req") QueryGoodReq req);

	Integer queryGoodCount(@Param("req") QueryGoodReq req);

	List<GoodInfo> querySuggestion(@Param("req") QuerySuggestGoodReq req);

}
