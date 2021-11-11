package com.example.mailkafkaservice.services;

import com.example.mailkafkaservice.models.KafkaTopic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyKafkaTopicListener {
    private  final MailService mailService;

    @KafkaListener(topics = "my-topic")
    public void getMessageFromTopic(String content) throws JsonProcessingException {
        log.debug("getMessageFromTopic");
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaTopic kafkaTopic = objectMapper.readValue(content,KafkaTopic.class);
        String message = "Hello, Dear "+kafkaTopic.getFirstName()+" "+kafkaTopic.getLastName()+"!";
        mailService.sendMessage(kafkaTopic.getEmail(),message);
    }
}
