DELIMITER $$

drop procedure if exists sp_init_user;
CREATE PROCEDURE sp_init_user(
    IN p_username VARCHAR(25),
    IN p_password VARCHAR(255),
    IN p_phone VARCHAR(11)
)
BEGIN
    -- 用户id
    DECLARE v_user_id INT;
    -- 账单分类：支出（一级）
    DECLARE v_food_id bigint;
    DECLARE v_clothes_id bigint;
    DECLARE v_wuye_id bigint;
    DECLARE v_transport_id bigint;
    DECLARE v_commumication_id bigint;
    DECLARE v_play_id bigint;
    DECLARE v_study_id bigint;
    DECLARE v_worldly_wisdom_id bigint;
    DECLARE v_health_id bigint;
    DECLARE v_finance_and_insurance_id bigint;
    DECLARE v_other_category_id bigint;
    -- 账单分类：收入（一级）
    DECLARE v_income_id INT;
    DECLARE v_other_income_id INT;
    -- 账户类型
    DECLARE v_cash_account_id bigint;
    DECLARE v_savings_account_id bigint;
    DECLARE v_virtual_account_id bigint;
    DECLARE v_invest_account_id bigint;
    DECLARE v_lof_account_id bigint;
    DECLARE v_credit_account_id bigint;
    DECLARE v_liability_account_id bigint;


    -- 声明一个变量，用于捕获异常
    declare exit handler for sqlexception begin
        -- 如果发生错误，回滚事务
        rollback ;
        -- 可以选择抛出错误或记录日志
        resignal ;
    end;

    -- 开始事务
    start transaction;

    -- 插入用户信息，并获取其 ID
    INSERT INTO user (username, password, phone)
    VALUES (p_username, p_password, p_phone);
    SET v_user_id = LAST_INSERT_ID();


    -- 默认账本
    INSERT INTO account_book (name, icon, type, sort, user_id)
    VALUES ('默认账本', null, 1, 1, v_user_id);


    -- 默认成员类型
    insert into member_type (name, icon, sort, user_id)
    values ('本人', null, 1, v_user_id),
           ('老公', null, 2, v_user_id),
           ('老婆', null, 3, v_user_id),
           ('子女', null, 4, v_user_id),
           ('父母', null, 5, v_user_id),
           ('家庭公用', null, 6, v_user_id);


    -- 默认项目类型
    insert into project_type (name, icon, sort, user_id)
    values ('孩子教育', null, 1, v_user_id),
           ('出差', null, 2, v_user_id),
           ('汽车', null, 3, v_user_id),
           ('购物', null, 4, v_user_id),
           ('宝宝', null, 5, v_user_id),
           ('红包', null, 6, v_user_id),
           ('公益', null, 7, v_user_id),
           ('旅游', null, 8, v_user_id),
           ('腐败', null, 9, v_user_id),
           ('回家过年', null, 10, v_user_id),
           ('人情', null, 11, v_user_id),
           ('日常', null, 12, v_user_id),
           ('朋友聚会', null, 13, v_user_id),
           ('装修', null, 14, v_user_id),
           ('医疗', null, 15, v_user_id);


    -- 默认商家类型
    INSERT INTO shop_type (name, icon, sort, user_id)
    VALUES
        ('其他', null, 1, v_user_id),
        ('药店', null, 2, v_user_id),
        ('超市', null, 3, v_user_id),
        ('商场', null, 4, v_user_id),
        ('公交', null, 5, v_user_id),
        ('地铁', null, 6, v_user_id),
        ('医院', null, 7, v_user_id),
        ('小卖部', null, 8, v_user_id),
        ('水果店', null, 9, v_user_id),
        ('4S店', null, 10, v_user_id),
        ('超市', null, 11, v_user_id),
        ('便利店', null, 12, v_user_id),
        ('书店', null, 13, v_user_id),
        ('加油站', null, 14, v_user_id),
        ('饭馆', null, 15, v_user_id),
        ('菜市场', null, 16, v_user_id),
        ('停车场', null, 17, v_user_id),
        ('家具城', null, 18, v_user_id),
        ('淘宝', null, 19, v_user_id),
        ('京东', null, 20, v_user_id);


    -- 账单分类：支出（一级）
    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('食品酒水', 1, null, 1, NULL, v_user_id);
    SET v_food_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('衣服饰品', 1, null, 2, NULL, v_user_id);
    SET v_clothes_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('居家物业', 1, null, 3, NULL, v_user_id);
    SET v_wuye_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('行车交通', 1, null, 4, NULL, v_user_id);
    SET v_transport_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('交流通讯', 1, null, 5, NULL, v_user_id);
    SET v_commumication_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('休闲娱乐', 1, null, 6, NULL, v_user_id);
    SET v_play_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('学习进修', 1, null, 7, NULL, v_user_id);
    SET v_study_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('人情往来', 1, null, 8, NULL, v_user_id);
    SET v_worldly_wisdom_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('医疗保健', 1, null, 9, NULL, v_user_id);
    SET v_health_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('金融保险', 1, null, 10, NULL, v_user_id);
    SET v_finance_and_insurance_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('其他杂项', 1, null, 11, NULL, v_user_id);
    SET v_other_category_id = LAST_INSERT_ID();

    -- 账单分类：收入（一级）
    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('职业收入', 2, null, 1, NULL, v_user_id);
    SET v_income_id = LAST_INSERT_ID();

    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES ('其他收入', 2, null, 2, NULL, v_user_id);
    SET v_other_income_id = LAST_INSERT_ID();


    -- 账单分类：收入（二级）
    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    VALUES
        -- 食品酒水
        ('早午晚餐', 1, null, 1, v_food_id, v_user_id),
        ('烟酒茶', 1, null, 2, v_food_id, v_user_id),
        ('水果', 1, null, 3, v_food_id, v_user_id),
        ('零食', 1, null, 4, v_food_id, v_user_id),
        ('饮料', 1, null, 5, v_food_id, v_user_id),
        -- 衣服饰品
        ('衣服裤子', 1, null, 1, v_clothes_id, v_user_id),
        ('鞋帽包包', 1, null, 2, v_clothes_id, v_user_id),
        ('化妆饰品', 1, null, 3, v_clothes_id, v_user_id),
        -- 居家物业
        ('日常用品', 1, null, 1, v_wuye_id, v_user_id),
        ('水电煤气', 1, null, 2, v_wuye_id, v_user_id),
        ('房租', 1, null, 3, v_wuye_id, v_user_id),
        ('物业管理', 1, null, 4, v_wuye_id, v_user_id),
        ('维修保养', 1, null, 5, v_wuye_id, v_user_id),
        -- 行车交通
        ('公共交通', 1, null, 1, v_transport_id, v_user_id),
        ('打车租车', 1, null, 2, v_transport_id, v_user_id),
        ('私家车费用', 1, null, 3, v_transport_id, v_user_id),
        -- 交流通讯
        ('座机费', 1, null, 1, v_commumication_id, v_user_id),
        ('手机费', 1, null, 2, v_commumication_id, v_user_id),
        ('上网费', 1, null, 3, v_commumication_id, v_user_id),
        ('邮寄费', 1, null, 4, v_commumication_id, v_user_id),
        -- 休闲娱乐
        ('运动健身', 1, null, 1, v_play_id, v_user_id),
        ('交际聚会', 1, null, 2, v_play_id, v_user_id),
        ('休闲玩乐', 1, null, 3, v_play_id, v_user_id),
        ('宠物宝贝', 1, null, 4, v_play_id, v_user_id),
        ('旅游度假', 1, null, 5, v_play_id, v_user_id),
        -- 学习进修
        ('数码装备', 1, null, 1, v_study_id, v_user_id),
        ('书刊杂志', 1, null, 2, v_study_id, v_user_id),
        ('培训进修', 1, null, 3, v_study_id, v_user_id),
        -- 人情往来
        ('送礼请客', 1, null, 1, v_worldly_wisdom_id, v_user_id),
        ('孝敬长辈', 1, null, 2, v_worldly_wisdom_id, v_user_id),
        ('还人钱物', 1, null, 3, v_worldly_wisdom_id, v_user_id),
        ('慈善捐助', 1, null, 4, v_worldly_wisdom_id, v_user_id),
        -- 医疗保健
        ('药品费', 1, null, 1, v_health_id, v_user_id),
        ('报检费', 1, null, 2, v_health_id, v_user_id),
        ('美容费', 1, null, 3, v_health_id, v_user_id),
        ('医疗费', 1, null, 4, v_health_id, v_user_id),
        -- 金融保险
        ('银行手续', 1, null, 1, v_finance_and_insurance_id, v_user_id),
        ('投资亏损', 1, null, 2, v_finance_and_insurance_id, v_user_id),
        ('按揭还款', 1, null, 3, v_finance_and_insurance_id, v_user_id),
        ('消费税收', 1, null, 4, v_finance_and_insurance_id, v_user_id),
        ('利息支出', 1, null, 5, v_finance_and_insurance_id, v_user_id),
        ('赔偿罚款', 1, null, 6, v_finance_and_insurance_id, v_user_id),
        -- 其他杂项
        ('其他支出', 1, null, 1, v_other_category_id, v_user_id),
        ('意外丢失', 1, null, 2, v_other_category_id, v_user_id),
        ('烂账损失', 1, null, 3, v_other_category_id, v_user_id);


    -- 账单分类：支出（二级）
    INSERT INTO bill_category (name, type, icon, sort, parent_id, user_id)
    values
        -- 职业收入
        ('工资收入', 2, null, 1, v_income_id, v_user_id),
        ('利息收入', 2, null, 2, v_income_id, v_user_id),
        ('加班收入', 2, null, 3, v_income_id, v_user_id),
        ('奖金收入', 2, null, 4, v_income_id, v_user_id),
        ('投资收入', 2, null, 5, v_income_id, v_user_id),
        ('兼职收入', 2, null, 6, v_income_id, v_user_id),
        -- 其他收入
        ('礼金收入', 2, null, 1, v_other_income_id, v_user_id),
        ('中奖收入', 2, null, 2, v_other_income_id, v_user_id),
        ('意外来钱', 2, null, 3, v_other_income_id, v_user_id),
        ('经营所得', 2, null, 4, v_other_income_id, v_user_id);

    -- 账户类型（一级）
    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('现金账户', null, 1, NULL, v_user_id);
    SET v_cash_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('储蓄账户', NULL, 2, NULL, v_user_id);
    SET v_savings_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('虚拟账户', NULL, 3, NULL, v_user_id);
    SET v_virtual_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('投资账户', NULL, 4, NULL, v_user_id);
    SET v_invest_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('债权账户', NULL, 5, NULL, v_user_id);
    SET v_lof_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('信用账户', NULL, 6, NULL, v_user_id);
    SET v_credit_account_id = LAST_INSERT_ID();

    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES ('负债账户', NULL, 7, NULL, v_user_id);
    SET v_liability_account_id = LAST_INSERT_ID();

    -- 账户类型（二级）
    INSERT INTO account_type (name, icon, sort, parent_id, user_id)
    VALUES
        -- 现金账户
        ('现金', null, 1, v_cash_account_id, v_user_id),
        -- 储蓄账户
        ('银行卡', null, 1, v_savings_account_id, v_user_id),
        -- 虚拟账户
        ('支付宝余额', null, 1, v_virtual_account_id, v_user_id),
        ('微信钱包', null, 2, v_virtual_account_id, v_user_id),
        -- 投资账户
        ('基金账户', null, 1, v_invest_account_id, v_user_id),
        ('股票账户', null, 2, v_invest_account_id, v_user_id),
        -- 债权账户
        ('应收款项', null, 1, v_lof_account_id, v_user_id),
        -- 信用账户
        ('信用卡', null, 1, v_credit_account_id, v_user_id),
        -- 负债账户
        ('应付款项', null, 1, v_liability_account_id, v_user_id);

    commit;

END$$

DELIMITER ;

-- 测试插入用户数据
CALL sp_init_user('admin', 'admin2', '11111111111');