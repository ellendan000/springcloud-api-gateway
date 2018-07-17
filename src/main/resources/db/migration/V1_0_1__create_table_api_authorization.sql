create table api_authorization (
  id           int auto_increment primary key,
  path         varchar(255) not null,
  method       varchar(10),
  params       varchar(255),
  headers      varchar(255),
  authorities  varchar(255),
  service_name varchar(50)
);