package com.wangsheng.ShopCasher.good.info;

import lombok.Data;
import java.util.Date;

@Data
public class WxGoodInfo {

  private String _id;

  private String _openid;

  private double bid;
  private String code;
  private String name;
  private String picId;
  private double price;
  private String shopId;
  private String spec;
  private String stock;
  private Date createTime;
  private Date updateTime;
}
