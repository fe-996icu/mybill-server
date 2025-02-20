package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.util.TokenHelper;
import com.icu.mybill.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("getUserInfo")
    public Result<UserVO> getUserInfo() {
        TokenUserDTO tokenUserDTO = ThreadLocalHelper.get();
        User user = userService.getById(tokenUserDTO.getId());

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.ok(userVO);
    }
}
