package com.wangsheng.WebSocket.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor{
	
	private static Logger logger = LoggerFactory.getLogger(HandshakeInterceptor.class);
	
    @Override  
    public boolean beforeHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Map<String, Object> attributes) throws Exception {  
        logger.debug("GOMA ===> Before Handshake,response={}",response.toString());  
        return super.beforeHandshake(request, response, wsHandler, attributes);  
    }  
  
    @Override  
    public void afterHandshake(ServerHttpRequest request,  
            ServerHttpResponse response, WebSocketHandler wsHandler,  
            Exception ex) {  
        logger.debug("GOMA ===> After Handshake,response={}",response.toString());  
        super.afterHandshake(request, response, wsHandler, ex);  
    }  
}
