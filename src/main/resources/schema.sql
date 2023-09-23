CREATE TABLE IF NOT EXISTS Volunteer (
    id SERIAL NOT NULL, 
    username VARCHAR(255), 
    password VARCHAR(255), 
    role VARCHAR(255), 
    active BOOLEAN, 
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS Contacts (
    id SERIAL NOT NULL,
    contact_name VARCHAR(255),
    phone VARCHAR(255),
    email VARCHAR(255),
    last_contact DATE,
    attitude INTEGER,
    comment VARCHAR(255),
    patron VARCHAR(255),
    PRIMARY KEY (id)
    );