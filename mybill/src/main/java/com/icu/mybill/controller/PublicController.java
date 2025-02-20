package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.LoginByPhoneDTO;
import com.icu.mybill.dto.user.LoginByUsernameDTO;
import com.icu.mybill.dto.user.RegisterUserByPhoneDTO;
import com.icu.mybill.dto.user.RegisterUserByUsernameDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.CommonUtil;
import com.icu.mybill.util.TokenHelper;
import com.icu.mybill.vo.user.UserVO;
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
    @PostMapping("register/username")
    Result<UserVO> registerUsername(@RequestBody @Validated RegisterUserByUsernameDTO registerUserDTO) {
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
    Result<UserVO> registerPhone(@RequestBody @Validated RegisterUserByPhoneDTO registerUserDTO) {
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
    public ResponseEntity<Result<UserVO>> loginUsername(@RequestBody @Validated LoginByUsernameDTO loginByUsernameDTO, HttpServletRequest request) {
        // 获取用户登录所在ip
        String realIp = CommonUtil.getRealIp(request);
        loginByUsernameDTO.setLastLoginIp(realIp);

        log.info("用户登录 /login/username：{}", loginByUsernameDTO);

        User user = userService.login(loginByUsernameDTO);

        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(user.getId());
        tokenUserDTO.setUsername(user.getUsername());
        tokenUserDTO.setLastLoginTime(user.getLastLoginTime());
        String token = tokenHelper.generateToken(tokenUserDTO);

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 将token信息存入响应头中，并返回用户vo对象
        return ResponseEntity.ok().header("token", token).body(Result.ok(userVO));
    }

    @PostMapping("login/phone")
    public ResponseEntity<Result<UserVO>> loginPhone(@RequestBody @Validated LoginByPhoneDTO loginByPhoneDTO, HttpServletRequest request) {
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

        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(user.getId());
        tokenUserDTO.setUsername(user.getUsername());
        tokenUserDTO.setLastLoginTime(user.getLastLoginTime());
        String token = tokenHelper.generateToken(tokenUserDTO);

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 将token信息存入响应头中，并返回用户vo对象
        return ResponseEntity.ok().header("token", token).body(Result.ok(userVO));
    }

}
