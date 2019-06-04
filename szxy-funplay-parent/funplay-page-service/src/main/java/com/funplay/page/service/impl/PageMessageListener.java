package com.funplay.page.service.impl;

import com.funplay.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class PageMessageListener implements MessageListener {
    @Autowired
    private ItemPageService itemPageService;
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage=(TextMessage)message;
            itemPageService.genItemHtml(Long.parseLong(textMessage.getText()));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
