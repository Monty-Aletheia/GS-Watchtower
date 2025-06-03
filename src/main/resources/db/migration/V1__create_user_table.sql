CREATE TABLE t_wt_users
(
    id         RAW(16) DEFAULT SYS_GUID() PRIMARY KEY, -- Usando RAW(16) para armazenar o UUID
    name       VARCHAR2(255) NOT NULL,
    email      VARCHAR2(255) UNIQUE NOT NULL,
    password   VARCHAR2(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);