package com.icu.mybill.service;

import com.icu.mybill.dto.user.LoginByPhoneDTO;
import com.icu.mybill.dto.user.LoginByUsernameDTO;
import com.icu.mybill.dto.user.RegisterUserByPhoneDTO;
import com.icu.mybill.dto.user.RegisterUserByUsernameDTO;
import com.icu.mybill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangjianzhong
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface UserService extends IService<User> {

    User login(LoginByUsernameDTO loginByUsernameDTO);

    User registerByUsername(RegisterUserByUsernameDTO registerUserDTO);

    User registerByPhone(RegisterUserByPhoneDTO registerUserDTO);

    User loginByPhone(LoginByPhoneDTO loginByPhoneDTO);
}
