package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icu.mybill.dto.accountbook.UpdateAccountBookSortDTO;
import com.icu.mybill.pojo.AccountBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.query.BasePageQuery;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface AccountBookService extends IService<AccountBook> {

    Page<AccountBook> pageQuery(BasePageQuery basePageQuery);

    /**
     * 更新账本排序
     * @param list
     * @return
     */
    Boolean updateSort(List<UpdateAccountBookSortDTO> list);
}
