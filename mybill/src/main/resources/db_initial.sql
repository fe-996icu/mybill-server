-- 删除旧数据库并创建新数据库
-- drop database if exists mybill;
create database if not exists mybill;
use mybill;

-- 用户表
drop table if exists user;
create table user (
    id bigint primary key auto_increment comment '用户id',
    username varchar(25) not null unique comment '用户名（唯一）',
    password varchar(255) not null comment '用户密码（加密存储）',
    phone varchar(11) unique null comment '手机号（唯一）',
    nickname varchar(25) null comment '昵称',
    avatar varchar(255) null comment '头像url',
    reg_time datetime not null default now() comment '注册时间',
    last_login_time datetime null comment '上次登录时间',
    last_login_ip varchar(255) null comment '上次登录ip',
    last_login_device varchar(255) null comment '上次登录设备信息',
    status tinyint not null default 1 comment '账号状态：1-正常, 0-禁用',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='用户表';

-- 账本表
drop table if exists account_book;
create table account_book (
    id bigint primary key auto_increment comment '账本id',
    name varchar(25) not null comment '账本名称',
    icon varchar(255) null comment '账本图标',
    type tinyint not null default 1 comment '账本类型：1-默认账本, 2-家庭账本, 3-生意账本',
    sort int not null comment '排序字段',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='账本表';

-- 账户类型表
drop table if exists account_type;
create table account_type (
    id bigint primary key auto_increment comment '账户类型id',
    name varchar(25) not null comment '账户类型名称',
    icon varchar(255) null comment '账户类型图标',
    sort int not null comment '排序字段',
    parent_id bigint null comment '父账户类型id（逻辑外键）',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='账户类型表';

-- 商家类型表
drop table if exists shop_type;
create table shop_type (
    id bigint primary key auto_increment comment '商家类型id',
    name varchar(25) not null comment '商家类型名称',
    icon varchar(255) null comment '商家类型图标',
    sort int not null comment '排序字段',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='商家类型表';

-- 成员类型表
drop table if exists member_type;
create table member_type (
    id bigint primary key auto_increment comment '成员类型id',
    name varchar(25) not null comment '成员类型名称',
    icon varchar(255) null comment '成员类型图标',
    sort int not null comment '排序字段',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='成员类型表';

-- 项目类型表
drop table if exists project_type;
create table project_type (
    id bigint primary key auto_increment comment '项目类型id',
    name varchar(25) not null comment '项目类型名称',
    icon varchar(255) null comment '项目类型图标',
    sort int not null comment '排序字段',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='项目类型表';

-- 账单分类表
drop table if exists bill_category;
create table bill_category (
    id bigint primary key auto_increment comment '账单分类id',
    name varchar(255) not null comment '账单分类名称',
    type tinyint not null default 1 comment '账单类型：1-支出, 2-收入',
    icon varchar(255) null comment '分类图标',
    sort int not null comment '排序字段',
    parent_id bigint null comment '父分类id（逻辑外键）',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='账单分类表';

-- 账单表
drop table if exists bill;
create table bill (
    id bigint primary key auto_increment comment '账单id',
    account_book_id bigint not null comment '账本id（逻辑外键）',
    type tinyint not null default 1 comment '账单类型：1-支出, 2-收入',
    bill_category_id bigint not null comment '账单分类id（逻辑外键）',
    account_type_id bigint not null comment '账户类型id（逻辑外键）',
    amount decimal(10,2) not null comment '金额',
    member_type_id bigint not null comment '成员类型id（逻辑外键）',
    date datetime not null default now() comment '账单日期',
    shop_type_id bigint null comment '商家类型id（逻辑外键）',
    project_type_id bigint null comment '项目类型id（逻辑外键）',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    notes varchar(1000) null comment '备注',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='账单表';

-- 附件表
drop table if exists attachment;
create table attachment (
    id bigint primary key auto_increment comment '附件id',
    file_path varchar(500) not null comment '文件路径',
    sort tinyint null comment '排序',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    bill_id bigint not null comment '账单id（逻辑外键）',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='附件表';

-- 模板表
drop table if exists template;
create table template (
    id bigint primary key auto_increment comment '模板id',
    name varchar(255) not null comment '模板名称',
    type tinyint not null default 1 comment '账单类型：1-支出, 2-收入',
    bill_category_id bigint not null comment '账单分类id（逻辑外键）',
    account_type_id bigint not null comment '账户类型id（逻辑外键）',
    amount decimal(10,2) not null comment '金额',
    member_type_id bigint not null comment '成员类型id（逻辑外键）',
    shop_type_id bigint null comment '商家类型id（逻辑外键）',
    project_type_id bigint null comment '项目类型id（逻辑外键）',
    user_id bigint not null comment '所属用户id（逻辑外键）',
    account_book_id bigint not null comment '账本id（逻辑外键）',
    notes varchar(1000) null comment '备注',
    create_time datetime default current_timestamp comment '创建时间',
    last_update_time datetime default current_timestamp on update current_timestamp comment '最后更新时间'
) engine=InnoDB default charset=utf8mb4 comment='模板表';
