create table route (
  id VARCHAR(50) primary key,
  path VARCHAR(255) NOT NULL,
  service_id VARCHAR(50),
  url VARCHAR(255),
  strip_prefix TINYINT(1),
  retryable TINYINT(1)
);