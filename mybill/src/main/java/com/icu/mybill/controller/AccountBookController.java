package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.accountbook.CreateAccountBookDTO;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.util.ThreadLocalHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("accountbook")
public class AccountBookController {
    private static final String TAG = "AccountBookController";

    @Autowired
    private AccountBookService accountBookService;

    /**
     * 创建账本
     *
     * @param createAccountBookDTO
     * @return
     */
    @PostMapping("create")
    public Result<AccountBook> create(@RequestBody CreateAccountBookDTO createAccountBookDTO) {
        AccountBook accountBook = BeanUtil.copyProperties(createAccountBookDTO, AccountBook.class);
        accountBook.setUserId(ThreadLocalHelper.get().getId());

        accountBookService.save(accountBook);

        return Result.ok(accountBook);
    }
}
