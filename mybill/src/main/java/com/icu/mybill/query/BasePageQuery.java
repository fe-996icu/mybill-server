package com.icu.mybill.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "排序字段", defaultValue = "id")
    private String orderBy = "id";
    @Schema(description = "排序方式", defaultValue = "true")
    private Boolean isAsc = true;

    /**
     * 默认按照id升序排序，自动装配 page、size、orderBy、isAsc
     * @return
     * @param <T>
     */
    public <T> Page<T> toPage(){
        return toPage(OrderItem.asc("id"));
    }

    /**
     * 自定义默认排序规则集合
     * @param orders
     * @param <T>
     * @return
     */
    public <T> Page<T> toPage(OrderItem ... orders){
        // 组合分页查询条件
        Page<T> page = new Page<>(pageNum, pageSize);

        // 设置排序条件
        if(orderBy != null){
            page.addOrder(new OrderItem().setColumn(orderBy).setAsc(isAsc));
        }else{
            page.addOrder(orders);
        }

        return page;
    }


    /**
     * 自定义排序规则
     * @param defaultOrderBy
     * @param isAsc
     * @param <T>
     * @return
     */
    public <T> Page<T> toPage(String defaultOrderBy, Boolean isAsc){
        return toPage(new OrderItem().setColumn(defaultOrderBy).setAsc(isAsc));
    }
}
