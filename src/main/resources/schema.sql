CREATE TABLE IF NOT EXISTS Volunteers (
    id SERIAL NOT NULL, 
    username VARCHAR(255), 
    password VARCHAR(255), 
    role VARCHAR(255), 
    active BOOLEAN, 
    PRIMARY KEY (id)
    );