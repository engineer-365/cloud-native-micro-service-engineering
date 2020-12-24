CREATE DATABASE fleashop DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE fleashop;

CREATE USER 'fleashop_user'@'%' IDENTIFIED BY 'fleashop_password';
GRANT ALL PRIVILEGES ON fleashop.* TO 'fleashop_user'@'%' WITH GRANT OPTION;
