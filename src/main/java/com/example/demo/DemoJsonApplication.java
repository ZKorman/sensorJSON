package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoJsonApplication {
	 static final String topicExchangeName = "json-exchange";
	 static final String queueName = "spring-boot-queue";
	 static final String routingKey = ".route";

	  @Bean
	 public Queue queue() {
	    return new Queue(queueName);
	  }

	  @Bean
	 public TopicExchange exchange() {
	    return new TopicExchange(topicExchangeName);
	  }

	  @Bean
	  public Binding binding(Queue queue, TopicExchange exchange) {
	    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	  }

	public static void main(String[] args) {
		SpringApplication.run(DemoJsonApplication.class, args);
	}

}
