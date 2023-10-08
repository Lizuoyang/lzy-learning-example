create schema dynamic_datasource_master;
create schema dynamic_datasource_slave;

use dynamic_datasource_master;
create table test_user(
  user_name varchar(255) not null comment '用户名'
);
insert into test_user (user_name) value ('master');

use dynamic_datasource_slave;
create table test_user(
  user_name varchar(255) not null comment '用户名'
);
insert into test_user (user_name) value ('slave');

use dynamic_datasource_master;
create table test_db_info(
    id int auto_increment primary key not null comment '主键Id',
    url varchar(255) not null comment '数据库URL',
    username varchar(255) not null comment '用户名',
    password varchar(255) not null comment '密码',
    driver_class_name varchar(255) not null comment '数据库驱动',
    name varchar(255) not null comment '数据库名称'
);
insert into test_db_info(url, username, password,driver_class_name, name)
value ('jdbc:mysql://192.168.56.102:3306/dynamic_datasource_slave?characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&useSSL=false',
       'root','123456','com.mysql.cj.jdbc.Driver','add_slave');
