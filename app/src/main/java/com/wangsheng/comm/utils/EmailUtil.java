package com.wangsheng.comm.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wangsheng.comm.utils.entity.MessageInfo;

@Service
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    public void send(MessageInfo info){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo(info.getAddress()); //自己给自己发送邮件
        message.setSubject(info.getTitle());
        message.setText(info.getContent());
        javaMailSender.send(message);
    }
}
