package com.example.demo;

import java.sql.Timestamp;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("sensorvalues")
public class JSONSensor {

  @PrimaryKey private final String sensor_id;

  private final float sensor_temp;

private Timestamp sensor_timestamp;

 public JSONSensor(String sensor_id, float sensor_temp, Timestamp sensor_timestamp) {
    this.sensor_id = sensor_id;
    this.sensor_temp = sensor_temp;
    this.sensor_timestamp = sensor_timestamp;
  }

  public String getId() {
    return sensor_id;
  }

  private float getTemp() {
    return sensor_temp;
  }

  private Timestamp getTs() {
    return sensor_timestamp;
  }
  
  @Override
  public String toString() {
    return String.format("{ @type = %1$s, sensor_id = %2$s, sensor_temp = %3$f, sensor_timestamp = %4$s }",getClass(), getId(), getTemp(), getTs());
  }
}