package com.icu.mybill.query;

import com.icu.mybill.enums.BillQueryDateRange;
import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true) // 继承父类的equals和hashCode方法
@Data
public class BillListQuery extends BasePageQuery {
    @NotNull(message = "账本id不能为空")
    @Schema(description = "账本id（逻辑外键）")
    private Long accountBookId;

    // @NotNull(message = "账单查询时间范围不能为空")
    @Schema(description = "账单查询时间范围")
    private BillQueryDateRange dateRange = BillQueryDateRange.DEFAULT;

    @Schema(description = "开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期")
    private LocalDate endDate;

    @Schema(description = "账单类型")
    private BillType type;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "金额范围-最小金额开始")
    private BigDecimal fromAmount;

    @Schema(description = "金额范围-最大金额结束")
    private BigDecimal toAmount;
}
