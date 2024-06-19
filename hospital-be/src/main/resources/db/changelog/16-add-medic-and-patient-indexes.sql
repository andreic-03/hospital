CREATE EXTENSION IF NOT EXISTS pg_trgm;

ALTER TABLE patient ADD COLUMN ts tsvector
    GENERATED ALWAYS AS (
            to_tsvector('romanian', first_name) ||
            to_tsvector('romanian', last_name)
        ) STORED;

CREATE INDEX ts_idx_patient ON patient USING GIN (ts);

ALTER TABLE medic ADD COLUMN ts tsvector
    GENERATED ALWAYS AS (
            to_tsvector('romanian', first_name) ||
            to_tsvector('romanian', last_name)
        ) STORED;

CREATE INDEX ts_idx_medic ON medic USING GIN (ts);