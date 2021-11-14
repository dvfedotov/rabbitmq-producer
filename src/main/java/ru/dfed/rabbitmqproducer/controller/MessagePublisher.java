package ru.dfed.rabbitmqproducer.controller;

import java.util.Date;
import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dfed.rabbitmqproducer.config.MqConfig;
import ru.dfed.rabbitmqproducer.domain.CustomMessage;

@RestController
public class MessagePublisher {

    private final RabbitTemplate template;

    public MessagePublisher(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomMessage message) {
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MqConfig.MESSAGE_EXCHANGE, MqConfig.MESSAGE_ROUNTING_KEY, message);
        return "Message Published";
    }

}
