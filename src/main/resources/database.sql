CREATE DATABASE IF NOT EXISTS addressbook;
USE addressbook;

CREATE TABLE IF NOT EXISTS contact (
    id BIGINT NOT NULL AUTO_INCREMENT,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

INSERT INTO contact(last_name, first_name, address, mobile_number, email_address) VALUES
('Jupiter', 'Julian', 'San Roque St.', '09161234567', 'julianjupiter.io@gmail.com'),
('Rizal', 'Jose', 'Calamba, Laguna', '09161234568', 'joserizal@gmail.com'),
('Bonifacio', 'Andress', 'Tondo, Manila', '09161234569', 'andresbonifacio@gmail.com');