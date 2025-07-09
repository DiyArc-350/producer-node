package com.voltras.producer.services;

import com.voltras.producer.models.MessageModel;
import com.voltras.producer.repositories.FCMTokenRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    private final RabbitTemplate rabbitTemplate;
    private final FCMTokenRepository fcmTokenRepository;

    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.key}")
    private String routingKey;

    public MessageService(RabbitTemplate rabbitTemplate, FCMTokenRepository fcmTokenRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.fcmTokenRepository = fcmTokenRepository;
    }

//    public void sendJsonMessage(MessageModel request) {
//        List<String> tokens = fcmTokenRepository.findAllTokens();
//
//        MessageModel payload = (tokens == null || tokens.isEmpty())
//                ? MessageModel.of(request.topic(), request.message(), request.category())
//                : MessageModel.of(tokens, request.topic(), request.message(), request.category());
//
//        logger.info("📤 Sending message to RabbitMQ with {} tokens", tokens != null ? tokens.size() : 0);
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
//    }

    public void sendJsonMessage(MessageModel request) {
        List<String> tokens = fcmTokenRepository.findAllTokens();

        if (tokens == null || tokens.isEmpty()) {
            logger.warn("⚠️ No FCM tokens found.");
            return;
        }

        logger.info("📤 Sending {} messages individually to RabbitMQ...", tokens.size());

        for (String token : tokens) {
            MessageModel singlePayload = MessageModel.of(
                    List.of(token), // single token as list
                    request.topic(),
                    request.message(),
                    request.category(),
                    request.image_url()
            );

            rabbitTemplate.convertAndSend(exchangeName, routingKey, singlePayload);
        }
    }

//    public void sendJsonMessage(MessageModel message) {
//        logger.info("📤 Sending message to RabbitMQ: {}", message);
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
//    }
}
