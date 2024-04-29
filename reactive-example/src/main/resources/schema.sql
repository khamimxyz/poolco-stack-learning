CREATE TABLE patient (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth VARCHAR(20),
    gender VARCHAR(10),
    phone_no VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE patient_address (
    id INT NOT NULL AUTO_INCREMENT,
    patient_id INT NOT NULL,
    address VARCHAR(50),
    suburb VARCHAR(50),
    state VARCHAR(20),
    postcode VARCHAR(10),
    PRIMARY KEY (id)
);