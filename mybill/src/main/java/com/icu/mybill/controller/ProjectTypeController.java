package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.projecttype.CreateProjectTypeDTO;
import com.icu.mybill.dto.projecttype.UpdateProjectTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.ProjectType;
import com.icu.mybill.service.ProjectTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.projecttype.ProjectTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("projecttype")
// 接口分组名称和分组描述
@Tag(name = "项目类型", description = "所有和项目类型相关的接口")
public class ProjectTypeController {
    @Autowired
    private ProjectTypeService projectTypeService;

    /**
     * 创建项目类型
     *
     * @param createProjectTypeDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建项目类型", description = "创建项目类型，接收一个项目类型对象")
    public Result<ProjectTypeVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的项目类型对象", required = true)
            @RequestBody @Validated CreateProjectTypeDTO createProjectTypeDTO
    ) {
        ProjectType projectType = BeanUtil.copyProperties(createProjectTypeDTO, ProjectType.class);
        projectTypeService.saveOne(projectType);

        return Result.ok(BeanUtil.copyProperties(projectType, ProjectTypeVO.class));
    }

    /**
     * 获取项目类型全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取项目类型全部列表", description = "获取项目类型全部列表")
    public Result<List<ProjectTypeVO>> list() {
        LambdaQueryWrapper<ProjectType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectType::getUserId, ThreadLocalHelper.get().getId());
        queryWrapper.orderByAsc(ProjectType::getSort, ProjectType::getCreateTime);

        List<ProjectType> list = this.projectTypeService.list(queryWrapper);
        List<ProjectTypeVO> projectTypeVOS = BeanUtil.copyToList(list, ProjectTypeVO.class);

        return Result.ok(projectTypeVOS);
    }

    /**
     * 更新项目类型
     *
     * @param updateProjectTypeDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新项目类型", description = "更新项目类型")
    public Result<Boolean> update(
            @Parameter(description = "要更新的项目类型信息", required = true) @RequestBody @Validated UpdateProjectTypeDTO updateProjectTypeDTO
    ) {
        // 项目类型名称和项目类型图标不能都为空
        // if (StringUtils.isAllEmpty(updateProjectTypeDTO.getName(), updateProjectTypeDTO.getIcon())){
        //     return Result.fail(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        // }

        boolean result = projectTypeService.updateData(updateProjectTypeDTO);

        return Result.ok(result);
    }

    /**
     * 删除项目类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除项目类型", description = "删除项目类型")
    public Result<Boolean> delete(
            @Parameter(description = "项目类型id", required = true) @RequestParam Long id
    ) {
        boolean result = projectTypeService.deleteById(id);

        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新项目类型排序", description = "更新项目类型排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新项目类型排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = projectTypeService.updateSort(list);

        return Result.ok(result);
    }
}
