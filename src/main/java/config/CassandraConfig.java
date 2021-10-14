package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.oss.driver.api.core.CqlSession;

@Configuration public class CassandraConfig 
{ public @Bean CqlSession session() { 
	return CqlSession.builder().withKeyspace("sensordb").build(); } 
}