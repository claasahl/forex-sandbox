CREATE TABLE Vote (
    ID int NOT NULL AUTO_INCREMENT,
    created_at datetime NOT NULL,
    user_id int NOT NULL,
    link_id int NOT NULL,
    PRIMARY KEY (ID)
); 