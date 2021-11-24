package ru.dfed.rabbitmqproducer.controller;

import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dfed.rabbitmqproducer.config.MqConfig;
import ru.dfed.rabbitmqproducer.domain.CustomMessage;

@Slf4j
@RestController
public class MessagePublisherController {

    private final RabbitTemplate template;

    public MessagePublisherController(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody CustomMessage message) {
        validateOrder(message);
        log.info("Received {}", message);
        template.convertAndSend(MqConfig.MESSAGE_EXCHANGE, message.getProductType().getType(), message);
        return "Message Published";
    }

    private void validateOrder(CustomMessage message) {
        if (message.getMessageId() == null) {
            message.setMessageId(UUID.randomUUID().toString());
        }
        if (message.getMessageDate() == null) {
            message.setMessageDate(new Date());
        }
    }

}
