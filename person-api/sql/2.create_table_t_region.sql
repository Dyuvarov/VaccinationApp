CREATE TABLE IF NOT EXISTS hw.t_region(
    id BIGSERIAL PRIMARY KEY,
    name varchar(500) UNIQUE
)