    package com.voltras.producer.models;

    import com.fasterxml.jackson.annotation.JsonInclude;

    import java.util.List;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record MessageModel(
            List<String> tokens,  // nullable
            String topic,
            String message,
            String category,
            String image_url
    ) {
        public static MessageModel of(List<String> tokens, String topic, String message, String category, String image_url) {
            return new MessageModel(tokens, topic, message, category, image_url );
        }

        public static MessageModel of(List<String> tokens, String topic, String message, String category) {
            return new MessageModel(tokens, topic, message, category, null);
        }

        public static MessageModel of(String topic, String message, String category) {
            return new MessageModel(null, topic, message, category,  null);
        }
    }
