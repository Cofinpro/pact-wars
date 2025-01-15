package de.cofinpro.demo.pact.spaceships.consumers;

import io.quarkus.runtime.Startup;
import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * This is a trick to get the RabbitMQ message queue correctly bound to the RabbitMQ exchange.
 * The support for RabbitMQ in Quarkus is very poor, so we use the client library directly.
 */
@Startup
@ApplicationScoped
public class RabbitMQInitializer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    void init() {
        logger.info("Initializing RabbitMQ");

        Vertx vertx = Vertx.vertx();

        RabbitMQOptions config = new RabbitMQOptions();
        config.setUser("guest");
        config.setPassword("guest");
        config.setHost("rabbitmq");
        config.setPort(5672);
        config.setAutomaticRecoveryEnabled(true);

        RabbitMQClient client = RabbitMQClient.create(vertx, config);
        logger.info("Created RabbitMQ client, connected={}", client.isConnected());
        client.start(event -> {
            if (event.failed()) {
                logger.error("Failed RabbitMQ Connection", event.cause());
            }
            if (event.succeeded()) {
                logger.info("Connected RabbitMQ client, connected={}, openChannel={}", client.isConnected(), client.isOpenChannel());
                client.exchangeDeclare("travels", "fanout", false, false, event1 -> {
                    if (event1.failed()) {
                        logger.warn("Error declaring Exchange, this could simply mean it's already declared!", event1.cause());
                    } else {
                        logger.info("Declared travels exchange!");
                    }
                    client.queueDeclare("spaceship-service", true, false, false, event2 -> {
                        if (event2.failed()) {
                            logger.warn("Error declaring Queue, this could simply mean it's already declared!", event2.cause());
                        } else {
                            logger.info("Declared spaceship-service queue!");
                        }
                        client.queueBind("spaceship-service", "travels", "", event3 -> {
                            if (event3.failed()) {
                                logger.error("Failed binding Queue to Exchange!", event3.cause());
                            } else {
                                logger.info("Bound spaceship-service queue to travels exchange");
                            }
                            client.stop(event4 -> {
                                if (event4.failed()) {
                                    logger.error("Failed RabbitMQ Disconnect", event4.cause());
                                }
                            });
                        });
                    });
                });
            }
        });
    }

}
