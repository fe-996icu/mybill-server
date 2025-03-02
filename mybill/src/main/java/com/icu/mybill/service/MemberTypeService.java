package com.icu.mybill.service;

import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.membertype.UpdateMemberTypeDTO;
import com.icu.mybill.pojo.MemberType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【member_type(成员类型表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface MemberTypeService extends IService<MemberType> {
    /**
     * 更新排序
     * @param list
     * @return
     */
    boolean updateSort(List<UpdateSortDTO> list);

    boolean saveOne(MemberType memberType);

    boolean updateData(UpdateMemberTypeDTO updateMemberTypeDTO);

    boolean deleteById(long id);
}
