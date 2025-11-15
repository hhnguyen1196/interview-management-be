CREATE
DATABASE `interview-management`
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE
`interview-management`;

CREATE TABLE job
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
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