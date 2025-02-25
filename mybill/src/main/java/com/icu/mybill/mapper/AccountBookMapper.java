package com.icu.mybill.mapper;

import com.icu.mybill.dto.accountbook.UpdateAccountBookSortDTO;
import com.icu.mybill.pojo.AccountBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.AccountBook
*/
public interface AccountBookMapper extends BaseMapper<AccountBook> {

    Boolean updateSort(Long userId, List<UpdateAccountBookSortDTO> list);
}




