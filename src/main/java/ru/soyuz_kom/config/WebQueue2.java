package ru.soyuz_kom.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.soyuz_kom.provider.Receiver;
import ru.soyuz_kom.provider.Receiver2;

@Configuration
public class WebQueue2 {
/*
    final static String queueName = "log";


    @Bean
    Queue queue2() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange2() {
        return new TopicExchange("spring-boot-exchange2");
    }

    @Bean
    Binding binding(Queue queue2, TopicExchange exchange2) {
        return BindingBuilder.bind(queue2).to(exchange2).with(queueName);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }


    @Bean
    Receiver2 receiver2() {
        return new Receiver2();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver2 receiver2) {
        return new MessageListenerAdapter(receiver2, "receiveMessage");
    }
    */
}
