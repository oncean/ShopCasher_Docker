package com.wangsheng.ShopCasher.settlement.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "t_settlement")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SettlementEntity {
	
	@Id
	private String settlementId;
	
	private String shopId;

	/** 结算日期 yyyy-MM-dd */
	private String settlementDate;
	
	/** 今日单数 */
	private Integer orderNum;
	
	/** 今日营业额*/
	private double sales;
	
	/** 今日纯利润*/
	private double profit;
	
	/** 结算更新时间 */
	private Date updateTime;
}
