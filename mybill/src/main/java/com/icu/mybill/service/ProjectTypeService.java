package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.projecttype.UpdateProjectTypeDTO;
import com.icu.mybill.pojo.ProjectType;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【project_type(项目类型表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface ProjectTypeService extends IService<ProjectType> {
    /**
     * 更新排序
     * @param list
     * @return
     */
    boolean updateSort(List<UpdateSortDTO> list);

    boolean saveOne(ProjectType projectType);

    boolean updateData(UpdateProjectTypeDTO updateProjectTypeDTO);

    boolean deleteById(long id);
}
