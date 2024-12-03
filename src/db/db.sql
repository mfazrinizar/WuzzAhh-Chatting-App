CREATE DATABASE wuzzahh;

USE wuzzahh;

CREATE TABLE users (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),           
    username VARCHAR(6) UNIQUE NOT NULL,            
    name VARCHAR(100) NOT NULL,                     
    password VARCHAR(256) NOT NULL                  
);

CREATE TABLE friends (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),           
    id_user_first CHAR(36) NOT NULL,                    
    id_user_second CHAR(36) NOT NULL,                   
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (id_user_first) REFERENCES users(id),
    FOREIGN KEY (id_user_second) REFERENCES users(id),
    UNIQUE (id_user_first, id_user_second),
    CHECK (id_user_first != id_user_second)          
);

CREATE TABLE chat_messages (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),           
    id_user_from CHAR(36) NOT NULL,                     
    id_user_to CHAR(36) NOT NULL,                       
    message VARCHAR(512) NOT NULL,                  
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    
    FOREIGN KEY (id_user_from) REFERENCES users(id),
    FOREIGN KEY (id_user_to) REFERENCES users(id)
);