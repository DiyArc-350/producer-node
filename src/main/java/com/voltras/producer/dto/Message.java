package com.voltras.producer.dto;

import lombok.Data;

@Data
public class Message {
    private Integer id;
    private String topic;
    private String message;
    private String category;
    private String image_url;

}
