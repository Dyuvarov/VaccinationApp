CREATE TABLE IF NOT EXISTS qr.t_vaccination_info(
    id          BIGSERIAL PRIMARY KEY,
    uuid        VARCHAR NOT NULL UNIQUE,
    date        TIMESTAMP NOT NULL,
    document    NUMERIC NOT NULL,
    qr_id       BIGINT NOT NULL,
    constraint fk_qr_id
                               foreign key(qr_id) references qr.t_qr_code(id) ON DELETE CASCADE
)