package de.cofinpro.planets.messaging;

import de.cofinpro.planets.models.Travel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    Log log = LogFactory.getLog(MessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    private final FanoutExchange travelsExchange = new FanoutExchange("travels");

    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTravel(Travel travel) {
        log.info(String.format("Sending RabbitMQ Message with Travel: %s", travel));
        rabbitTemplate.convertAndSend(travelsExchange.getName(), "", travel);
    }

}
