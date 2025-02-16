package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.user.LoginDTO;
import com.icu.mybill.vo.user.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 登录
     * @Validated用于校验参数
     * @param loginDTO
     * @return
     */
    @PostMapping("login")
    public Result<LoginVO> login(@RequestBody @Validated LoginDTO loginDTO) {
        log.info("用户登录：{}", loginDTO);
        LoginVO loginVO = BeanUtil.copyProperties(loginDTO, LoginVO.class);
        return Result.ok(loginVO);
    }
}
