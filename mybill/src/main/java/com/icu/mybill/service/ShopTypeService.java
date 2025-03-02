package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.shoptype.UpdateShopTypeDTO;
import com.icu.mybill.pojo.ShopType;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【shop_type(商家类型表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface ShopTypeService extends IService<ShopType> {

    /**
     * 更新排序
     * @param list
     * @return
     */
    boolean updateSort(List<UpdateSortDTO> list);

    boolean saveOne(ShopType shopType);

    boolean updateData(UpdateShopTypeDTO updateShopTypeDTO);

    boolean deleteById(long id);
}
