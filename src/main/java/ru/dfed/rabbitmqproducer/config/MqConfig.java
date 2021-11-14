package ru.dfed.rabbitmqproducer.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqConfig {

    public static final String MESSAGE_QUEUE = "message_queue_1";
    public static final String MESSAGE_QUEUE_2 = "message_queue_2";
    public static final String MESSAGE_EXCHANGE = "message_exchange";
    public static final String MESSAGE_ROUTING_KEY_1 = "message_routingKey_1";
    public static final String MESSAGE_ROUTING_KEY_2 = "message_routingKey_2";

    @Bean
    public Queue queue1() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public Queue queue2() {
        return new Queue(MESSAGE_QUEUE_2);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(MESSAGE_EXCHANGE);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder
            .bind(queue1())
            .to(exchange())
            .with(MESSAGE_ROUTING_KEY_1);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder
            .bind(queue2())
            .to(exchange())
            .with(MESSAGE_ROUTING_KEY_2);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
