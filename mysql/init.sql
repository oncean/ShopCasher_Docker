-- 创建数据库
create database IF NOT EXISTS `shopcasher` default character set utf8 collate utf8_general_ci;

use mysql;
update user set host = '%' where user = 'root';
FLUSH PRIVILEGES;