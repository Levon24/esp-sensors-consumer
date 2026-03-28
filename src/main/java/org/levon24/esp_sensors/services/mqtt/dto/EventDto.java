package org.levon24.esp_sensors.services.mqtt.dto;

/*
 * User: levon
 * Date: 28.03.2026
 * Time: 10:41
 */

public record EventDto(
  Long timestamp,
  Double temperature,
  Double humidity) {

}
