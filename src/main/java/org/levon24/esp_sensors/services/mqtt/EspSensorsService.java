package org.levon24.esp_sensors.services.mqtt;

/*
 * User: levon
 * Date: 28.03.2026
 * Time: 08:53
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.levon24.esp_sensors.models.Event;
import org.levon24.esp_sensors.services.db.EventService;
import org.levon24.esp_sensors.services.mqtt.dto.EventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class EspSensorsService implements MqttCallbackExtended {
  private static final Logger logger = LoggerFactory.getLogger(EspSensorsService.class);
  private static final Map<String, String> sensors = Map.of(
    "sensors/kitchen", "kitchen"
  );
  private final ObjectMapper objectMapper;
  private final EventService eventService;
  private MqttClient mqttClient;

  @Value("${mqtt.brokerUrl}")
  private String brokerUrl;

  @Value("${mqtt.clientId}")
  private String clientId;

  public EspSensorsService(ObjectMapper objectMapper, EventService eventService) {
    this.objectMapper = objectMapper;
    this.eventService = eventService;
  }

  @PostConstruct
  public void init() throws MqttException {
    mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
    mqttClient.setCallback(this);

    final MqttConnectOptions connectOptions = new MqttConnectOptions();
    connectOptions.setCleanSession(true);
    connectOptions.setAutomaticReconnect(true);
    connectOptions.setKeepAliveInterval(60);
    logger.info("Connecting to broker: {}.", brokerUrl);

    mqttClient.connect(connectOptions);
    logger.info("Initialized");
  }

  @Override
  public void connectComplete(boolean reconnect, String serverUri) {
    logger.info("Connected - Reconnect: {}, ServerUri: {}.", reconnect, serverUri);

    try {
      for (String topic : sensors.keySet()) {
        logger.info("Subscribing to: {}.", topic);
        mqttClient.subscribe(topic);
      }
    } catch (MqttException e) {
      logger.error("Can't subscribe to topics: {}.", e.getMessage(), e);
    }
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    final String payload = new String(message.getPayload());
    logger.info("MessageArrived: {} on topic: {}.", payload, topic);

    try {
      final EventDto eventDto = objectMapper.readValue(payload, EventDto.class);
      logger.debug("Message EventDto: {}.", eventDto);

      final Event event = new Event();

      final Timestamp timestamp;
      if (eventDto.timestamp() != null && eventDto.timestamp() > 0) {
        timestamp = new Timestamp(eventDto.timestamp() * 1000);
        logger.debug("Timestamp: {}.", timestamp);
      } else {
        timestamp = new Timestamp(System.currentTimeMillis());
      }
      event.setTimestamp(timestamp);

      final String sensor = sensors.get(topic);
      event.setSensor(sensor);

      event.setTemperature(eventDto.temperature());
      event.setHumidity(eventDto.humidity());
      event.setBattery(eventDto.battery());

      final Event s = eventService.save(event);
      logger.info("Saved event: {}.", s);
    } catch (Exception e) {
      logger.warn("Convert - Error: {}.", e.getMessage(), e);
    }
  }

  @Override
  public void connectionLost(Throwable cause) {
    logger.warn("ConnectionLost: {}. Reconnecting.", cause.getMessage());
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    logger.info("DeliveryComplete: {}.", token);
  }
}
