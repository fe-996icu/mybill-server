package com.icu.mybill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icu.mybill.dto.billcategory.UpdateBillCategorySortDTO;
import com.icu.mybill.pojo.BillCategory;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【bill_category(账单分类表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.BillCategory
*/
public interface BillCategoryMapper extends BaseMapper<BillCategory> {
    boolean updateSort(Long userId, List<UpdateBillCategorySortDTO> list);
}




