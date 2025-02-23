package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.PageDTO;
import com.icu.mybill.dto.accountbook.CreateAccountBookDTO;
import com.icu.mybill.dto.accountbook.UpdateAccountBookDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.query.BasePageQuery;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.accountbook.AccountBookVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取账本全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取账本全部列表", description = "获取账本全部列表")
    public Result<List<AccountBookVO>> list() {
        LambdaQueryWrapper<AccountBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountBook::getUserId, ThreadLocalHelper.get().getId());
        queryWrapper.orderByDesc(AccountBook::getSort, AccountBook::getCreateTime);

        List<AccountBook> list = this.accountBookService.list(queryWrapper);
        List<AccountBookVO> accountBookVOS = BeanUtil.copyToList(list, AccountBookVO.class);

        return Result.ok(accountBookVOS);
    }

    /**
     * 分页获取账本列表
     *
     * @param basePageQuery
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页获取账本列表", description = "分页获取账本列表")
    public Result<PageDTO<AccountBookVO>> page(
            @Parameter(description = "分页查询参数")
            @ModelAttribute() BasePageQuery basePageQuery
    ) {
        Page<AccountBook> page = accountBookService.pageQuery(basePageQuery);
        PageDTO<AccountBookVO> dto = PageDTO.of(page, AccountBookVO.class);

        return Result.ok(dto);
    }

    /**
     * 获取账本详情
     *
     * @param id
     * @return
     */
    @GetMapping("detail")
    @Operation(summary = "获取账本详情", description = "获取账本详情")
    public Result<AccountBookVO> getById(
            @Parameter(description = "账本id", required = true) @RequestParam(required = true) Long id
    ) {
        AccountBook accountBook = accountBookService.getById(id);
        AccountBookVO accountBookVO = BeanUtil.copyProperties(accountBook, AccountBookVO.class);

        return Result.ok(accountBookVO);
    }

    /**
     * 更新账本
     *
     * @param updateAccountBookDTO
     * @return
     */
    @PostMapping("update")
    @Operation(summary = "更新账本", description = "更新账本")
    public Result<Boolean> update(
            @Parameter(description = "要更新的账本信息", required = true) @RequestBody @Validated UpdateAccountBookDTO updateAccountBookDTO
    ) {
        if (StringUtils.isBlank(updateAccountBookDTO.getName()) && StringUtils.isBlank(updateAccountBookDTO.getIcon())){
            return Result.fail(ResultCode.PARAMETER_FAIL.getCode(), "账本名称和账本图标不能都为空");
        }

        AccountBook accountBook = BeanUtil.copyProperties(updateAccountBookDTO, AccountBook.class);
        return Result.ok(accountBookService.updateById(accountBook));
    }
}
