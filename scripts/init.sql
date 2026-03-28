-- init database
CREATE SCHEMA esp_sensors;
CREATE USER 'esp-sensors'@'localhost' IDENTIFIED BY 'd13932943ee25168';
GRANT ALL PRIVILEGES ON esp_sensors.* to 'esp-sensors'@'localhost';

-- change db
USE esp_sensors;

-- init tables
SOURCE tables/events.sql
