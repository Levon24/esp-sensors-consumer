CREATE TABLE events (
  id BIGINT NOT NULL AUTO_INCREMENT,
  room VARCHAR(40) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  temperature DOUBLE,
  humidity DOUBLE,
  PRIMARY KEY (id)
);
