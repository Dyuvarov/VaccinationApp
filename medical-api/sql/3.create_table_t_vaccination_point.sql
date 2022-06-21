CREATE TABLE IF NOT EXISTS hw.t_vaccination_point(
    id BIGSERIAL primary key,
    uuid VARCHAR NOT NULL  UNIQUE,
    name VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    address VARCHAR NOT NULL
)