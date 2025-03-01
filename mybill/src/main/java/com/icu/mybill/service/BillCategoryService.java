package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.dto.billcategory.UpdateBillCategoryDTO;
import com.icu.mybill.dto.billcategory.UpdateBillCategorySortDTO;
import com.icu.mybill.pojo.BillCategory;

import java.util.List;

/**
 * @author zhangjianzhong
 * @description 针对表【bill_category(账单分类表)】的数据库操作Service
 * @createDate 2025-02-15 12:35:29
 */
public interface BillCategoryService extends IService<BillCategory> {

    boolean updateSort(List<UpdateBillCategorySortDTO> list);

    boolean saveParentAndChild(BillCategory parent, List<BillCategory> children);

    boolean saveSub(BillCategory billCategory);

    boolean updateData(UpdateBillCategoryDTO updateBillCategoryDTO);

    boolean deleteById(long id);
}
