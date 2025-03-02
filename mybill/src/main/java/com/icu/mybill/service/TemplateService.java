package com.icu.mybill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.template.UpdateTemplateDTO;
import com.icu.mybill.pojo.Template;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【template(模板表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface TemplateService extends IService<Template> {
    /**
     * 更新排序
     * @param list
     * @return
     */
    boolean updateSort(List<UpdateSortDTO> list);

    boolean saveOne(Template template);

    boolean updateData(UpdateTemplateDTO updateTemplateDTO);

    boolean deleteById(long id);
}
