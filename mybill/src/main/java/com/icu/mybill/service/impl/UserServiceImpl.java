package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icu.mybill.dto.user.LoginByPhoneDTO;
import com.icu.mybill.dto.user.LoginByUsernameDTO;
import com.icu.mybill.dto.user.RegisterUserByPhoneDTO;
import com.icu.mybill.dto.user.RegisterUserByUsernameDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.enums.UserStatus;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.*;
import com.icu.mybill.pojo.*;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.Argon2Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private AccountBookMapper accountBookMapper;
    @Autowired
    private MemberTypeMapper memberTypeMapper;
    @Autowired
    private ProjectTypeMapper projectTypeMapper;
    @Autowired
    private ShopTypeMapper shopTypeMapper;
    @Autowired
    private BillCategoryMapper billCategoryMapper;
    @Autowired
    private AccountTypeMapper accountTypeMapper;

    @Override
    public User login(LoginByUsernameDTO loginDTO) throws FrontendErrorPromptException {
        User user = this.lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                .one();

        // 未查询到用户
        if (user == null){
            throw new FrontendErrorPromptException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 密码校验
        if (!Argon2Helper.checkPassword(user.getPassword(), loginDTO.getPassword())) {
            throw new FrontendErrorPromptException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        this.checkUserStatusAndUpdateLoginInfo(loginDTO.getLastLoginIp(), loginDTO.getDevice(), user);

        return user;
    }

    @Override
    public User loginByPhone(LoginByPhoneDTO loginByPhoneDTO) {
        User user = this.lambdaQuery()
                .eq(User::getPhone, loginByPhoneDTO.getPhone())
                .one();

        // 未查询到用户
        if (user == null){
            throw new FrontendErrorPromptException(ResultCode.USER_NOT_EXIST);
        }

        this.checkUserStatusAndUpdateLoginInfo(loginByPhoneDTO.getLastLoginIp(), loginByPhoneDTO.getDevice(), user);

        return user;
    }

    private boolean checkUserStatusAndUpdateLoginInfo(String lastLoginIp, String device, User user) {

        // 用户状态为非正常
        if (user.getStatus() != UserStatus.NORMAL){
            log.info("用户登录失败，用户状态为：[username={}]-[phone={}]-[status={}]", user.getUsername(), user.getPhone(), user.getStatus());
            throw new FrontendErrorPromptException(ResultCode.USER_NOT_AVAILABLE);
        }

        // 更新：登录信息和最后更新时间
        boolean updated = this.lambdaUpdate().eq(User::getId, user.getId())
                .set(User::getLastLoginIp, lastLoginIp)
                .set(User::getLastLoginDevice, device)
                .set(User::getLastLoginTime, LocalDateTime.now())

                // 最后更新时间在mysql中设置了在update时自动更新，所以不需要设置
                // .set(User::getLastUpdateTime, LocalDateTime.now())
                .update();

        if (updated){
            user.setLastLoginIp(lastLoginIp);
            user.setLastLoginDevice(device);
            user.setLastLoginTime(LocalDateTime.now());

            // 查询最新用户信息，也可以不查询，主要是更新了最后更新时间
            // user = this.lambdaQuery().eq(User::getId, user.getId()).one();
            log.info("用户登录成功，并更新了登录信息：{}", user);
        }

        return updated;
    }

    @Override
    @Transactional
    public User registerByUsername(RegisterUserByUsernameDTO registerUserDTO) {
        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(Argon2Helper.hashPassword(registerUserDTO.getPassword()));
        user.setNickname(registerUserDTO.getUsername());
        // 新增用户
        this.save(user);

        // 初始化用户数据
        this.initUserData(user.getId());
        // 读取并解析 resources/config/initUserData.json 文件


/*

        // 创建默认账本
        AccountBook accountBook = new AccountBook();
        accountBook.setName("默认账本");
        accountBook.setIcon(null);
        accountBook.setType(AccountBookType.DEFAULT);
        accountBook.setUserId(user.getId());
        accountBook.setSort(1);
        this.accountBookMapper.insert(accountBook);

        // 默认成员类型
        String[] memberTypeNames = {"管理员", "成员"};
        List<MemberType> memberTypes = new ArrayList<>();
        for (int i = 0; i < memberTypeNames.length; i++) {
            MemberType memberType = new MemberType();
            memberType.setName(memberTypeNames[i]);
            memberType.setIcon(null);
            memberType.setSort(i+1);
            memberType.setUserId(user.getId());
            memberTypes.add(memberType);
        }
        this.memberTypeMapper.insert(memberTypes);

        // 默认项目类型
        String[] projectTypeNames = {"孩子教育", "出差"};
        List<ProjectType> projectTypes = new ArrayList<>();
        for (int i = 0; i < projectTypeNames.length; i++) {
            ProjectType memberType = new ProjectType();
            memberType.setName(projectTypeNames[i]);
            memberType.setIcon(null);
            memberType.setSort(i+1);
            memberType.setUserId(user.getId());
            projectTypes.add(memberType);
        }
        this.projectTypeMapper.insert(projectTypes);

        // 默认商家类型
        String[] shopTypeNames = {"超市", "超市", "超市"};
        List<ShopType> shopTypes = new ArrayList<>();
        for (int i = 0; i < shopTypeNames.length; i++) {
            ShopType memberType = new ShopType();
            memberType.setName(shopTypeNames[i]);
            memberType.setIcon(null);
            memberType.setSort(i+1);
            memberType.setUserId(user.getId());
            shopTypes.add(memberType);
        }
        this.shopTypeMapper.insert(shopTypes);

        // 账单分类：支出（一级）
        String[] billCategoryNames = {"餐饮", "购物", "交通"};
        List<BillCategory> billCategories = new ArrayList<>();
        for (int i = 0; i < billCategoryNames.length; i++) {
            BillCategory billCategory = new BillCategory();
            billCategory.setName(billCategoryNames[i]);
            billCategory.setType(BillType.EXPENSE);
            billCategory.setIcon(null);
            billCategory.setSort(i+1);
            billCategory.setParentId(null);
            billCategory.setUserId(user.getId());
            billCategories.add(billCategory);
        }
        // 账单分类：收入（一级）
*/




        // this.billCategoryMapper.insert(billCategories);
        // throw new RuntimeException("测试事务");

        return user;
    }

    @Override
    public User registerByPhone(RegisterUserByPhoneDTO registerUserDTO) {
        User user = new User();
        user.setPhone(registerUserDTO.getPhone());
        user.setNickname(registerUserDTO.getPhone());
        // 新增用户
        this.save(user);

        // 初始化用户数据
        this.initUserData(user.getId());

        return user;
    }

    private final ObjectMapper objectMapper = new ObjectMapper()
            // Jackson 解析时忽略未知字段（全局）
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    /**
     * 初始化用户数据
     * @param userId 用户ID
     */
    public void initUserData(Long userId) {
        try {
            // 读取并解析 resources/config/initUserData.json 文件
            ClassPathResource resource = new ClassPathResource("config/initUserData.json");
            JsonNode rootNode = objectMapper.readTree(resource.getInputStream());

            saveEntitiesWithChildren(rootNode.get("account_book"), AccountBook.class, accountBookMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("member_type"), MemberType.class, memberTypeMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("project_type"), ProjectType.class, projectTypeMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("shop_type"), ShopType.class, shopTypeMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("bill_category").get("expense"), BillCategory.class, billCategoryMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("bill_category").get("income"), BillCategory.class, billCategoryMapper, null, userId);
            saveEntitiesWithChildren(rootNode.get("account_type"), AccountType.class, accountTypeMapper, null, userId);
        } catch (IOException e) {
            throw new RuntimeException("初始化用户数据失败", e);
        }
    }

    private <T> void saveEntities(JsonNode jsonNode, Class<T> clazz, BaseMapper<T> mapper, Long userId) {
        if (jsonNode != null && jsonNode.isArray()) {
            List<T> entities = objectMapper.convertValue(jsonNode, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
            for (T entity : entities) {
                try {
                    var userIdField = clazz.getDeclaredField("userId");
                    userIdField.setAccessible(true);
                    userIdField.set(entity, userId);
                } catch (NoSuchFieldException | IllegalAccessException ignored) {
                    // 如果实体没有 userId 字段，忽略
                    throw new RuntimeException("实体没有 userId 字段");
                }

                // mapper.insert(entity);
                log.info("entity:{}", entity);
            }
        }
    }

    /**
     * 保存实体类及其子级
     * @param jsonNode
     * @param clazz
     * @param mapper
     * @param parentId
     * @param userId
     * @param <T>
     */
    private <T> void saveEntitiesWithChildren(JsonNode jsonNode, Class<T> clazz, BaseMapper<T> mapper,
                                              Long parentId, Long userId) {
        if (jsonNode != null && jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                // 解析 JSON 为实体类
                T entity = objectMapper.convertValue(node, clazz);

                // 反射设置 userId 和 parentId（如果有）
                setFieldIfPresent(entity, "userId", userId);
                setFieldIfPresent(entity, "parentId", parentId);

                // 插入数据库
                mapper.insert(entity);
                // log.info("entity11111111111111:{}", entity);

                // 反射获取 ID 作为子级的 parentId
                Long newParentId = getFieldIfPresent(entity, "id");

                // 处理 children 递归
                if (node.has("children") && node.get("children").isArray()) {
                    saveEntitiesWithChildren(node.get("children"), clazz, mapper, newParentId, userId);
                }
            }
        }
    }
    private <T> void setFieldIfPresent(T entity, String fieldName, Object value) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, value);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            // 如果字段不存在或无法访问，则忽略
        }
    }

    private <T> Long getFieldIfPresent(T entity, String fieldName) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Long) field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return null;
        }
    }

}
