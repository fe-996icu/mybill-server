package com.icu.mybill.service;

import com.icu.mybill.dto.accounttype.UpdateAccountTypeDTO;
import com.icu.mybill.dto.accounttype.UpdateAccountTypeSortDTO;
import com.icu.mybill.pojo.AccountType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_type(账户类型表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface AccountTypeService extends IService<AccountType> {

    boolean updateSort(List<UpdateAccountTypeSortDTO> list);

    boolean saveParentAndChildren(AccountType parent, List<AccountType> children);

    boolean saveChildren(AccountType children);

    boolean updateData(UpdateAccountTypeDTO updateAccountTypeDTO);

    boolean deleteById(long accountTypeId);
}
