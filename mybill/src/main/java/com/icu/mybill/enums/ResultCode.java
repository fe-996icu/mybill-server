package com.icu.mybill.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 业务状态码
 */
@Getter
public enum ResultCode {
    SUCCESS(200,"ok"),
    PARAMETER_FAIL(400,"请求参数错误"),
    NOT_PERMISSION(401,"无权限访问"),
    NOT_AUTHORIZED(403,"访问受限"),
    RESOURCE_NOT_FOUND(404,"资源未找到"),
    METHOD_NOT_SUPPORTED(405,"请求方法不支持"),
    SERVER_ERROR(500,"对不起,服务器异常,请稍后重试"),

    SQL_SYNTAX_ERROR(5001,"SQL语法错误"),
    DATABASE_CONNECTION_FAILURE(5002,"数据库连接失败"),
    UNIQUE_CONSTRAINT_CONFLICT(5003,"唯一约束冲突"),
    FOREIGN_KEY_CONSTRAINT_CONFLICT(5004,"外键约束冲突"),
    DATA_DOES_NOT_EXIST(5005,"数据不存在"),
    DATABASE_TIMEOUT(5006,"数据库超时"),


    // 新增的数据不是自己的
    CREATE_DATA_NOT_SELF_ERROR(5101,"创建的数据不是本人数据"),
    // 删除的数据不是自己的
    DELETE_DATA_NOT_SELF_ERROR(5102,"删除的数据不是本人数据"),
    // 修改的数据不是自己的
    UPDATE_DATA_NOT_SELF_ERROR(5103,"修改的数据不是本人数据"),
    // 查询的数据不是自己的
    QUERY_DATA_NOT_SELF_ERROR(5104,"查询的数据不是本人数据"),
    REFERENCE_PARENT_DATA_NOT_SELF_ERROR(5105,"引用的父级数据不是本人数据"),
    REFERENCE_CHILDREN_DATA_NOT_SELF_ERROR(5105,"引用的二级数据不是本人数据"),
    NOT_QUERY_NEED_OPERATE_DATA_ERROR(5106,"未查询到需要操作的数据"),

    UPDATE_REQUIRE_ONE_FIELD_ERROR(5120,"至少更新一个字段"),
    UPDATE_SORT_LIST_EMPTY_ERROR(5121,"排序列表为空"),



    // 10000~10099：用户
    USERNAME_OR_PASSWORD_ERROR(10001,"用户名或密码错误"),
    USER_NOT_EXIST(10002,"用户不存在"),
    USER_NOT_AVAILABLE(10003,"用户状态不可用"),
    USERNAME_EXIST(10004,"用户名已存在"),
    PHONE_EXIST(10005,"手机号已存在"),
    PHONE_NOT_EXIST(10006,"手机号不存在"),
    PHONE_CODE_ERROR(10007,"验证码错误"),

    // 10100~10199：账本
    ACCOUNT_BOOK_NOT_EXISTS(10100,"账本不存在"),
    ACCOUNT_BOOK_NOT_SELF_ERROR(10101,"账本不是本人数据"),

    // 10200~10299：账户类型
    ACCOUNT_TYPE_NOT_EXISTS(10200,"账户类型不存在"),
    ACCOUNT_TYPE_PARENT_NOT_EXIST(10201,"一级账户类型不存在"),
    ACCOUNT_TYPE_CHILDREN_DATA_NOT_EXISTS(10202,"二级账户类型不存在"),
    ACCOUNT_TYPE_REQUIRED_TOP_LEVEL(10203,"账户类型必须是一级数据"),
    ACCOUNT_TYPE_REQUIRED_SUB_LEVEL(10204,"账户类型必须是二级数据"),
    ACCOUNT_TYPE_NOT_SELF_ERROR(10205,"账户类型不是本人数据"),
    ACCOUNT_TYPE_REFERENCE_REQUIRED_TOP_LEVEL(10206,"引用的账户类型必须是一级数据"),
    ACCOUNT_TYPE_REFERENCE_REQUIRED_SUB_LEVEL(10207,"引用的账户类型必须是二级数据"),

    // 10300~10399：商家类型
    SHOP_TYPE_NOT_EXISTS(10300,"商家类型不存在"),
    SHOP_TYPE_NOT_SELF_ERROR(10301,"商家类型不是本人数据"),

    // 10400~10499：成员类型
    MEMBER_TYPE_NOT_EXISTS(10400,"成员类型不存在"),
    MEMBER_TYPE_NOT_SELF_ERROR(10401,"成员类型不是本人数据"),

    // 10500~10599：项目类型
    PROJECT_TYPE_NOT_EXISTS(10500,"项目类型不存在"),
    PROJECT_TYPE_NOT_SELF_ERROR(10501,"项目类型不是本人数据"),

    // 10600~10699：账单分类
    BILL_CATEGORY_NOT_EXISTS(10600,"账单分类不存在"),
    BILL_CATEGORY_PARENT_NOT_EXISTS(10601,"一级账单分类不存在"),
    BILL_CATEGORY_CHILDREN_DATA_NOT_EXISTS(10602,"二级账单分类不存在"),
    BILL_CATEGORY_REQUIRED_TOP_LEVEL(10603,"账单分类必须是一级数据"),
    BILL_CATEGORY_REQUIRED_SUB_LEVEL(10604,"账单分类必须是二级数据"),
    BILL_CATEGORY_NOT_SELF_ERROR(10605,"账单分类数是本人数据"),
    BILL_CATEGORY_REFERENCE_REQUIRED_TOP_LEVEL(10606,"引用的账单分类必须是一级数据"),
    BILL_CATEGORY_REFERENCE_REQUIRED_SUB_LEVEL(10607,"引用的账单分类必须是二级数据"),

    // 10700~10799：账单

    // 10800~10899：附件
    ATTACHMENT_LIST_BIND_EMPTY_ERROR(10800,"绑定附件列表不能为空"),
    ATTACHMENT_ONE_BILL_EXCEED_MAX_SIZE_ERROR(10801, "单个账单附件数量超出最大数量"),

    // 10900~10999：模版
    ;

    @JsonValue
    private final Integer code;
    private final String message;
    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static ResultCode fromCode(Object code) {
        if (code == null) {
            return null;
        }

        try {
            int intValue = Integer.parseInt(code.toString());
            return Arrays.stream(ResultCode.values())
                    .filter(type -> type.code.equals(intValue))
                    .findFirst()
                    // .orElse(null); // 或者抛出异常
                    .orElseThrow(() -> new IllegalArgumentException("无效的值: " + intValue));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
