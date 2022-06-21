CREATE TABLE IF NOT EXISTS hw.t_contact (
    id BIGSERIAL PRIMARY KEY,
    person_id BIGINT,
    type VARCHAR(100),
    value varchar(1000),
    constraint fk_person_id FOREIGN KEY (person_id) references hw.t_person
);