package com.icu.mybill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.pojo.AccountBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.AccountBook
*/
public interface AccountBookMapper extends BaseMapper<AccountBook> {

    boolean updateSort(@Param("userId") long userId, @Param("list") List<UpdateSortDTO> list);
}




