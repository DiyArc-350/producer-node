package com.voltras.producer.services;

import com.voltras.producer.dto.FCMTokenDTO;
import com.voltras.producer.entities.FCMTokenEntity;
import com.voltras.producer.repositories.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMTokenService {

    private final FCMTokenRepository fcmTokenRepository;

//    @Transactional
//    public void saveToken(FCMTokenDTO dto) {
//        try {
//            FCMTokenEntity token = FCMTokenEntity.builder()
//                    .token(dto.getToken())
//                    .build();
//
//            fcmTokenRepository.save(token);
//        } catch (Exception e) {
//            log.error("❌ Failed to save token: {}", dto.getToken(), e);
//        }
//    }

    @Transactional
    public void saveToken(FCMTokenDTO dto) {
        if (fcmTokenRepository.existsByToken(dto.getToken())) {
            log.warn("⚠️ Token already exists: {}", dto.getToken());
            return;
        }

        FCMTokenEntity token = FCMTokenEntity.builder()
                .token(dto.getToken())
                .build();

        fcmTokenRepository.save(token);
        log.info("✅ Token saved: {}", dto.getToken());
    }

    public List<String> getAllTokens() {
        return fcmTokenRepository.findAll()
                .stream()
                .map(FCMTokenEntity::getToken)
                .toList();
    }


}
