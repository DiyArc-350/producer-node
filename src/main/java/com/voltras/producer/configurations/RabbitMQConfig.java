    package com.voltras.producer.configurations;

    import org.springframework.amqp.core.*;
    import org.springframework.amqp.rabbit.connection.ConnectionFactory;
    import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
    import org.springframework.amqp.support.converter.MessageConverter;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Primary;

    @Configuration
    public class RabbitMQConfig {

        @Value("${rabbitmq.name}")
        private String queueName;

        @Value("${rabbitmq.exchange}")
        private String exchangeName;

        @Value("${rabbitmq.key}")
        private String routingKey;

        @Bean
        public Queue queue() {
            return new Queue(queueName, true); // durable = true
        }

    //    start of private message

        @Bean
        public TopicExchange exchange() {
            return new TopicExchange(exchangeName);
        }

        @Bean
        public Binding bindingMessage(Queue queue, TopicExchange exchange) {
            return BindingBuilder
                    .bind(queue)
                    .to(exchange)
                    .with(routingKey);
        }

    //    end of private message bind


        // 🔥 THIS is the converter that handles POJO → JSON
//        @Bean
//        public MessageConverter jsonMessageConverter() {
//            return new Jackson2JsonMessageConverter();
//        }
//
//        // 🔥 THIS injects the converter into RabbitTemplate
//        @Bean
//        public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//            rabbitTemplate.setMessageConverter(jsonMessageConverter());
//            return rabbitTemplate;
//        }

//        @Bean
//        public MessageConverter converter() {
//            return new Jackson2JsonMessageConverter();
//        }
//
//        @Bean
//        @Primary
//        public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
//            RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//            rabbitTemplate.setMessageConverter(converter());
//            return rabbitTemplate;
//        }


        @Bean
        public MessageConverter jsonMessageConverter() {
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
            RabbitTemplate template = new RabbitTemplate(connectionFactory);
            template.setMessageConverter(jsonMessageConverter());
            return template;
        }


    }
