package org.levon24.esp_sensors.repositories;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:34
 */

import org.levon24.esp_sensors.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

}
