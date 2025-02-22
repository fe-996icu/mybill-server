package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.accountbook.CreateAccountBookDTO;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.accountbook.AccountBookVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("accountbook")
// 接口分组名称和分组描述
@Tag(name = "账本", description = "所有和账本相关的接口")
public class AccountBookController {
    @Autowired
    private AccountBookService accountBookService;

    /**
     * 创建账本
     *
     * @param createAccountBookDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建账本", description = "创建账本，接收一个账本对象")
    public Result<AccountBookVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的账本对象", required = true)
            @RequestBody
            CreateAccountBookDTO createAccountBookDTO
    ) {
        AccountBook accountBook = BeanUtil.copyProperties(createAccountBookDTO, AccountBook.class);
        accountBook.setUserId(ThreadLocalHelper.get().getId());

        accountBookService.save(accountBook);

        return Result.ok(BeanUtil.copyProperties(accountBook, AccountBookVO.class));
    }
}
