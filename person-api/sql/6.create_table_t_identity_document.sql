CREATE TABLE IF NOT EXISTS hw.t_identity_document
(
    id            BIGSERIAL PRIMARY KEY,
    document_type VARCHAR(100),
    number        BIGINT,
    "primary"     BOOLEAN,
    person_id     BIGINT,
    constraint fk_person_id FOREIGN KEY (person_id) REFERENCES hw.t_person(id)
)
