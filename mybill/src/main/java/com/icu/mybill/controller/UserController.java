package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.dto.user.LoginDTO;
import com.icu.mybill.pojo.User;
import com.icu.mybill.service.UserService;
import com.icu.mybill.util.CommonUtil;
import com.icu.mybill.util.TokenHelper;
import com.icu.mybill.vo.user.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 登录
     *
     * @param loginDTO
     * @return
     * @Validated用于校验参数
     */
    @PostMapping("login")
    public ResponseEntity<Result<LoginVO>> login(@RequestBody @Validated LoginDTO loginDTO, HttpServletRequest request) {
        // 获取用户登录所在ip
        String realIp = CommonUtil.getRealIp(request);
        loginDTO.setLastLoginIp(realIp);

        log.info("用户登录：{}", loginDTO);

        User user = userService.login(loginDTO);

        LoginVO loginVO = BeanUtil.copyProperties(user, LoginVO.class);

        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(user.getId());
        tokenUserDTO.setUsername(user.getUsername());
        String token = tokenHelper.generateToken(tokenUserDTO);

        return ResponseEntity.ok().header("token", token).body(Result.ok(loginVO));
    }


    /**
     * 登录
     * @Validated用于校验参数
     * @param loginDTO
     * @return
     */
    @PostMapping("getUserInfo")
    public Result<LoginVO> getUserInfo(@RequestBody @Validated LoginDTO loginDTO) {
        log.info("用户登录：{}", loginDTO);

        // User user = userService.login(loginDTO);

        LoginVO loginVO = BeanUtil.copyProperties(loginDTO, LoginVO.class);
        return Result.ok(loginVO);
    }
}
