package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.LoginByPhoneDTO;
import com.icu.mybill.dto.user.LoginByUsernameDTO;
import com.icu.mybill.dto.user.RegisterUserByPhoneDTO;
import com.icu.mybill.dto.user.RegisterUserByUsernameDTO;
import com.icu.mybill.enums.LoginType;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.CommonUtil;
import com.icu.mybill.util.TokenHelper;
import com.icu.mybill.vo.user.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("public")
// @Tag(name = "公共接口", description = "所有和公共相关的接口，不需要鉴权")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenHelper tokenHelper;

    @Value("${com.icu.mybill.defaultPhoneCode}")
    private String DEFAULT_PHONE_CODE;

    /**
     * 注册
     *
     * @param registerUserDTO
     * @return
     */
    @Operation(summary = "通过账号注册用户", description = "通过账号注册用户")
    @PostMapping("register/username")
    Result<UserVO> registerUsername(
            @Parameter(description = "注册用户信息", required = true) @RequestBody @Validated RegisterUserByUsernameDTO registerUserDTO
    ) {
        // 检查用户名是否存在
        boolean exists = userService.lambdaQuery().eq(User::getUsername, registerUserDTO.getUsername()).exists();
        if(exists){
            return Result.fail(ResultCode.USERNAME_EXIST);
        }

        User user = userService.registerByUsername(registerUserDTO);
        return Result.ok(BeanUtil.copyProperties(user, UserVO.class));
    }

    /**
     * 注册
     *
     * @param registerUserDTO
     * @return
     */
    @PostMapping("register/phone")
    @Operation(summary = "通过手机号注册用户", description = "通过手机号注册用户")
    Result<UserVO> registerPhone(
            @Parameter(description = "通过手机号注册用户信息", required = true) @RequestBody @Validated RegisterUserByPhoneDTO registerUserDTO
    ) {
        // 检查手机号是否存在
        boolean exists = userService.lambdaQuery().eq(User::getPhone, registerUserDTO.getPhone()).exists();
        if(exists){
            return Result.fail(ResultCode.PHONE_EXIST);
        }

        // 检查验证码
        if(!DEFAULT_PHONE_CODE.equals(registerUserDTO.getCode())){
            return Result.fail(ResultCode.PHONE_CODE_ERROR);
        }

        User user = userService.registerByPhone(registerUserDTO);
        return Result.ok(BeanUtil.copyProperties(user, UserVO.class));
    }
    /**
     * 登录
     *
     * @param loginByUsernameDTO
     * @return
     * @Validated用于校验参数
     */
    @PostMapping("login/username")
    @Operation(summary = "通过账号密码登录", description = "通过账号密码登录")
    public ResponseEntity<Result<UserVO>> loginUsername(
            @Parameter(description = "账号密码登录信息", required = true)
            @RequestBody @Validated LoginByUsernameDTO loginByUsernameDTO, HttpServletRequest request
    ) {
        // 获取用户登录所在ip
        String realIp = CommonUtil.getRealIp(request);
        loginByUsernameDTO.setLastLoginIp(realIp);

        log.info("用户登录 /login/username：{}", loginByUsernameDTO);

        User user = userService.login(loginByUsernameDTO);
        return this.getLoginResult(user, LoginType.USERNAME);
    }

    @PostMapping("login/phone")
    @Operation(summary = "通过手机号登录", description = "通过手机号登录")
    public ResponseEntity<Result<UserVO>> loginPhone(
            @Parameter(description = "手机号登录信息", required = true) @RequestBody @Validated LoginByPhoneDTO loginByPhoneDTO, HttpServletRequest request
    ) {
        // 检查验证码
        if(!DEFAULT_PHONE_CODE.equals(loginByPhoneDTO.getCode())){
            // 返回错误信息
            return ResponseEntity.ok().body(Result.fail(ResultCode.PHONE_CODE_ERROR));
        }

        // 获取用户登录所在ip
        String realIp = CommonUtil.getRealIp(request);
        loginByPhoneDTO.setLastLoginIp(realIp);

        log.info("用户登录 /login/phone：{}", loginByPhoneDTO);

        User user = userService.loginByPhone(loginByPhoneDTO);
        return this.getLoginResult(user, LoginType.PHONE);
    }

    private ResponseEntity<Result<UserVO>> getLoginResult(User user, LoginType loginType){
        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(user.getId());
        tokenUserDTO.setUsername(user.getUsername());
        tokenUserDTO.setPhone(user.getPhone());
        tokenUserDTO.setLoginType(loginType);
        tokenUserDTO.setLastLoginTime(user.getLastLoginTime());
        String token = tokenHelper.generateToken(tokenUserDTO);

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 将token信息存入响应头中，并返回用户vo对象
        return ResponseEntity.ok().header("token", token).body(Result.ok(userVO));
    }
}
