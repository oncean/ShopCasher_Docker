package com.wangsheng.ShopCasher.good.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_good_abandonHis")
public class GoodInfoAbandonHis extends GoodInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*之前的ID*/
	private String formerId;

	public String getFormerId() {
		return formerId;
	}

	public void setFormerId(String formerId) {
		this.formerId = formerId;
	}
}
