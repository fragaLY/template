CREATE SCHEMA IF NOT EXISTS demo;

CREATE TABLE IF NOT EXISTS demo."user"
(
    id       UUID PRIMARY KEY,
    role     VARCHAR(14) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS demo.notice
(
    id                           UUID PRIMARY KEY,
    date                         TIMESTAMP WITHOUT TIME ZONE,
    place                        VARCHAR(255),
    personal_identification_code VARCHAR(255),
    case_present                 BOOLEAN,
    type                         VARCHAR(15)  NOT NULL,
    status                       VARCHAR(10)  NOT NULL,
    comment                      TEXT,
    deadline_at                  TIMESTAMP WITHOUT TIME ZONE,
    created_by                   VARCHAR(255) NOT NULL,
    updated_by                   VARCHAR(255) NOT NULL,
    created_at                   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at                   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

DELETE
FROM demo."user";
INSERT INTO demo.user
VALUES ('f461dd29-6b6e-49a0-8412-2f125596287d', 'USER', '$2a$10$jMqMhcCh/.rArwHdnHuE2.qGNdvcmTT.mdIIg2.uO12FRrqHij0S2');
INSERT INTO demo.user
VALUES ('09401b0a-2c4f-4a7a-94e1-65ab3ad3a418', 'ADMINISTRATOR', '$2a$10$4dzA/foZ4Es.yN3Cr62mhuomYL/T5o08Mz1vbo9aFI82.GPUS581O');
INSERT INTO demo.user
VALUES ('541f0b04-c019-4a5b-9b0d-30b6ac0390da', 'OFFICIAL', '$2a$10$1WTj8/6kZkllwoNQFzlqF.a6emKVk/8v0vXhvI7arqq9cfn3iIr0K');

DELETE
FROM demo."notice";
INSERT INTO demo.notice
VALUES ('2c72641d-38eb-47d4-aa44-cc7e49bdc5d4',
        CURRENT_TIMESTAMP,
        'Mogilev',
        'PID123456789',
        TRUE,
        'HOSPITAL',
        'IN_PROCESS',
        'Comment text',
        '2024-01-07 16:37:06.647235',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO demo.notice
VALUES ('918fd7a0-c606-42b5-8ce9-bc2fa394a999',
        CURRENT_TIMESTAMP,
        'Mogilev',
        'PID123456789',
        TRUE,
        'DENTISTRY',
        'IN_PROCESS',
        'Comment text',
        CURRENT_TIMESTAMP - INTERVAL '15 MINUTES',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);


INSERT INTO demo.notice
VALUES ('696ffe0d-d095-4da8-9651-aee8da7f5dbd',
        CURRENT_TIMESTAMP,
        'Mogilev',
        'PID123456789',
        TRUE,
        'FAMILY_DOCTOR',
        'APPROVED',
        'Approved by administrator notice',
        CURRENT_TIMESTAMP + INTERVAL '16 MINUTES',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        '09401b0a-2c4f-4a7a-94e1-65ab3ad3a418',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO demo.notice
VALUES ('61439ec6-aa85-4491-b255-e31f66e2febd',
        CURRENT_TIMESTAMP,
        'Mogilev',
        'PID123456789',
        TRUE,
        'PRIVATE_PERSON',
        'REJECTED',
        'Rejected by official notice',
        CURRENT_TIMESTAMP + INTERVAL '15 MINUTES',
        'f461dd29-6b6e-49a0-8412-2f125596287d',
        '541f0b04-c019-4a5b-9b0d-30b6ac0390da',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);