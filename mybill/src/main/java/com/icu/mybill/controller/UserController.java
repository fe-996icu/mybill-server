package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.UpdateUserDTO;
import com.icu.mybill.enums.UserStatus;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 冻结用户
     *
     * @return
     */
    @PostMapping("freeze")
    public Result<UserVO> freeze() {
        TokenUserDTO tokenUserDTO = ThreadLocalHelper.get();
        User user = userService.getById(tokenUserDTO.getId());

        user.setStatus(UserStatus.LOCKED);
        userService.updateById(user);

        return Result.ok(null);
    }

    /**
     * 更新用户信息
     *
     * @return
     */
    @PostMapping("update")
    public Result<?> update(@RequestBody UpdateUserDTO updateUserDTO) {
        User user = BeanUtil.copyProperties(updateUserDTO, User.class);
        user.setId(ThreadLocalHelper.get().getId());

        boolean result = userService.updateById(user);
        return Result.ok(result);
    }

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
