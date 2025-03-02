package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.membertype.UpdateMemberTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.pojo.MemberType;
import com.icu.mybill.service.MemberTypeService;
import com.icu.mybill.mapper.MemberTypeMapper;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【member_type(成员类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class MemberTypeServiceImpl extends ServiceImpl<MemberTypeMapper, MemberType>
    implements MemberTypeService{

    @Autowired
    private MemberTypeMapper memberTypeMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<MemberType> queryList = this.lambdaQuery()
                // .eq(MemberType::getUserId, userId)
                .in(MemberType::getId, list.stream().map(UpdateSortDTO::getId).toList())
                .list();

        // 查询到的数据量与查询的id数量不一致，说明有id不存在，抛出异常
        if (list.size() != queryList.size()){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        // 遍历查询到的数据，判断用户id是否与当前用户id一致，不一致抛出异常
        queryList.forEach(item -> {
            if (!item.getUserId().equals(userId)) {
                throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
            }
        });

        return this.memberTypeMapper.updateSort(userId, list);
    }

    @Override
    public boolean saveOne(MemberType accountBook) {
        Long userId = ThreadLocalHelper.get().getId();
        accountBook.setUserId(userId);

        return this.save(accountBook);
    }

    @Override
    public boolean updateData(UpdateMemberTypeDTO updateMemberTypeDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        MemberType memberType = this.getById(updateMemberTypeDTO.getId());

        if(memberType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!memberType.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        BeanUtil.copyProperties(memberType, updateMemberTypeDTO);

        return this.updateById(memberType);
    }

    @Override
    public boolean deleteById(long id) {
        MemberType memberType = this.getById(id);

        if (memberType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!memberType.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




