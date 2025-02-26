package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.PageDTO;
import com.icu.mybill.dto.accounttype.CreateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.AccountType;
import com.icu.mybill.query.BasePageQuery;
import com.icu.mybill.service.AccountTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.accounttype.AccountTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("accounttype")
// 接口分组名称和分组描述
@Tag(name = "账户类型", description = "所有和账户类型相关的接口")
public class AccountTypeController {
    @Autowired
    private AccountTypeService accountTypeService;

    /**
     * 创建账户类型
     *
     * @param createAccountTypeDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建账户类型", description = "创建账户类型，接收一个账户类型对象")
    public Result<AccountTypeVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的账户类型对象", required = true)
            @RequestBody @Validated CreateAccountTypeDTO createAccountTypeDTO
    ) {
        AccountType AccountType = BeanUtil.copyProperties(createAccountTypeDTO, AccountType.class);

        accountTypeService.saveAccountType(AccountType);

        return Result.ok(BeanUtil.copyProperties(AccountType, AccountTypeVO.class));
    }

    /**
     * 获取账户类型全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取账户类型全部列表", description = "获取账户类型全部列表")
    public Result<List<AccountTypeVO>> list() {
        LambdaQueryWrapper<AccountType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountType::getUserId, ThreadLocalHelper.get().getId());
        queryWrapper.orderByDesc(AccountType::getSort, AccountType::getCreateTime);

        List<AccountType> list = this.accountTypeService.list(queryWrapper);
        List<AccountTypeVO> AccountTypeVOS = BeanUtil.copyToList(list, AccountTypeVO.class);

        return Result.ok(AccountTypeVOS);
    }

    /**
     * 分页获取账户类型列表
     *
     * @param basePageQuery
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页获取账户类型列表", description = "分页获取账户类型列表")
    public Result<PageDTO<AccountTypeVO>> page(
            @Parameter(description = "分页查询参数")
            @ModelAttribute() BasePageQuery basePageQuery
    ) {
        Page<AccountType> page = accountTypeService.pageQuery(basePageQuery);
        PageDTO<AccountTypeVO> dto = PageDTO.of(page, AccountTypeVO.class);

        return Result.ok(dto);
    }

    /**
     * 获取账户类型详情
     *
     * @param id
     * @return
     */
    @GetMapping("detail")
    @Operation(summary = "获取账户类型详情", description = "获取账户类型详情")
    public Result<AccountTypeVO> getById(
            @Parameter(description = "账户类型id", required = true) @RequestParam(required = true) Long id
    ) {
        AccountType accountType = accountTypeService.getOne(
                Wrappers.lambdaQuery(AccountType.class)
                        .eq(AccountType::getId, id)
                        .eq(AccountType::getUserId, ThreadLocalHelper.get().getId())
        );
        AccountTypeVO AccountTypeVO = BeanUtil.copyProperties(accountType, AccountTypeVO.class);

        return Result.ok(AccountTypeVO);
    }

    /**
     * 更新账户类型
     *
     * @param updateAccountTypeDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新账户类型", description = "更新账户类型")
    public Result<Boolean> update(
            @Parameter(description = "要更新的账户类型信息", required = true) @RequestBody @Validated UpdateAccountTypeDTO updateAccountTypeDTO
    ) {
        // 账户类型名称和账户类型图标不能都为空
        if (StringUtils.isAllEmpty(updateAccountTypeDTO.getName(), updateAccountTypeDTO.getIcon())){
            return Result.fail(ResultCode.PARAMETER_FAIL.getCode(), "账户类型名称和账户类型图标不能都为空");
        }

        Long userId = ThreadLocalHelper.get().getId();

        LambdaUpdateWrapper<AccountType> updateWrapper = Wrappers.lambdaUpdate(AccountType.class)
                .eq(AccountType::getId, updateAccountTypeDTO.getId())
                .eq(AccountType::getUserId, userId)
                .set(StringUtils.isNotBlank(updateAccountTypeDTO.getName()), AccountType::getName, updateAccountTypeDTO.getName())
                .set(StringUtils.isNotBlank(updateAccountTypeDTO.getIcon()), AccountType::getIcon, updateAccountTypeDTO.getIcon());

        boolean result = accountTypeService.update(updateWrapper);
        return Result.ok(result);
    }

    /**
     * 删除账户类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除账户类型", description = "删除账户类型")
    public Result<Boolean> delete(
            @Parameter(description = "账户类型id", required = true) @RequestParam Long id
    ) {
        boolean result = accountTypeService.remove(
                Wrappers.lambdaQuery(AccountType.class)
                        .eq(AccountType::getId, id)
                        .eq(AccountType::getUserId, ThreadLocalHelper.get().getId())
        );
        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新账户类型排序", description = "更新账户类型排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新账户类型排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateAccountTypeSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.PARAMETER_FAIL.getCode(), "数据列表不能为空");
        }

        Boolean result = accountTypeService.updateSort(list);
        return Result.ok(result);
    }
}
