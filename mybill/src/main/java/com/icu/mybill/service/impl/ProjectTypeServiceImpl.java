package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.projecttype.UpdateProjectTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.ProjectTypeMapper;
import com.icu.mybill.pojo.ProjectType;
import com.icu.mybill.service.ProjectTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【project_type(项目类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class ProjectTypeServiceImpl extends ServiceImpl<ProjectTypeMapper, ProjectType>
    implements ProjectTypeService{

    @Autowired
    private ProjectTypeMapper projectTypeMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<ProjectType> queryList = this.lambdaQuery()
                // .eq(ProjectType::getUserId, userId)
                .in(ProjectType::getId, list.stream().map(UpdateSortDTO::getId).toList())
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

        return this.projectTypeMapper.updateSort(userId, list);
    }

    @Override
    public boolean saveOne(ProjectType projectType) {
        Long userId = ThreadLocalHelper.get().getId();
        projectType.setUserId(userId);

        return this.save(projectType);
    }

    @Override
    public boolean updateData(UpdateProjectTypeDTO updateProjectTypeDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        ProjectType projectType = this.getById(updateProjectTypeDTO.getId());

        if(projectType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!projectType.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        BeanUtil.copyProperties(projectType, updateProjectTypeDTO);

        return this.updateById(projectType);
    }

    @Override
    public boolean deleteById(long id) {
        ProjectType projectType = this.getById(id);

        if (projectType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!projectType.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




