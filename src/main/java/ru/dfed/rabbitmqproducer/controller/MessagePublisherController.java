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
public class MessagePublisherController {

    private final RabbitTemplate template;

    public MessagePublisherController(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomMessage message) {
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        if(message.getMessage().contains("test")){
            template.convertAndSend(MqConfig.MESSAGE_EXCHANGE, MqConfig.MESSAGE_ROUTING_KEY_1, message);
        }else{
            template.convertAndSend(MqConfig.MESSAGE_EXCHANGE, MqConfig.MESSAGE_ROUTING_KEY_2, message);
        }
        return "Message Published";
    }

}
