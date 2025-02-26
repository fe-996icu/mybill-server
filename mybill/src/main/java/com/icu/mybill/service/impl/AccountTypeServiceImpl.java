package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.pojo.AccountType;
import com.icu.mybill.query.BasePageQuery;
import com.icu.mybill.service.AccountTypeService;
import com.icu.mybill.mapper.AccountTypeMapper;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_type(账户类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AccountTypeServiceImpl extends ServiceImpl<AccountTypeMapper, AccountType>
    implements AccountTypeService{

    @Override
    public Page<AccountType> pageQuery(BasePageQuery basePageQuery) {
        return null;
    }

    @Override
    public boolean updateSort(List<UpdateAccountTypeSortDTO> list) {
        return true;
    }

    @Override
    public boolean saveAccountType(AccountType accountType) {
        Long userId = ThreadLocalHelper.get().getId();
        accountType.setUserId(userId);

        // parentId是null，直接保存（一级账户类型）
        Long parentId = accountType.getParentId();
        if (parentId == null) {
            return this.save(accountType);
        }


        // 查询父级账户类型
        AccountType parentAccountType = this.lambdaQuery()
                .eq(AccountType::getId, parentId)
                .eq(AccountType::getUserId, userId)
                .one();

        // 没有查询到父账户类型
        if (parentAccountType == null) {
            throw new FrontendErrorPromptException(ResultCode.PARENT_ACCOUNT_TYPE_NOT_EXIST);
        }

        // 父级账户类型必须是顶级
        if (parentAccountType.getParentId() != null){
            throw new FrontendErrorPromptException(ResultCode.PARENT_ACCOUNT_TYPE_REQUIRE_TOP_LEVEL);
        }

        return this.save(accountType);
    }

    @Override
    public boolean updateData(UpdateAccountTypeDTO updateAccountTypeDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        AccountType accountType = this.getById(updateAccountTypeDTO.getId());

        if(accountType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!accountType.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        AccountType updateAccountType = BeanUtil.copyProperties(updateAccountTypeDTO, AccountType.class);

        return this.updateById(updateAccountType);
    }

    @Override
    public boolean deleteById(Long accountTypeId) {
        AccountType accountType = this.getById(accountTypeId);

        if (accountType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!accountType.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(accountTypeId);
    }
}




