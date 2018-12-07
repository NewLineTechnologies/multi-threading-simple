create table counter_info
(
  reached_at TIMESTAMP
);

create table request_info
(
  id              bigint auto_increment
    primary key,
  header_names    TEXT,
  remote_host     TEXT,
  query_string    TEXT,
  attribute_names TEXT
)