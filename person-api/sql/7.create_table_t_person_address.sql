CREATE TABLE IF NOT EXISTS hw.t_person_address(
     id BIGSERIAL PRIMARY KEY,
     person_id BIGINT,
     address_id BIGINT,
     constraint fk_person_id FOREIGN KEY (person_id) REFERENCES hw.t_person(id) ON DELETE CASCADE,
     constraint fk_address_id FOREIGN KEY (address_id) REFERENCES hw.t_address(id) ON DELETE CASCADE
)