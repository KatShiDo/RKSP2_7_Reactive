CREATE TABLE IF NOT EXISTS computers (
    id SERIAL PRIMARY KEY,
    cpu VARCHAR(255),
    gpu VARCHAR(255),
    ram INTEGER,
    storage INTEGER
);