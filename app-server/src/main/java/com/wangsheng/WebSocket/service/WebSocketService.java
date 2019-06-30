package com.wangsheng.WebSocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.wangsheng.WebSocket.message.ScanSocketMessage;

@Service
public class WebSocketService {
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public void send(ScanSocketMessage message,String url){
		messagingTemplate.convertAndSend(url, message);
	}
	
	public void send(String message,String url){
		messagingTemplate.convertAndSend(url, message);
	}
}
