package com.icu.mybill.mapper;

import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.pojo.AccountType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_type(账户类型表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.AccountType
*/
public interface AccountTypeMapper extends BaseMapper<AccountType> {

    boolean updateSort(@Param("userId") long userId, @Param("list") List<UpdateSortDTO> list);

}




