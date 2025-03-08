package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.PageDTO;
import com.icu.mybill.dto.bill.CreateBillDTO;
import com.icu.mybill.dto.bill.UpdateBillDTO;
import com.icu.mybill.enums.BillQueryDateRange;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.Bill;
import com.icu.mybill.query.BillListQuery;
import com.icu.mybill.service.BillService;
import com.icu.mybill.vo.bill.BillVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("bill")
// 接口分组名称和分组描述
@Tag(name = "账单", description = "所有和账单相关的接口")
public class BillController {
    @Autowired
    private BillService billService;

    /**
     * 创建账单
     *
     * @param createBillDTO
     * @return
     */
    @PostMapping("create")
    // 接口名和接口描述
    @Operation(summary = "创建账单", description = "创建账单，接收一个账单对象")
    public Result<BillVO> create(
            // 接口参数描述
            @Parameter(description = "要创建的账单对象", required = true)
            @RequestBody @Validated CreateBillDTO createBillDTO
    ) {
        Bill bill = BeanUtil.copyProperties(createBillDTO, Bill.class);

        boolean result = billService.saveOne(bill);

        return Result.ok(BeanUtil.copyProperties(bill, BillVO.class));
    }

    /**
     * 更新账单
     *
     * @param updateBillDTO
     * @return
     */
    @PutMapping("update")
    @Operation(summary = "更新账单", description = "更新账单")
    public Result<Boolean> update(
            @Parameter(description = "要更新的账单信息", required = true)
            @RequestBody @Validated
            UpdateBillDTO updateBillDTO
    ) {
        boolean result = billService.updateData(updateBillDTO);

        return Result.ok(result);
    }

    /**
     * 删除账单
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete")
    @Operation(summary = "删除账单", description = "删除账单")
    public Result<Boolean> delete(
            @Parameter(description = "账单id", required = true) @RequestParam Long id
    ) {
        boolean result = billService.deleteById(id);

        return Result.ok(result);
    }

    /**
     * 分页获取账单列表
     *
     * @param query
     * @return
     */
    @GetMapping("page")
    @Operation(summary = "分页获取账本列表", description = "分页获取账本列表")
    public Result<PageDTO<BillVO>> page(
            @Parameter(description = "分页查询参数")
            @ModelAttribute() @Validated BillListQuery query
    ) {
        // 校验日期范围，不是 默认查询时，日期范围不能为空
        if (query.getDateRange() != BillQueryDateRange.DEFAULT){
            if (query.getStartDate() == null || query.getEndDate() == null){
                return Result.fail(ResultCode.QUERY_DATE_RANGE_EMPTY_ERROR);
            }

            // 开始日期不能大于结束日期
            if (query.getStartDate().isAfter(query.getEndDate())){
                return Result.fail(ResultCode.QUERY_START_DATE_GREATER_THAN_END_DATE_ERROR);
            }
        }else{
            query.setStartDate(null);
            query.setEndDate(null);
        }

        Page<Bill> page = billService.pageQuery(query);
        PageDTO<BillVO> dto = PageDTO.of(page, BillVO.class);

        return Result.ok(dto);
    }

}
