package com.icu.mybill.mapper;

import com.icu.mybill.dto.accountbook.UpdateAccountBookSortDTO;
import com.icu.mybill.pojo.AccountBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.AccountBook
*/
public interface AccountBookMapper extends BaseMapper<AccountBook> {

    Boolean updateSort(@Param("userId") Long userId, @Param("list") List<UpdateAccountBookSortDTO> list);
}




