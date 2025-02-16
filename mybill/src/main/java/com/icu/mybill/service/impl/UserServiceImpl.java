package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.user.LoginDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.enums.UserStatus;
import com.icu.mybill.exception.user.LoginException;
import com.icu.mybill.mapper.UserMapper;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.Argon2Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
* @author zhangjianzhong
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User login(LoginDTO loginDTO) throws LoginException {
        User user = this.lambdaQuery()
                .eq(User::getUsername, loginDTO.getUsername())
                // .eq(User::getStatus, UserStatus.NORMAL)
                .one();

        // 未查询到用户
        if (user == null){
            throw new LoginException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 密码校验
        if (!Argon2Helper.checkPassword(user.getPassword(), loginDTO.getPassword())) {
            throw new LoginException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 用户状态为非正常
        if (user.getStatus() != UserStatus.NORMAL){
            log.info("用户登录失败，用户状态为：[{}]-[{}]", user.getUsername(), user.getStatus());
            throw new LoginException(ResultCode.USER_NOT_AVAILABLE);
        }

        // 更新：登录信息和最后更新时间
        boolean updated = this.lambdaUpdate().eq(User::getId, user.getId())
                .set(User::getLastLoginIp, loginDTO.getLastLoginIp())
                .set(User::getLastLoginDevice, loginDTO.getDevice())
                .set(User::getLastLoginTime, LocalDateTime.now())

                // 最后更新时间在mysql中设置了在update时自动更新，所以不需要设置
                // .set(User::getLastUpdateTime, LocalDateTime.now())
                .update();

        if (updated){
            user.setLastLoginIp(loginDTO.getLastLoginIp());
            user.setLastLoginDevice(loginDTO.getDevice());
            user.setLastLoginTime(LocalDateTime.now());

            // 查询最新用户信息，也可以不查询，主要是更新了最后更新时间
            // user = this.lambdaQuery().eq(User::getId, user.getId()).one();
            log.info("用户登录成功，并更新了登录信息：{}", user);
        }

        return user;
    }
}




