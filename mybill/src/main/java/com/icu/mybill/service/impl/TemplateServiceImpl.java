package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.dto.template.UpdateTemplateDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.*;
import com.icu.mybill.pojo.*;
import com.icu.mybill.service.TemplateService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author zhangjianzhong
 * @description 针对表【template(模板表)】的数据库操作Service实现
 * @createDate 2025-02-15 12:35:29
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
        implements TemplateService{

    @Autowired
    private TemplateMapper templateMapper;

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
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<Template> templateList = this.lambdaQuery()
                // .eq(Template::getUserId, userId)
                .in(Template::getId, list.stream().map(UpdateSortDTO::getId).toList())
                .list();

        // 查询到的数据量与查询的id数量不一致，说明有id不存在，抛出异常
        if (list.size() != templateList.size()) {
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        // 遍历查询到的数据，判断用户id是否与当前用户id一致，不一致抛出异常
        templateList.forEach(item -> {
            if (!item.getUserId().equals(userId)) {
                throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
            }
        });

        return this.templateMapper.updateSort(userId, list);
    }

    @Override
    public boolean saveOne(Template template) {
        Long userId = ThreadLocalHelper.get().getId();
        template.setUserId(userId);

        // 校验账单分类是否在表中存在，并且需要是二级账单分类
        if (template.getBillCategoryId() != null){
            BillCategory billCategory = this.billCategoryMapper.selectById(template.getBillCategoryId());

            if (billCategory == null){
                throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_EXISTS);
            }

            if (billCategory.getParentId() == null){
                throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_REQUIRED_SUB_LEVEL);
            }

            if(!Objects.equals(billCategory.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.BILL_CATEGORY_NOT_SELF_ERROR);
            }
        }

        // 校验账户类型是否在表中存在，并且需要时二级账单分类
        if (template.getAccountTypeId() != null){
            AccountType accountType = this.accountTypeMapper.selectById(template.getAccountTypeId());

            if (accountType == null){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_EXISTS);
            }

            if (accountType.getParentId() == null){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_REQUIRED_SUB_LEVEL);
            }

            if(!Objects.equals(accountType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_TYPE_NOT_SELF_ERROR);
            }
        }

        // 校验成员类型是否在表中存在
        if (template.getMemberTypeId() != null){
            MemberType memberType = this.memberTypeMapper.selectById(template.getMemberTypeId());

            if (memberType == null){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(memberType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_SELF_ERROR);
            }
        }

        // 校验商家类型是否在表中存在
        if (template.getShopTypeId() != null){
            ShopType shopType = this.shopTypeMapper.selectById(template.getShopTypeId());

            if (shopType == null){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(shopType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_SELF_ERROR);
            }
        }

        // 校验项目类型是否在表中存在
        if (template.getProjectTypeId() != null){
            ProjectType projectType = this.projectTypeMapper.selectById(template.getProjectTypeId());

            if (projectType == null){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_EXISTS);
            }

            if(!Objects.equals(projectType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_SELF_ERROR);
            }
        }

        // 校验账本id是否存在
        if (template.getAccountBookId() != null){
            AccountBook accountBook = this.accountBookMapper.selectById(template.getAccountBookId());

            if (accountBook == null){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_EXISTS);
            }

            if(!Objects.equals(accountBook.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_SELF_ERROR);
            }
        }

        return this.save(template);
    }

    @Override
    public boolean updateData(UpdateTemplateDTO updateTemplateDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        Template template = this.getById(updateTemplateDTO.getId());
        if(template == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!template.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }


        // 校验账单分类是否在表中存在，并且需要是二级账单分类
        BillCategory billCategory = this.billCategoryMapper.selectById(updateTemplateDTO.getBillCategoryId());
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
        AccountType accountType = this.accountTypeMapper.selectById(updateTemplateDTO.getAccountTypeId());
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
        if (updateTemplateDTO.getMemberTypeId() != null) {
            MemberType memberType = this.memberTypeMapper.selectById(updateTemplateDTO.getMemberTypeId());
            if (memberType == null){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_EXISTS);
            }
            if(!Objects.equals(memberType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.MEMBER_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验商家类型是否在表中存在
        if (updateTemplateDTO.getShopTypeId() != null) {
            ShopType shopType = this.shopTypeMapper.selectById(updateTemplateDTO.getShopTypeId());
            if (shopType == null){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_EXISTS);
            }
            if(!Objects.equals(shopType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.SHOP_TYPE_NOT_SELF_ERROR);
            }
        }


        // 校验项目类型是否在表中存在
        if (updateTemplateDTO.getProjectTypeId() != null) {
            ProjectType projectType = this.projectTypeMapper.selectById(updateTemplateDTO.getProjectTypeId());
            if (projectType == null){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_EXISTS);
            }
            if(!Objects.equals(projectType.getUserId(), userId)){
                throw new FrontendErrorPromptException(ResultCode.PROJECT_TYPE_NOT_SELF_ERROR);
            }
        }


        // null值允许被更新，因为前端可以删除一些字段设置
        return this.lambdaUpdate()
                .eq(Template::getId, updateTemplateDTO.getId())
                .set(Template::getName, updateTemplateDTO.getName())
                .set(Template::getBillCategoryId, updateTemplateDTO.getBillCategoryId())
                .set(Template::getAccountTypeId, updateTemplateDTO.getAccountTypeId())
                .set(Template::getAmount, updateTemplateDTO.getAmount())
                .set(Template::getMemberTypeId, updateTemplateDTO.getMemberTypeId())
                .set(Template::getShopTypeId, updateTemplateDTO.getShopTypeId())
                .set(Template::getProjectTypeId, updateTemplateDTO.getProjectTypeId())
                .set(Template::getNotes, updateTemplateDTO.getNotes())
                .update();
    }

    @Override
    public boolean deleteById(long id) {
        Template template = this.getById(id);
        if (template == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!template.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }

    @Override
    public List<Template> listByAccountBookId(long accountBookId) {
        Template template = this.templateMapper.selectById(accountBookId);
        if (template == null) {
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_EXISTS);
        }
        if (!template.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.ACCOUNT_BOOK_NOT_SELF_ERROR);
        }

        return this.lambdaQuery()
                .eq(Template::getAccountBookId, accountBookId)
                .eq(Template::getUserId, ThreadLocalHelper.get().getId())
                .list();
    }
}
