CREATE DATABASE `interview-management`
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `interview-management`;

CREATE TABLE account
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(100) NOT NULL UNIQUE,
    password      VARCHAR(100),
    full_name     VARCHAR(100),
    gender        VARCHAR(10),
    date_of_birth DATE,
    email         VARCHAR(100),
    address       TEXT,
    phone_number  VARCHAR(20),
    department    VARCHAR(100),
    role          VARCHAR(20),
    is_active     BOOLEAN DEFAULT TRUE,
    created_by    VARCHAR(100),
    created_date  DATETIME,
    updated_by    VARCHAR(100),
    updated_date  DATETIME
);

INSERT INTO account (username,
                     password,
                     full_name,
                     email,
                     role,
                     is_active,
                     created_by,
                     created_date,
                     updated_by,
                     updated_date)
VALUES ('admin',
        '$2a$10$cNadva6mq0Q.4DRGscyY2elKGcRlTraoU73tSDgzW3Wy.VSce.ALW',
        'admin',
        'admin@gmail.com',
        'ADMIN',
        TRUE,
        'admin',
        NOW(),
        'admin',
        NOW());

CREATE TABLE job
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255),
    level           VARCHAR(100),
    salary          VARCHAR(100),
    start_date      DATE,
    end_date        DATE,
    working_address VARCHAR(255),
    description     TEXT,
    status          VARCHAR(50),
    created_by      VARCHAR(100),
    created_date    DATETIME,
    updated_by      VARCHAR(100),
    updated_date    DATETIME
);

CREATE TABLE job_skill
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id       BIGINT,
    skill        VARCHAR(100),
    created_by   VARCHAR(100),
    created_date DATETIME,
    updated_by   VARCHAR(100),
    updated_date DATETIME
);

CREATE TABLE candidate
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name          VARCHAR(255),
    email              VARCHAR(255),
    date_of_birth      DATE,
    address            VARCHAR(255),
    phone_number       VARCHAR(50),
    gender             VARCHAR(20),
    cv_file_path       VARCHAR(255),
    filename           VARCHAR(255),
    position           VARCHAR(100),
    year_of_experience INT,
    level              VARCHAR(100),
    note               TEXT,
    status             VARCHAR(50),
    created_by         VARCHAR(100),
    created_date       DATETIME,
    updated_by         VARCHAR(100),
    updated_date       DATETIME
);

CREATE TABLE candidate_skill
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    candidate_id BIGINT,
    skill        VARCHAR(100),
    created_by   VARCHAR(100),
    created_date DATETIME,
    updated_by   VARCHAR(100),
    updated_date DATETIME
);

CREATE TABLE interview
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id         BIGINT,
    candidate_id   BIGINT,
    interviewer_id BIGINT,
    recruiter_id   BIGINT,
    schedule_date  DATE,
    from_hour      TIME(6),
    to_hour        TIME(6),
    meeting_id     VARCHAR(255),
    created_by     VARCHAR(100),
    created_date   DATETIME,
    updated_by     VARCHAR(100),
    updated_date   DATETIME
);
