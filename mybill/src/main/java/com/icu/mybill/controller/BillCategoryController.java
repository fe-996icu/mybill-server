package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.billcategory.CreateParentAndChildrenBillCategoryDTO;
import com.icu.mybill.dto.billcategory.CreateChildrenBillCategoryDTO;
import com.icu.mybill.dto.billcategory.UpdateBillCategoryDTO;
import com.icu.mybill.dto.billcategory.UpdateBillCategorySortDTO;
import com.icu.mybill.enums.BillType;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.pojo.BillCategory;
import com.icu.mybill.service.BillCategoryService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.util.TreeDataHelper;
import com.icu.mybill.vo.billcategory.accounttype.BillCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("billcategory")
// 接口分组名称和分组描述
@Tag(name = "账单分类", description = "所有和账单分类相关的接口")
public class BillCategoryController {
    @Autowired
    private BillCategoryService billCategoryService;

    /**
     * 创建账单分类（父分类和子分类）
     *
     * @param createParentAndChildrenBillCategoryDTO
     * @return
     */
    @PostMapping("createParentAndChild")
    // 接口名和接口描述
    @Operation(summary = "创建账单分类（父分类和子分类）", description = "创建账单分类（父分类和子分类）")
    public Result<BillCategoryVO> createParentAndChildren(
            // 接口参数描述
            @Parameter(description = "要创建的账单分类对象，需要包含children属性", required = true)
            @RequestBody @Validated CreateParentAndChildrenBillCategoryDTO createParentAndChildrenBillCategoryDTO
    ) {
        BillCategory parent = BeanUtil.copyProperties(createParentAndChildrenBillCategoryDTO, BillCategory.class);
        List<BillCategory> children = BeanUtil.copyToList(createParentAndChildrenBillCategoryDTO.getChildren(), BillCategory.class);

        billCategoryService.saveParentAndChild(parent, children);

        BillCategoryVO parentVo = BeanUtil.copyProperties(parent, BillCategoryVO.class);
        parentVo.setChildren(BeanUtil.copyToList(children, BillCategoryVO.class));

        return Result.ok(parentVo);
    }

    /**
     * 创建二级账单分类
     *
     * @param createChildrenBillCategoryDTO
     * @return
     */
    @Transactional
    @PostMapping("createChild")
    // 接口名和接口描述
    @Operation(summary = "创建二级账单分类", description = "创建二级账单分类，接收一个账单分类对象，必须包含parentId")
    public Result<BillCategoryVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的二级账单分类对象", required = true)
            @RequestBody @Validated CreateChildrenBillCategoryDTO createChildrenBillCategoryDTO
    ) {
        BillCategory billCategory = BeanUtil.copyProperties(createChildrenBillCategoryDTO, BillCategory.class);
        billCategoryService.saveSub(billCategory);
        return Result.ok(BeanUtil.copyProperties(billCategory, BillCategoryVO.class));
    }

    /**
     * 获取账单分类全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取账单分类全部列表", description = "获取账单分类全部列表")
    public Result<List<BillCategoryVO>> list(
            @Schema(description = "账单类型，1为支出，2为收入，不传查所有", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @RequestParam(value = "type", required = false)
            BillType type
    ) {
        List<BillCategory> list = this.billCategoryService.list(
                Wrappers.lambdaQuery(BillCategory.class)
                        .eq(BillCategory::getUserId, ThreadLocalHelper.get().getId())
                        .eq(type!=null, BillCategory::getType, type)
        );

        // 生成树形结构
        List<Tree<Long>> treeList = TreeDataHelper.beanListToTreeList(list);
        // 转一下，方便明确返回值类型，swagger自动识别VO，否则生成的文档字段是Tree的不直观
        List<BillCategoryVO> billCategoryVOS = BeanUtil.copyToList(treeList, BillCategoryVO.class);

        return Result.ok(billCategoryVOS);
    }

    /**
     * 更新账单分类
     *
     * @param updateBillCategoryDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新账单分类", description = "更新账单分类")
    public Result<Boolean> update(
            @Parameter(description = "要更新的账单分类信息", required = true) @RequestBody @Validated UpdateBillCategoryDTO updateBillCategoryDTO
    ) {
        // 账单分类名称和账单分类图标不能都为空
        if (StringUtils.isAllEmpty(updateBillCategoryDTO.getName(), updateBillCategoryDTO.getIcon())){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        }

        boolean result = billCategoryService.updateData(updateBillCategoryDTO);

        return Result.ok(result);
    }

    /**
     * 删除账单分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除账单分类", description = "删除账单分类")
    public Result<Boolean> delete(
            @Parameter(description = "账单分类id", required = true) @RequestParam("id") Long id
    ) {
        boolean result = billCategoryService.deleteById(id);
        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新账单分类排序", description = "更新账单分类排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新账单分类排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateBillCategorySortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = billCategoryService.updateSort(list);
        return Result.ok(result);
    }
}
