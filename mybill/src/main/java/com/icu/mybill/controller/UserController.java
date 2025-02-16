package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.LoginDTO;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.CommonUtil;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.util.TokenHelper;
import com.icu.mybill.vo.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenHelper tokenHelper;

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     * @Validated用于校验参数
     */
    @PostMapping("login")
    public ResponseEntity<Result<UserVO>> login(@RequestBody @Validated LoginDTO loginDTO, HttpServletRequest request) {
        // 获取用户登录所在ip
        String realIp = CommonUtil.getRealIp(request);
        loginDTO.setLastLoginIp(realIp);

        log.info("用户登录：{}", loginDTO);

        User user = userService.login(loginDTO);

        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(user.getId());
        tokenUserDTO.setUsername(user.getUsername());
        tokenUserDTO.setLastLoginTime(user.getLastLoginTime());
        String token = tokenHelper.generateToken(tokenUserDTO);

        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 将token信息存入响应头中，并返回用户vo对象
        return ResponseEntity.ok().header("token", token).body(Result.ok(userVO));
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
