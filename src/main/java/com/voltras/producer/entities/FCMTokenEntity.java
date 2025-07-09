package com.voltras.producer.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fcm_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FCMTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;
}
