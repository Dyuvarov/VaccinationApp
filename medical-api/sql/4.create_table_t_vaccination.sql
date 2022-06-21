CREATE TABLE IF NOT EXISTS hw.t_vaccination (
    id BIGSERIAL primary key,
    uuid VARCHAR NOT NULL UNIQUE,
    date TIMESTAMP NOT NULL,
    vaccine_id BIGINT NOT NULL,
    vaccination_point_id BIGINT NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    patronymic VARCHAR,
    document INT NOT NULL,
    handle_date TIMESTAMP,

    constraint fk_vaccine_id
        foreign key(vaccine_id) references hw.t_vaccine(id) ON DELETE CASCADE,
    constraint fk_vaccination_point_id
        foreign key(vaccination_point_id) references hw.t_vaccination_point(id) ON DELETE CASCADE
)