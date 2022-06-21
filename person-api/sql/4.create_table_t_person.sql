CREATE TABLE IF NOT EXISTS hw.t_person(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    patronymic VARCHAR(100),
    birth_date DATE,
    registry_address_id BIGINT,
    hidden BOOL default false,
    constraint fk_registry_address_id FOREIGN KEY (registry_address_id) references hw.t_address(id)
                        ON DELETE SET NULL
)