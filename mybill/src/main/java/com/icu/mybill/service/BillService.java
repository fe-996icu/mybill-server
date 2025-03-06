package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.dto.bill.UpdateBillDTO;
import com.icu.mybill.pojo.Bill;
import com.icu.mybill.query.BillListQuery;

/**
* @author zhangjianzhong
* @description 针对表【bill(账单表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface BillService extends IService<Bill> {

    boolean saveOne(Bill bill);

    boolean updateData(UpdateBillDTO updateBillDTO);

    boolean deleteById(long id);

    Page<Bill> pageQuery(BillListQuery query);
}
