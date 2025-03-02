package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.shoptype.CreateShopTypeDTO;
import com.icu.mybill.dto.shoptype.UpdateShopTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.ShopType;
import com.icu.mybill.service.ShopTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import com.icu.mybill.vo.shoptype.ShopTypeVO;
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
@RequestMapping("shoptype")
// 接口分组名称和分组描述
@Tag(name = "商家类型", description = "所有和商家类型相关的接口")
public class ShopTypeController {
    @Autowired
    private ShopTypeService shopTypeService;

    /**
     * 创建商家类型
     *
     * @param createShopTypeDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建商家类型", description = "创建商家类型，接收一个商家类型对象")
    public Result<ShopTypeVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的商家类型对象", required = true)
            @RequestBody @Validated CreateShopTypeDTO createShopTypeDTO
    ) {
        ShopType shopTypeVO = BeanUtil.copyProperties(createShopTypeDTO, ShopType.class);
        shopTypeService.saveOne(shopTypeVO);

        return Result.ok(BeanUtil.copyProperties(shopTypeVO, ShopTypeVO.class));
    }

    /**
     * 获取商家类型全部列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "获取商家类型全部列表", description = "获取商家类型全部列表")
    public Result<List<ShopTypeVO>> list() {
        LambdaQueryWrapper<ShopType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopType::getUserId, ThreadLocalHelper.get().getId());
        queryWrapper.orderByAsc(ShopType::getSort, ShopType::getCreateTime);

        List<ShopType> list = this.shopTypeService.list(queryWrapper);
        List<ShopTypeVO> shopTypeVOS = BeanUtil.copyToList(list, ShopTypeVO.class);

        return Result.ok(shopTypeVOS);
    }

    /**
     * 更新商家类型
     *
     * @param updateShopTypeDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新商家类型", description = "更新商家类型")
    public Result<Boolean> update(
            @Parameter(description = "要更新的商家类型信息", required = true) @RequestBody @Validated UpdateShopTypeDTO updateShopTypeDTO
    ) {
        // 商家类型名称和商家类型图标不能都为空
        // if (StringUtils.isAllEmpty(updateShopTypeDTO.getName(), updateShopTypeDTO.getIcon())){
        //     return Result.fail(ResultCode.UPDATE_REQUIRE_ONE_FIELD_ERROR);
        // }

        boolean result = shopTypeService.updateData(updateShopTypeDTO);

        return Result.ok(result);
    }

    /**
     * 删除商家类型
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除商家类型", description = "删除商家类型")
    public Result<Boolean> delete(
            @Parameter(description = "商家类型id", required = true) @RequestParam Long id
    ) {
        boolean result = shopTypeService.deleteById(id);

        return Result.ok(result);
    }

    @PutMapping("sort")
    @Operation(summary = "更新商家类型排序", description = "更新商家类型排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新商家类型排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        boolean result = shopTypeService.updateSort(list);

        return Result.ok(result);
    }
}
