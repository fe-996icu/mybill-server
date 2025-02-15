package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




