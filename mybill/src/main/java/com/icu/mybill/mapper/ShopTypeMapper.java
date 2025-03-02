package com.icu.mybill.mapper;

import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.pojo.ShopType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【shop_type(商家类型表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.ShopType
*/
public interface ShopTypeMapper extends BaseMapper<ShopType> {
    boolean updateSort(@Param("userId") long userId, @Param("list") List<UpdateSortDTO> list);
}




