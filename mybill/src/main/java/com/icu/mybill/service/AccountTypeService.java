package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.pojo.AccountType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.query.BasePageQuery;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_type(账户类型表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface AccountTypeService extends IService<AccountType> {

    Page<AccountType> pageQuery(BasePageQuery basePageQuery);

    boolean updateSort(List<UpdateAccountTypeSortDTO> list);

    boolean saveAccountType(AccountType accountType);

    boolean updateData(UpdateAccountTypeDTO updateAccountTypeDTO);

    boolean deleteById(Long accountTypeId);
}
