-- 新建一个用户，密码： 123456 加密后的值
insert into user (id, username, password, phone, nickname, avatar, reg_time, last_login_time, last_login_ip,
                  last_login_device, status, last_update_time)
values (1, 'zhangsan', '$argon2i$v=19$m=65536,t=3,p=2$hQRvNWz6mrL7+uvrPsSPhg$PdqrcPLfy8nYz6h2fnI+k9NbdPY8t3vLIsteoiZu0sA',
        '18701362075', '张三',
        'https://img1.baidu.com/it/u=2450167307,438710338&fm=253&fmt=auto&app=138&f=JPEG?w=320&h=320', now(), null,
        null, null, 1, null);


-- 为用户1创建一个默认账本
insert into account_book (name, icon, type, sort, user_id, create_time, last_update_time)
values ('默认账本', null, 1, 1, 1, now(), null);


-- 为用户1创建成员类型
insert into member_type (name, icon, sort, user_id, create_time, last_update_time)
values ('本人', null, 1, 1, now(), null),
       ('老公', null, 2, 1, now(), null),
       ('老婆', null, 3, 1, now(), null),
       ('子女', null, 4, 1, now(), null),
       ('父母', null, 5, 1, now(), null),
       ('家庭公用', null, 6, 1, now(), null);


-- 为用户1创建项目类型
insert into project_type (name, icon, sort, user_id, create_time, last_update_time)
values ('孩子教育', null, 1, 1, now(), null),
       ('出差', null, 2, 1, now(), null),
       ('汽车', null, 3, 1, now(), null),
       ('购物', null, 4, 1, now(), null),
       ('宝宝', null, 5, 1, now(), null),
       ('红包', null, 6, 1, now(), null),
       ('公益', null, 7, 1, now(), null),
       ('旅游', null, 8, 1, now(), null),
       ('腐败', null, 9, 1, now(), null),
       ('回家过年', null, 10, 1, now(), null),
       ('人情', null, 11, 1, now(), null),
       ('日常', null, 12, 1, now(), null),
       ('朋友聚会', null, 13, 1, now(), null),
       ('装修', null, 14, 1, now(), null),
       ('医疗', null, 15, 1, now(), null);


-- 为用户1创建商家类型
 insert into shop_type (name, icon, sort, user_id, create_time, last_update_time)
 values
     ('其他', null, 1, 1, now(), null),
     ('药店', null, 2, 1, now(), null),
     ('超市', null, 3, 1, now(), null),
     ('商场', null, 4, 1, now(), null),
     ('公交', null, 5, 1, now(), null),
     ('地铁', null, 6, 1, now(), null),
     ('医院', null, 7, 1, now(), null),
     ('小卖部', null, 8, 1, now(), null),
     ('水果店', null, 9, 1, now(), null),
     ('4S店', null, 10, 1, now(), null),
     ('超市', null, 11, 1, now(), null),
     ('便利店', null, 12, 1, now(), null),
     ('书店', null, 13, 1, now(), null),
     ('加油站', null, 14, 1, now(), null),
     ('饭馆', null, 15, 1, now(), null),
     ('菜市场', null, 16, 1, now(), null),
     ('停车场', null, 17, 1, now(), null),
     ('家具城', null, 18, 1, now(), null),
     ('淘宝', null, 19, 1, now(), null),
     ('京东', null, 20, 1, now(), null);


-- 为用户1创建分类及其子分类（支出）
INSERT INTO bill_category (id, name, type, icon, sort, parent_id, user_id, create_time, last_update_time)
VALUES
    -- 食品酒水
    (1, '食品酒水', 1, NULL, 1, NULL, 1, NOW(), NULL),
        (2, '早午晚餐', 1, NULL, 1, 1, 1, NOW(), NULL),
        (3, '烟酒茶', 1, NULL, 2, 1, 1, NOW(), NULL),
        (4, '水果', 1, NULL, 3, 1, 1, NOW(), NULL),
        (5, '零食', 1, NULL, 4, 1, 1, NOW(), NULL),
        (6, '饮料', 1, NULL, 5, 1, 1, NOW(), NULL),

    -- 衣服饰品
    (7, '衣服饰品', 1, NULL, 2, NULL, 1, NOW(), NULL),
        (8, '衣服裤子', 1, NULL, 1, 7, 1, NOW(), NULL),
        (9, '鞋帽包包', 1, NULL, 2, 7, 1, NOW(), NULL),
        (10, '化妆饰品', 1, NULL, 3, 7, 1, NOW(), NULL),

    -- 居家物业
    (11, '居家物业', 1, NULL, 3, NULL, 1, NOW(), NULL),
        (12, '日常用品', 1, NULL, 1, 11, 1, NOW(), NULL),
        (13, '水电煤气', 1, NULL, 2, 11, 1, NOW(), NULL),
        (14, '房租', 1, NULL, 3, 11, 1, NOW(), NULL),
        (15, '物业管理', 1, NULL, 4, 11, 1, NOW(), NULL),
        (16, '维修保养', 1, NULL, 5, 11, 1, NOW(), NULL),

    -- 行车交通
    (17, '行车交通', 1, NULL, 4, NULL, 1, NOW(), NULL),
        (18, '公共交通', 1, NULL, 1, 17, 1, NOW(), NULL),
        (19, '打车租车', 1, NULL, 2, 17, 1, NOW(), NULL),
        (20, '私家车费用', 1, NULL, 3, 17, 1, NOW(), NULL),

    -- 交流通讯
    (21, '交流通讯', 1, NULL, 5, NULL, 1, NOW(), NULL),
        (22, '座机费', 1, NULL, 1, 21, 1, NOW(), NULL),
        (23, '手机费', 1, NULL, 2, 21, 1, NOW(), NULL),
        (24, '上网费', 1, NULL, 3, 21, 1, NOW(), NULL),
        (25, '邮寄费', 1, NULL, 4, 21, 1, NOW(), NULL),

    -- 休闲娱乐
    (26, '休闲娱乐', 1, NULL, 6, NULL, 1, NOW(), NULL),
        (27, '运动健身', 1, NULL, 1, 26, 1, NOW(), NULL),
        (28, '交际聚会', 1, NULL, 2, 26, 1, NOW(), NULL),
        (29, '休闲玩乐', 1, NULL, 3, 26, 1, NOW(), NULL),
        (30, '宠物宝贝', 1, NULL, 4, 26, 1, NOW(), NULL),
        (31, '旅游度假', 1, NULL, 5, 26, 1, NOW(), NULL),

    -- 学习进修
    (32, '学习进修', 1, NULL, 7, NULL, 1, NOW(), NULL),
        (33, '数码装备', 1, NULL, 1, 32, 1, NOW(), NULL),
        (34, '书刊杂志', 1, NULL, 2, 32, 1, NOW(), NULL),
        (35, '培训进修', 1, NULL, 3, 32, 1, NOW(), NULL),

    -- 人情往来
    (36, '人情往来', 1, NULL, 8, NULL, 1, NOW(), NULL),
        (37, '送礼请客', 1, NULL, 1, 36, 1, NOW(), NULL),
        (38, '孝敬长辈', 1, NULL, 2, 36, 1, NOW(), NULL),
        (39, '还人钱物', 1, NULL, 3, 36, 1, NOW(), NULL),
        (40, '慈善捐助', 1, NULL, 4, 36, 1, NOW(), NULL),

    -- 医疗保健
    (41, '医疗保健', 1, NULL, 9, NULL, 1, NOW(), NULL),
        (42, '药品费', 1, NULL, 1, 41, 1, NOW(), NULL),
        (43, '报检费', 1, NULL, 2, 41, 1, NOW(), NULL),
        (44, '美容费', 1, NULL, 3, 41, 1, NOW(), NULL),
        (45, '医疗费', 1, NULL, 4, 41, 1, NOW(), NULL),

    -- 金融保险
    (46, '金融保险', 1, NULL, 10, NULL, 1, NOW(), NULL),
        (47, '银行手续', 1, NULL, 1, 46, 1, NOW(), NULL),
        (48, '投资亏损', 1, NULL, 2, 46, 1, NOW(), NULL),
        (49, '按揭还款', 1, NULL, 3, 46, 1, NOW(), NULL),
        (50, '消费税收', 1, NULL, 4, 46, 1, NOW(), NULL),
        (51, '利息支出', 1, NULL, 5, 46, 1, NOW(), NULL),
        (52, '赔偿罚款', 1, NULL, 6, 46, 1, NOW(), NULL),

    -- 其他杂项
    (53, '其他杂项', 1, NULL, 11, NULL, 1, NOW(), NULL),
        (54, '其他支出', 1, NULL, 1, 53, 1, NOW(), NULL),
        (55, '意外丢失', 1, NULL, 2, 53, 1, NOW(), NULL),
        (56, '烂账损失', 1, NULL, 3, 53, 1, NOW(), NULL);


-- 为用户1创建分类及其子分类（收入）
insert into bill_category (id, name, type, icon, sort, parent_id, user_id, create_time, last_update_time)
values
    -- 职业收入
    ('57', '职业收入', 2, null, 1, null, 1, now(), null),
        ('58', '工资收入', 2, null, 1, '57', 1, now(), null),
        ('59', '利息收入', 2, null, 2, '57', 1, now(), null),
        ('60', '加班收入', 2, null, 3, '57', 1, now(), null),
        ('61', '奖金收入', 2, null, 4, '57', 1, now(), null),
        ('62', '投资收入', 2, null, 5, '57', 1, now(), null),
        ('63', '兼职收入', 2, null, 6, '57', 1, now(), null),

    -- 其他收入
    ('64', '其他收入', 2, null, 2, null, 1, now(), null),
        ('65', '礼金收入', 2, null, 1, '64', 1, now(), null),
        ('66', '中奖收入', 2, null, 2, '64', 1, now(), null),
        ('67', '意外来钱', 2, null, 3, '64', 1, now(), null),
        ('68', '经营所得', 2, null, 4, '64', 1, now(), null);


-- 为用户1创建账户类型
insert into account_type (id, name, icon, sort, parent_id, user_id, create_time, last_update_time)
values
    -- 现金账户
    ('1', '现金账户', null, 1, null, 1, now(), null),
        ('2', '现金', null, 1, '1', 1, now(), null),

    -- 储蓄账户
    ('3', '储蓄账户', null, 2, null, 1, now(), null),
        ('4', '银行卡', null, 1, '3', 1, now(), null),

    -- 虚拟账户
    ('5', '虚拟账户', null, 3, null, 1, now(), null),
        ('6', '支付宝余额', null, 1, '5', 1, now(), null),
        ('7', '微信钱包', null, 2, '5', 1, now(), null),

    -- 投资账户
    ('8', '投资账户', null, 4, null, 1, now(), null),
        ('9', '基金账户', null, 1, '8', 1, now(), null),
        ('10', '股票账户', null, 2, '8', 1, now(), null),

    -- 债券账户
    ('11', '债券账户', null, 5, null, 1, now(), null),
        ('12', '应收款项', null, 1, '11', 1, now(), null),

    -- 信用账户
    ('13', '信用账户', null, 6, null, 1, now(), null),
        ('14', '信用卡', null, 1, '13', 1, now(), null),

    -- 负债账户
    ('15', '负债账户', null, 7, null, 1, now(), null),
        ('16', '应付款项', null, 1, '15', 1, now(), null);


-- 为用户1插入支出、收入类型的账单记录
insert into bill (id, account_book_id, type, bill_category_id, account_type_id, amount, member_type_id, date,
                  shop_type_id, project_type_id, user_id, notes, create_time, last_update_time)
values (1, 1, 1, 2, 1, 100.00, 1, '2023-07-01', 1, 1, 1, '测试支出记录', now(), null),
       (2, 1, 1, 2, 2, 200.00, 1, '2023-07-02', 2, 2, 1, '测试支出记录', now(), null),
       (3, 1, 1, 2, 3, 300.00, 2, '2023-07-03', 3, 3, 1, '测试支出记录', now(), null),
       (4, 1, 1, 2, 4, 400.00, 2, '2023-07-04', 4, 4, 1, '测试支出记录', now(), null),
       (5, 1, 1, 3, 5, 500.00, 3, '2023-07-05', 5, 5, 1, '测试支出记录', now(), null),
       (6, 1, 1, 3, 6, 600.00, 3, '2023-07-06', 6, 6, 1, '测试支出记录', now(), null),
       (7, 1, 1, 3, 7, 700.00, 4, '2023-07-07', 7, 7, 1, '测试支出记录', now(), null),
       (8, 1, 1, 4, 8, 800.00, 4, '2023-07-08', 8, 8, 1, '测试支出记录', now(), null),
       (9, 1, 1, 4, 9, 900.00, 5, '2023-07-09', 9, 9, 1, '测试支出记录', now(), null),
       (10, 1, 1, 4, 10, 1000.00, 5, '2023-07-10', 10, 10, 1, '测试支出记录', now(), null);