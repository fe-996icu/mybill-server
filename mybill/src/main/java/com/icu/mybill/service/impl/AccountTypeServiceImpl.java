package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.AccountTypeMapper;
import com.icu.mybill.pojo.AccountType;
import com.icu.mybill.service.AccountTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_type(账户类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AccountTypeServiceImpl extends ServiceImpl<AccountTypeMapper, AccountType>
    implements AccountTypeService{

    @Autowired
    private AccountTypeMapper accountTypeMapper;

    @Override
    public boolean updateSort(List<UpdateAccountTypeSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<AccountType> queryList = this.lambdaQuery()
                // .eq(AccountType::getUserId, userId)
                .in(AccountType::getId, list.stream().map(UpdateAccountTypeSortDTO::getId).toList())
                .list();

        // 查询到的数据量与查询的id数量不一致，说明有id不存在，抛出异常
        if (list.size() != queryList.size()){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        // 遍历查询到的数据，判断用户id是否与当前用户id一致，不一致抛出异常
        queryList.forEach(item -> {
            if (!item.getUserId().equals(userId)) {
                throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
            }
        });

        return this.accountTypeMapper.updateSort(userId, list);
    }

    @Transactional
    @Override
    public boolean saveParentAndChildren(AccountType parent, List<AccountType> children) {
        Long userId = ThreadLocalHelper.get().getId();

        // 保存父级账户类型
        parent.setUserId(userId);
        parent.setParentId(null);
        this.save(parent);

        for (int i = 0; i < children.size(); i++) {
            AccountType item = children.get(i);
            item.setParentId(parent.getId());
            item.setUserId(userId);
            item.setSort(i + 1);
        }

        return this.saveBatch(children);
    }

    @Override
    public boolean saveChildren(AccountType accountType) {
        Long userId = ThreadLocalHelper.get().getId();

        // 没有查询到父账户类型
        if (accountType.getParentId() == null) {
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_NOT_EXIST);
        }

        // 查询父级账户类型
        AccountType parent = this.lambdaQuery()
                .eq(AccountType::getId, accountType.getParentId())
                .one();

        // 没有查询到父账户类型
        if (parent == null) {
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_NOT_EXIST);
        }

        // 操作的不是自己的数据
        if (!parent.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.REFERENCE_PARENT_DATA_NOT_SELF_ERROR);
        }

        // 父级账户类型必须是顶级
        if (parent.getParentId() != null){
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_REQUIRE_TOP_LEVEL);
        }

        accountType.setUserId(userId);

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
    public boolean deleteById(long id) {
        AccountType accountType = this.getById(id);

        if (accountType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!accountType.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




