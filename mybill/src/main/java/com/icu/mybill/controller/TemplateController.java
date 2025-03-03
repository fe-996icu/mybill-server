package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.template.CreateTemplateDTO;
import com.icu.mybill.dto.template.UpdateTemplateDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.Template;
import com.icu.mybill.service.TemplateService;
import com.icu.mybill.vo.template.TemplateVO;
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
@RequestMapping("template")
// 接口分组名称和分组描述
@Tag(name = "账单模版", description = "所有和账单模版相关的接口")
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    /**
     * 创建账单模版
     *
     * @param createTemplateDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建账单模版", description = "创建账单模版，接收一个账单模版对象")
    public Result<TemplateVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的账单模版对象", required = true)
            @RequestBody @Validated CreateTemplateDTO createTemplateDTO
    ) {
        Template template = BeanUtil.copyProperties(createTemplateDTO, Template.class);

        templateService.saveOne(template);

        return Result.ok(BeanUtil.copyProperties(template, TemplateVO.class));
    }

    /**
     * 获取账单模版列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取账单模版全部列表", description = "获取账单模版全部列表")
    public Result<List<TemplateVO>> list(
            @Parameter(description = "账本id", required = true)
            @RequestParam(value = "accountBookId", required = true)
            Long accountBookId
    ) {
        List<Template> list = this.templateService.listByAccountBookId(accountBookId);

        List<TemplateVO> memberTypeVOS = BeanUtil.copyToList(list, TemplateVO.class);

        return Result.ok(memberTypeVOS);
    }

    /**
     * 更新账单模版
     *
     * @param updateTemplateDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新账单模版", description = "更新账单模版")
    public Result<Boolean> update(
            @Parameter(description = "要更新的账单模版信息", required = true) @RequestBody @Validated UpdateTemplateDTO updateTemplateDTO
    ) {
        // 账单模版名称和账单模版图标不能都为空
        // if (StringUtils.isAllEmpty(updateTemplateDTO.getName(), updateTemplateDTO.getIcon())){
        //     return Result.fail(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        // }

        boolean result = templateService.updateData(updateTemplateDTO);

        return Result.ok(result);
    }

    /**
     * 删除账单模版
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除账单模版", description = "删除账单模版")
    public Result<Boolean> delete(
            @Parameter(description = "账单模版id", required = true) @RequestParam Long id
    ) {
        boolean result = templateService.deleteById(id);

        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新账单模版排序", description = "更新账单模版排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新账单模版排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = templateService.updateSort(list);

        return Result.ok(result);
    }
}
