CREATE DATABASE `interview-management `
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE job (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    level VARCHAR(100),
    salary VARCHAR(255),
    start_date DATE,
    end_date DATE,
    working_address VARCHAR(255),
    description TEXT,
    status VARCHAR(50),
    created_by VARCHAR(255),
    created_date DATETIME,
    updated_by VARCHAR(255),
    updated_date DATETIME,
    PRIMARY KEY (id)
)

CREATE TABLE job_skill (
    id BIGINT NOT NULL AUTO_INCREMENT,
    job_id BIGINT,
    skill VARCHAR(100),
    created_by VARCHAR(255),
    created_date DATETIME,
    updated_by VARCHAR(255),
    updated_date DATETIME
    PRIMARY KEY (id)
) ENGINE = InnoDB
