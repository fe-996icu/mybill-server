package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.mapper.AccountBookMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AccountBookServiceImpl extends ServiceImpl<AccountBookMapper, AccountBook>
    implements AccountBookService{

}




