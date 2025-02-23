package com.icu.mybill.dto;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 封装返回给前端的统一分页对象，因为可能会提供给其他微服务使用，所以封装在dto包里
 * @param <T>
 */
@Data
@Schema(description = "全局分页对象")
public class PageDTO <T> {
    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Long pages;
    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private Long total;
    /**
     * 数据集合
     */
    @Schema(description = "数据集合")
    private List<T> rows;

    /**
     * 将mybatis-plus的分页对象，转为前端分页对象
     * @param page
     * @param voClass
     * @return
     * @param <VO>
     * @param <POJO>
     */
    public static <VO, POJO> PageDTO<VO> of(Page<POJO> page, Class<VO> voClass) {
        List<VO> rows = BeanUtil.copyToList(page.getRecords(), voClass);

        PageDTO<VO> dto = new PageDTO<>();
        dto.setPages(page.getPages());
        dto.setRows(rows);
        dto.setTotal(page.getTotal());

        return dto;
    }


    /**
     * 将mybatis-plus的分页对象，转为前端分页对象
     * @param page
     * @param convert 通过转换器可以自定义POJO转成VO的行为
     * @return
     * @param <VO>
     * @param <POJO>
     */
    public static <VO, POJO> PageDTO<VO> of(Page<POJO> page, Function<POJO, VO> convert) { // 这里使用jdk8的函数式编程
        List<VO> rows = page.getRecords().stream().map(convert).collect(Collectors.toList());

        PageDTO<VO> dto = new PageDTO<>();
        dto.setPages(page.getPages());
        dto.setRows(rows);
        dto.setTotal(page.getTotal());

        return dto;
    }
}
