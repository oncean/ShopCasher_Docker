package com.wangsheng.auth.service.token.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import lombok.AllArgsConstructor;;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel implements Serializable{

	  
    private static final long serialVersionUID = -1;  
    
    //用户id
    private String userId;

    //随机生成的uuid
    private String token;
    
    //随机生产的连接码
    private String connectCode;
}
