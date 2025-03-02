package com.icu.mybill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.pojo.MemberType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【member_type(成员类型表)】的数据库操作Mapper
* @createDate 2025-02-15 12:35:29
* @Entity com.icu.mybill.pojo.MemberType
*/
public interface MemberTypeMapper extends BaseMapper<MemberType> {
    boolean updateSort(@Param("userId") long userId, @Param("list") List<UpdateSortDTO> list);


}




