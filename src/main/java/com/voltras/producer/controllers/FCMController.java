package com.voltras.producer.controllers;

import com.voltras.producer.dto.FCMTokenDTO;
import com.voltras.producer.repositories.FCMTokenRepository;
import com.voltras.producer.services.FCMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FCMController {

    private final FCMTokenService fcmTokenService;
    private final FCMTokenRepository fcmTokenRepository;


    @PostMapping("/register-token")
    public ResponseEntity<String> registerFcmToken(@RequestBody FCMTokenDTO request) {
        fcmTokenService.saveToken(request);
        return ResponseEntity.ok("Token registered");
    }

    @GetMapping("/check")
    public ResponseEntity<?> check() {
        return ResponseEntity.ok(fcmTokenRepository.findAllTokens());
    }

}
