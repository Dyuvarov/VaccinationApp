CREATE TABLE IF NOT EXISTS hw.t_address(
    id        BIGSERIAL PRIMARY KEY,
    region_id BIGINT,
    city VARCHAR(100),
    street VARCHAR(100),
    house   INT,
    flat    INT,
    constraint fk_region_id FOREIGN KEY(region_id) REFERENCES hw.t_region(id) ON DELETE SET NULL
)