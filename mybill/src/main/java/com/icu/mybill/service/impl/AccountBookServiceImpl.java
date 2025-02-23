package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.mapper.AccountBookMapper;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.query.BasePageQuery;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.accountbook.AccountBookVO;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AccountBookServiceImpl extends ServiceImpl<AccountBookMapper, AccountBook>
    implements AccountBookService{

    @Override
    public Page<AccountBook> pageQuery(BasePageQuery query) {
        // 组合分页查询条件
        Page<AccountBook> page = query.toPage();
        // 查询
        this.lambdaQuery().eq(AccountBook::getUserId, ThreadLocalHelper.get().getId()).page(page);

        return page;
    }

    @Override
    public AccountBook getById(Serializable id) {
        return this.lambdaQuery()
                .eq(AccountBook::getId, id)
                .eq(AccountBook::getUserId, ThreadLocalHelper.get().getId()).one();
    }
}




