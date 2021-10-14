package com.example.demo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datastax.oss.driver.api.core.CqlSession;

@Service
@RequestMapping(path = "test/v1")
public class JSONSender {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(JSONSender.class);
    
    public JSONSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    private static JSONSensor newSensor(String sensor_id, float sensor_temp, Timestamp sensor_timestamp) {
        return new JSONSensor(sensor_id, sensor_temp, sensor_timestamp);
      }
    
   public static void addRegister (JSONSensor newSensor) {
	   System.out.println(newSensor.toString());
       log.info("testing...");

   }
    
   @Scheduled(fixedDelay = 10000L)
    public void sendMessage() {
        log.info("Sending message...");
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 7200000);
        CqlSession cqlSession = CqlSession.builder().withKeyspace("sensordb").build();

	    CassandraOperations template = new CassandraTemplate(cqlSession);

	    JSONSensor sensor = template.insert(newSensor(UUID.randomUUID().toString(),(float) (Math.random()*100),timestamp));

	    log.info(template.selectOne(Query.query(Criteria.where("sensor_id").is(sensor.getId())), JSONSensor.class).getId());
	    cqlSession.close();
        rabbitTemplate.convertAndSend(DemoJsonApplication.topicExchangeName, DemoJsonApplication.routingKey, sensor.toString());
    }
}
