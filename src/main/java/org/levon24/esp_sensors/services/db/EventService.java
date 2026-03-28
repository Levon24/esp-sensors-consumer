package org.levon24.esp_sensors.services.db;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:35
 */

import org.levon24.esp_sensors.models.Event;
import org.levon24.esp_sensors.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository repository;

  @Autowired
  public EventService(EventRepository repository) {
    this.repository = repository;
  }

  public Event save(Event event) {
    return repository.save(event);
  }
}
