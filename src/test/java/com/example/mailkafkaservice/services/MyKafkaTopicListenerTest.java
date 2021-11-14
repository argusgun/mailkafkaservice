package com.example.mailkafkaservice.services;

import com.example.mailkafkaservice.models.KafkaTopic;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class MyKafkaTopicListenerTest {
    @Autowired
    private MyKafkaTopicListener myKafkaTopicListener;
    @MockBean
    private MailService mailService;

    @Test
    void getMessageFromTopic() throws JsonProcessingException {
        myKafkaTopicListener.getMessageFromTopic(getMessageForTest());
        Mockito.verify(mailService,Mockito.times(1)).sendMessage(getTopicForTest().getEmail(),getTestMessage(getTopicForTest()));
    }

    public KafkaTopic getTopicForTest(){
        KafkaTopic kafkaTopic = new KafkaTopic();
        kafkaTopic.setFirstName("A");
        kafkaTopic.setLastName("B");
        kafkaTopic.setEmail("a@mail.ru");
        return kafkaTopic;
    }

    public String getMessageForTest(){
        return "{\"lastName\": \"B\",\"firstName\": \"A\",\"email\":\"a@mail.ru\"}";
    }

    public  String  getTestMessage(KafkaTopic kafkaTopic){
        return "Hello, Dear "+kafkaTopic.getFirstName()+" "+kafkaTopic.getLastName()+"!";
    }
}