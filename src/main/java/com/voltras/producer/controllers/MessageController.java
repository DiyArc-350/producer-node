package com.voltras.producer.controllers;

import com.voltras.producer.models.MessageModel;
import com.voltras.producer.services.FCMTokenService;
import com.voltras.producer.services.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/private")
public class MessageController {

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.key}")
    private String routingKey;

    private final MessageService messageService;
    private final RabbitTemplate rabbitTemplate;
    private final FCMTokenService fcmTokenService;

    public MessageController(MessageService messageService, RabbitTemplate rabbitTemplate, FCMTokenService fcmTokenService) {
        this.messageService = messageService;
        this.rabbitTemplate = rabbitTemplate;
        this.fcmTokenService = fcmTokenService;
    }


//    @PostMapping("/publish")
//    public ResponseEntity<String> send(@RequestBody MessageModel message) {
//
//        MessageModel messageWithId = new MessageModel(
//                message.topic(),
//                message.message(),
//                message.category()
//        );
//
//        messageService.sendJsonMessage(messageWithId);
//
//        return ResponseEntity.ok("Message sent to RabbitMQ");
//    }

    @PostMapping("/publish")
    public ResponseEntity<String> send(@RequestBody MessageModel message) {
        List<String> tokens = fcmTokenService.getAllTokens();

        MessageModel messageWithTokens = new MessageModel(
                tokens,
                message.topic(),
                message.message(),
                message.category(),
                message.image_url()
        );

        messageService.sendJsonMessage(messageWithTokens);

        return ResponseEntity.ok("Message sent to RabbitMQ");
    }


//    @PostMapping("/publish")
//    public ResponseEntity<String> send(@RequestBody MessageModel message) {
//        messageService.sendJsonMessage(message);
//        return ResponseEntity.ok("Private message sent to RabbitMQ with FCM tokens");
//    }


}
