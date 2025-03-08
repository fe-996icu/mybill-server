package com.icu.mybill.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 封装统一的分页查询请求参数实体，各业务实体的查询参数需要做分页的继承此实体即可。
 */
@Data
@Schema(description = "分页查询请求参数基类")
public class BasePageQuery {
    @Schema(description = "当前页码", defaultValue = "1")
    private Integer pageNum = 1;
    @Schema(description = "每页数量", defaultValue = "100")
    private Integer pageSize = 100;
    @Schema(description = "排序字段")
    private String orderBy;
    @Schema(description = "排序方式", defaultValue = "true")
    private Boolean isAsc = true;

    /**
     * 创建分页对象，自动装配 page、size
     * @param <T>
     * @return
     */
    private <T> Page<T> createPage(){
        // 组合分页查询条件
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 创建分页对象，自动装配 pageNum、 pageSize、 orderBy 和 isAsc
     * @return
     * @param <T>
     */
    public <T> Page<T> toPage(){
        // 组合分页查询条件
        Page<T> page = this.createPage();
        if(this.orderBy != null){
            page.addOrder(new OrderItem().setColumn(orderBy).setAsc(isAsc));
        }
        return page;
    }

    /**
     * 创建分页对象，自定义排序规则，单个或多个
     * @param orders
     * @param <T>
     * @return
     */
    public <T> Page<T> toPage(@NotNull OrderItem ... orders){
        Page<T> page = this.createPage();
        // 设置排序条件
        page.addOrder(orders);
        return page;
    }

    /**
     * 创建分页对象，自定义排序规则，单个排序字段
     * @param orderBy
     * @param isAsc
     * @param <T>
     * @return
     */
    public <T> Page<T> toPage(String orderBy, boolean isAsc){
        return this.toPage(new OrderItem().setColumn(orderBy).setAsc(isAsc));
    }
}
