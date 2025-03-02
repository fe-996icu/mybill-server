package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.billcategory.UpdateBillCategoryDTO;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.BillCategoryMapper;
import com.icu.mybill.pojo.BillCategory;
import com.icu.mybill.service.BillCategoryService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【bill_category(账单分类表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class BillCategoryServiceImpl extends ServiceImpl<BillCategoryMapper, BillCategory>
    implements BillCategoryService{

    @Autowired
    private BillCategoryMapper billCategoryMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<BillCategory> queryList = this.lambdaQuery()
                // .eq(BillCategory::getUserId, userId)
                .in(BillCategory::getId, list.stream().map(UpdateSortDTO::getId).toList())
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

        return this.billCategoryMapper.updateSort(userId, list);
    }


    @Override
    @Transactional
    public boolean saveParentAndChildren(BillCategory parent, List<BillCategory> children) {
        Long userId = ThreadLocalHelper.get().getId();

        // 保存父级账单分类
        parent.setUserId(userId);
        parent.setParentId(null);
        this.save(parent);

        // 设置二级账户类型初始值
        for (int i = 0; i < children.size(); i++) {
            BillCategory item = children.get(i);
            item.setParentId(parent.getId());
            item.setType(parent.getType());
            item.setUserId(userId);
            item.setSort(i + 1);
        }

        return this.saveBatch(children);
    }

    @Transactional
    @Override
    public boolean saveChildren(BillCategory billCategory) {
        Long userId = ThreadLocalHelper.get().getId();

        // 创建子分类时，parentId必须存在
        if (billCategory.getParentId() == null) {
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_NOT_EXIST);
        }

        // 查询父级账户类型
        BillCategory parent = this.lambdaQuery()
                .eq(BillCategory::getId, billCategory.getParentId())
                .one();

        // 没有查询到父账户类型
        if (parent == null) {
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_NOT_EXIST);
        }

        // 操作的不是自己的数据
        if (!parent.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.REFERENCE_PARENT_DATA_NOT_SELF_ERROR);
        }

        // 父级账户类型必须是顶级
        if (parent.getParentId() != null){
            throw new FrontendErrorPromptException(ResultCode.PARENT_BILL_CATEGORY_REQUIRE_TOP_LEVEL);
        }

        billCategory.setUserId(userId);
        billCategory.setType(parent.getType());

        return this.save(billCategory);
    }

    @Override
    public boolean updateData(UpdateBillCategoryDTO updateBillCategoryDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        BillCategory billCategory = this.getById(updateBillCategoryDTO.getId());

        if(billCategory == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!billCategory.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        BeanUtil.copyProperties(billCategory, updateBillCategoryDTO);

        return this.updateById(billCategory);
    }

    @Override
    public boolean deleteById(long id) {
        BillCategory billCategory = this.getById(id);

        if (billCategory == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!billCategory.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




