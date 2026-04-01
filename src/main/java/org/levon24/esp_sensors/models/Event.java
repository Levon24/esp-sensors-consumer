package org.levon24.esp_sensors.models;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:27
 */

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "events")
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "sensor", length = 40)
  private String sensor;

  @Column(name = "timestamp")
  private Timestamp timestamp;

  @Column(name = "temperature")
  private Double temperature;

  @Column(name = "humidity")
  private Double humidity;

  @Column(name = "battery")
  private Double battery;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSensor() {
    return sensor;
  }

  public void setSensor(String sensor) {
    this.sensor = sensor;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Double getHumidity() {
    return humidity;
  }

  public void setHumidity(Double humidity) {
    this.humidity = humidity;
  }

  public Double getBattery() {
    return battery;
  }

  public void setBattery(Double battery) {
    this.battery = battery;
  }

  @Override
  public String toString() {
    return "Event{" +
      "id=" + id +
      ", sensor='" + sensor + '\'' +
      ", timestamp=" + timestamp +
      ", temperature=" + temperature +
      ", humidity=" + humidity +
      ", battery=" + battery +
      '}';
  }
}
