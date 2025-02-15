package com.icu.mybill.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;


/**
 * 封装统一的分页查询请求参数实体，各业务实体的查询参数需要做分页的继承此实体即可。
 */
@Data
public class BasePageQuery {
    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;
    private Boolean isAsc;

    // 默认按照last_update_time倒序排序
    public <T> Page<T> toPage(){
        return toPage(OrderItem.desc("last_update_time"));
    }

    // 自定义默认排序规则集合
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


    // 自定义排序规则
    public <T> Page<T> toPage(String defaultOrderBy, Boolean isAsc){
        return toPage(new OrderItem().setColumn(defaultOrderBy).setAsc(isAsc));
    }
}
