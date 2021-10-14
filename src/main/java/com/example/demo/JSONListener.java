package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class JSONListener {
 private static final Logger log = LoggerFactory.getLogger(JSONListener.class);
 
 @RabbitListener(queues = DemoJsonApplication.queueName)
 public void consumeMSG(final Message message) {
	 log.info("Received input from the sensor: {}",message.toString());
 }
 
 @PostMapping
 public void registerSensor(@RequestBody JSONSensor sensor) {
	 JSONSender.addRegister(sensor);
	 log.info("Received sensor temp from postman");
 }
}
