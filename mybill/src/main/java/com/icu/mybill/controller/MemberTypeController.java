package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.membertype.CreateMemberTypeDTO;
import com.icu.mybill.dto.membertype.UpdateMemberTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.MemberType;
import com.icu.mybill.service.MemberTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.membertype.MemberTypeVO;
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
@RequestMapping("membertype")
// 接口分组名称和分组描述
@Tag(name = "成员类型", description = "所有和成员类型相关的接口")
public class MemberTypeController {
    @Autowired
    private MemberTypeService memberTypeService;

    /**
     * 创建成员类型
     *
     * @param createMemberTypeDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建成员类型", description = "创建成员类型，接收一个成员类型对象")
    public Result<MemberTypeVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的成员类型对象", required = true)
            @RequestBody @Validated CreateMemberTypeDTO createMemberTypeDTO
    ) {
        MemberType memberType = BeanUtil.copyProperties(createMemberTypeDTO, MemberType.class);
        memberTypeService.saveOne(memberType);

        return Result.ok(BeanUtil.copyProperties(memberType, MemberTypeVO.class));
    }

    /**
     * 获取成员类型全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取成员类型全部列表", description = "获取成员类型全部列表")
    public Result<List<MemberTypeVO>> list() {
        LambdaQueryWrapper<MemberType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberType::getUserId, ThreadLocalHelper.get().getId());
        queryWrapper.orderByAsc(MemberType::getSort, MemberType::getCreateTime);

        List<MemberType> list = this.memberTypeService.list(queryWrapper);
        List<MemberTypeVO> memberTypeVOS = BeanUtil.copyToList(list, MemberTypeVO.class);

        return Result.ok(memberTypeVOS);
    }

    /**
     * 更新成员类型
     *
     * @param updateMemberTypeDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新成员类型", description = "更新成员类型")
    public Result<Boolean> update(
            @Parameter(description = "要更新的成员类型信息", required = true) @RequestBody @Validated UpdateMemberTypeDTO updateMemberTypeDTO
    ) {
        // 成员类型名称和成员类型图标不能都为空
        // if (StringUtils.isAllEmpty(updateMemberTypeDTO.getName(), updateMemberTypeDTO.getIcon())){
        //     return Result.fail(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        // }

        boolean result = memberTypeService.updateData(updateMemberTypeDTO);

        return Result.ok(result);
    }

    /**
     * 删除成员类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除成员类型", description = "删除成员类型")
    public Result<Boolean> delete(
            @Parameter(description = "成员类型id", required = true) @RequestParam Long id
    ) {
        boolean result = memberTypeService.deleteById(id);

        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新成员类型排序", description = "更新成员类型排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新成员类型排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = memberTypeService.updateSort(list);

        return Result.ok(result);
    }
}
