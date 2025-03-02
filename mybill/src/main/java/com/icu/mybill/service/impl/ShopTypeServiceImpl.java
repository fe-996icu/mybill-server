package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.shoptype.UpdateShopTypeDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.ShopTypeMapper;
import com.icu.mybill.pojo.ShopType;
import com.icu.mybill.service.ShopTypeService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【shop_type(商家类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType>
    implements ShopTypeService{


    @Autowired
    private ShopTypeMapper shopTypeMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<ShopType> queryList = this.lambdaQuery()
                // .eq(ShopType::getUserId, userId)
                .in(ShopType::getId, list.stream().map(UpdateSortDTO::getId).toList())
                .list();

        // 查询到的数据量与查询的id数量不一致，说明有id不存在，抛出异常
        if (list.size() != queryList.size()){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        // 遍历查询到的数据，判断用户id是否与当前用户id一致，不一致抛出异常
        queryList.forEach(item -> {
            if (!item.getUserId().equals(userId)) {
                throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
            }
        });

        return this.shopTypeMapper.updateSort(userId, list);
    }

    @Override
    public boolean saveOne(ShopType shopType) {
        Long userId = ThreadLocalHelper.get().getId();
        shopType.setUserId(userId);

        return this.save(shopType);
    }

    @Override
    public boolean updateData(UpdateShopTypeDTO updateShopTypeDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        ShopType shopType = this.getById(updateShopTypeDTO.getId());

        if(shopType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!shopType.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        BeanUtil.copyProperties(shopType, updateShopTypeDTO);

        return this.updateById(shopType);
    }

    @Override
    public boolean deleteById(long id) {
        ShopType shopType = this.getById(id);

        if (shopType == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!shopType.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




