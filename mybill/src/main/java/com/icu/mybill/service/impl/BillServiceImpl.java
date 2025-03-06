package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.bill.UpdateBillDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.*;
import com.icu.mybill.pojo.*;
import com.icu.mybill.service.BillService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author zhangjianzhong
* @description 针对表【bill(账单表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
    implements BillService{

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillCategoryMapper billCategoryMapper;

    @Autowired
    private AccountTypeMapper accountTypeMapper;

    @Autowired
    private MemberTypeMapper memberTypeMapper;

    @Autowired
    private ShopTypeMapper shopTypeMapper;

    @Autowired
    private ProjectTypeMapper projectTypeMapper;

    @Autowired
    private AccountBookMapper accountBookMapper;

    @Override
    public boolean saveOne(Bill bill) {
        Long userId = ThreadLocalHelper.get().getId();
        bill.setUserId(userId);


        // 校验账单分类是否在表中存在，并且需要是二级账单分类
        BillCategory billCategory = this.billCategoryMapper.selectById(bill.getBillCategoryId());
        if (billCategory == null){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_EXISTS);
        }
        if (billCategory.getParentId() == null){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_REQUIRED_SUB_LEVEL);
        }
        if(!Objects.equals(billCategory.getUserId(), userId)){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_SELF_ERROR);
        }


        // 校验账户类型是否在表中存在，并且需要时二级账单分类
        AccountType accountType = this.accountTypeMapper.selectById(bill.getAccountTypeId());
        if (accountType == null){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_EXISTS);
        }
        if (accountType.getParentId() == null){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_REQUIRED_SUB_LEVEL);
        }
        if(!Objects.equals(accountType.getUserId(), userId)){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_SELF_ERROR);
        }


        // 校验成员类型是否在表中存在
        if (bill.getMemberTypeId() != null){
            MemberType memberType = this.memberTypeMapper.selectById(bill.getMemberTypeId());
            if (memberType == null){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_EXISTS);
            }
            if(!Objects.equals(memberType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验商家类型是否在表中存在
        if (bill.getShopTypeId() != null){
            ShopType shopType = this.shopTypeMapper.selectById(bill.getShopTypeId());

            if (shopType == null){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(shopType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验项目类型是否在表中存在
        if (bill.getProjectTypeId() != null){
            ProjectType projectType = this.projectTypeMapper.selectById(bill.getProjectTypeId());

            if (projectType == null){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(projectType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验账本id是否存在
        if (bill.getAccountBookId() != null){
            AccountBook accountBook = this.accountBookMapper.selectById(bill.getAccountBookId());

            if (accountBook == null){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_EXISTS);
            }

            if(!Objects.equals(accountBook.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_SELF_ERROR);
            }
        }


        return this.save(bill);
    }

    @Override
    public boolean updateData(UpdateBillDTO updateBillDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        Bill bill = this.getById(updateBillDTO.getId());
        if(bill == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!bill.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }


        // 校验账单分类是否在表中存在，并且需要是二级账单分类
        BillCategory billCategory = this.billCategoryMapper.selectById(updateBillDTO.getBillCategoryId());
        if (billCategory == null){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_EXISTS);
        }
        if (billCategory.getParentId() == null){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_REQUIRED_SUB_LEVEL);
        }
        if(!Objects.equals(billCategory.getUserId(), userId)){
            throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_SELF_ERROR);
        }


        // 校验账户类型是否在表中存在，并且需要时二级账单分类
        AccountType accountType = this.accountTypeMapper.selectById(updateBillDTO.getAccountTypeId());
        if (accountType == null){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_EXISTS);
        }
        if (accountType.getParentId() == null){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_REQUIRED_SUB_LEVEL);
        }
        if(!Objects.equals(accountType.getUserId(), userId)){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_SELF_ERROR);
        }


        // 校验成员类型是否在表中存在
        if (updateBillDTO.getMemberTypeId() != null){
            MemberType memberType = this.memberTypeMapper.selectById(updateBillDTO.getMemberTypeId());
            if (memberType == null){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_EXISTS);
            }
            if(!Objects.equals(memberType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验商家类型是否在表中存在
        if (updateBillDTO.getShopTypeId() != null){
            ShopType shopType = this.shopTypeMapper.selectById(updateBillDTO.getShopTypeId());

            if (shopType == null){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(shopType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验项目类型是否在表中存在
        if (updateBillDTO.getProjectTypeId() != null){
            ProjectType projectType = this.projectTypeMapper.selectById(updateBillDTO.getProjectTypeId());

            if (projectType == null){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(projectType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_SELF_ERROR);
            }
        }


        // null值允许被更新，因为前端可以删除一些字段设置
        return this.lambdaUpdate()
                .eq(Bill::getId, updateBillDTO.getId())
                .set(Bill::getAccountTypeId, updateBillDTO.getAccountTypeId())
                .set(Bill::getBillCategoryId, updateBillDTO.getBillCategoryId())
                .set(Bill::getAmount, updateBillDTO.getAmount())
                .set(Bill::getDate, updateBillDTO.getDate())
                .set(Bill::getMemberTypeId, updateBillDTO.getMemberTypeId())
                .set(Bill::getShopTypeId, updateBillDTO.getShopTypeId())
                .set(Bill::getProjectTypeId, updateBillDTO.getProjectTypeId())
                .set(Bill::getNotes, updateBillDTO.getNotes())
                .update();
    }

    @Override
    public boolean deleteById(long id) {
        Bill bill = this.getById(id);
        if (bill == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!bill.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }
}




