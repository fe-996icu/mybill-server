package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.accounttype.CreateChildrenAccountTypeDTO;
import com.icu.mybill.dto.accounttype.CreateParentAndChildrenAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.pojo.AccountType;
import com.icu.mybill.service.AccountTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.util.TreeDataHelper;
import com.icu.mybill.vo.accounttype.AccountTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
     * 创建账户类型（一级和二级）
     *
     * @param createParentAndChildrenAccountTypeDTO
     * @return
     */
    @PostMapping("createParentAndChildren")
    // 接口名和接口描述
    @Operation(summary = "创建账户类型（一级和二级）", description = "创建账户类型（一级和二级）")
    public Result<AccountTypeVO> createParentAndChildren(
            // 接口参数描述
            @Parameter(description = "要创建的账户类型对象，需要包含children属性", required = true)
            @RequestBody @Validated CreateParentAndChildrenAccountTypeDTO createParentAndChildrenAccountTypeDTO
    ) {
        AccountType parent = BeanUtil.copyProperties(createParentAndChildrenAccountTypeDTO, AccountType.class);
        List<AccountType> children = BeanUtil.copyToList(createParentAndChildrenAccountTypeDTO.getChildren(), AccountType.class);

        accountTypeService.saveParentAndChildren(parent, children);

        AccountTypeVO parentVo = BeanUtil.copyProperties(parent, AccountTypeVO.class);
        parentVo.setChildren(BeanUtil.copyToList(children, AccountTypeVO.class));

        return Result.ok(BeanUtil.copyProperties(parentVo, AccountTypeVO.class));
    }

    /**
     * 创建二级账户类型
     *
     * @param createChildrenAccountTypeDTO
     * @return
     */
    @PostMapping("createChildren")
    // 接口名和接口描述
    @Operation(summary = "创建二级账户类型", description = "创建二级账户类型，接收一个账户类型对象，必须包含parentId")
    public Result<AccountTypeVO> createChildren(
            // 接口参数描述
            @Parameter(description = "要创建的二级账户类型对象", required = true)
            @RequestBody @Validated CreateChildrenAccountTypeDTO createChildrenAccountTypeDTO
    ) {
        AccountType accountType = BeanUtil.copyProperties(createChildrenAccountTypeDTO, AccountType.class);
        accountTypeService.saveChildren(accountType);
        return Result.ok(BeanUtil.copyProperties(accountType, AccountTypeVO.class));
    }

    /**
     * 获取账户类型全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取账户类型全部列表", description = "获取账户类型全部列表")
    public Result<List<AccountTypeVO>> list() {
        List<AccountType> list = this.accountTypeService.list(
                Wrappers.lambdaQuery(AccountType.class)
                        .eq(AccountType::getUserId, ThreadLocalHelper.get().getId())
        );

        // 生成树形结构
        List<Tree<Long>> treeList = TreeDataHelper.beanListToTreeList(list);
        // 转一下，方便明确返回值类型，swagger自动识别VO，否则生成的文档字段是Tree的不直观
        List<AccountTypeVO> accountTypeVOS = BeanUtil.copyToList(treeList, AccountTypeVO.class);

        return Result.ok(accountTypeVOS);
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
            throw new FrontendErrorPromptException(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        }

        boolean result = accountTypeService.updateData(updateAccountTypeDTO);

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
            @Parameter(description = "账户类型id", required = true) @RequestParam("id") Long id
    ) {
        boolean result = accountTypeService.deleteById(id);
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
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = accountTypeService.updateSort(list);
        return Result.ok(result);
    }
}
