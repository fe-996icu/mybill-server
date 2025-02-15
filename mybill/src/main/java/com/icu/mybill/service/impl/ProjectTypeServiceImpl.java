package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.ProjectType;
import com.icu.mybill.service.ProjectTypeService;
import com.icu.mybill.mapper.ProjectTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【project_type(项目类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class ProjectTypeServiceImpl extends ServiceImpl<ProjectTypeMapper, ProjectType>
    implements ProjectTypeService{

}




