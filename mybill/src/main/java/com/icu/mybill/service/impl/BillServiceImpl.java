package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.Bill;
import com.icu.mybill.service.BillService;
import com.icu.mybill.mapper.BillMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【bill(账单表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
    implements BillService{

}




