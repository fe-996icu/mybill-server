package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.PhoneCodeDTO;
import com.icu.mybill.dto.user.UpdateUserDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.enums.UserStatus;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.user.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
@Tag(name = "用户", description = "所有和用户相关的接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${com.icu.mybill.defaultPhoneCode}")
    private String DEFAULT_PHONE_CODE;

    /**
     * 冻结用户
     *
     * @return
     */
    @PostMapping("freeze")
    @Operation(summary = "冻结当前用户", description = "冻结当前用户")
    public Result<Boolean> freeze(
            @Parameter(description = "短信验证码", required = true) @RequestBody PhoneCodeDTO phoneCodeDTO
    ) {
        if(!DEFAULT_PHONE_CODE.equals(phoneCodeDTO.getCode())){
            return Result.fail(ResultCode.PHONE_CODE_ERROR);
        }

        User user = new User();
        user.setId(ThreadLocalHelper.get().getId());
        user.setStatus(UserStatus.LOCKED);
        boolean result = userService.updateById(user);

        return Result.ok(result);
    }

    /**
     * 更新用户信息
     *
     * @return
     */
    @PostMapping("update")
    @Operation(summary = "更新当前用户信息", description = "更新当前用户信息")
    public Result<Boolean> update(
            @Parameter(description = "要更新的用户信息", required = true) @RequestBody UpdateUserDTO updateUserDTO
    ) {
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
    @Operation(summary = "获取当前用户信息", description = "获取当前用户信息")
    public Result<UserVO> getUserInfo() {
        TokenUserDTO tokenUserDTO = ThreadLocalHelper.get();
        User user = userService.getById(tokenUserDTO.getId());

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.ok(userVO);
    }
}
