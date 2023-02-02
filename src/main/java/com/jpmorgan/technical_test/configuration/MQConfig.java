package com.jpmorgan.technical_test.configuration;

import com.jpmorgan.technical_test.v1_0_0.sales_message_adapter.MQListenerClient;
import com.jpmorgan.technical_test.v1_0_0.sales_message_adapter.SalesMessageAdapter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

  @Value("${mq.queue.name:}")
  private String queueName;

  @Value("${mq.username:}")
  private String username;

  @Value("${mq.password:}")
  private String password;

  @Bean
  public Queue queue() {
    return new Queue(queueName, false);
  }

  @Bean
  public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
    ApplicationContext context = new AnnotationConfigApplicationContext(SalesMessageConfig.class);;
    SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
    simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
    simpleMessageListenerContainer.setQueues(queue());
    simpleMessageListenerContainer.setMessageListener(context.getBean(MQListenerClient.class));
    return simpleMessageListenerContainer;
  }

  @Bean
  public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setUsername(password);
		return cachingConnectionFactory;
	}
}
