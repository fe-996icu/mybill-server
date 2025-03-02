package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.accountbook.UpdateAccountBookDTO;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.AccountBookMapper;
import com.icu.mybill.pojo.AccountBook;
import com.icu.mybill.query.BasePageQuery;
import com.icu.mybill.service.AccountBookService;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【account_book(账本表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AccountBookServiceImpl extends ServiceImpl<AccountBookMapper, AccountBook>
    implements AccountBookService{

    @Autowired
    private AccountBookMapper accountBookMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<AccountBook> queryList = this.lambdaQuery()
                // .eq(AccountBook::getUserId, userId)
                .in(AccountBook::getId, list.stream().map(UpdateSortDTO::getId).toList())
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

        return this.accountBookMapper.updateSort(userId, list);
    }

    @Override
    public boolean saveOne(AccountBook accountBook) {
        Long userId = ThreadLocalHelper.get().getId();

        accountBook.setUserId(userId);

        return this.save(accountBook);
    }

    @Override
    public boolean updateData(UpdateAccountBookDTO updateAccountBookDTO) {
        Long userId = ThreadLocalHelper.get().getId();

        AccountBook accountBook = this.getById(updateAccountBookDTO.getId());

        if(accountBook == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!accountBook.getUserId().equals(userId)){
            throw new FrontendErrorPromptException(ResultCode.UPDATE_DATA_NOT_SELF_ERROR);
        }

        BeanUtil.copyProperties(accountBook, updateAccountBookDTO);

        return this.updateById(accountBook);
    }

    @Override
    public boolean deleteById(long id) {
        AccountBook accountBook = this.getById(id);

        if (accountBook == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }

        if (!accountBook.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.DELETE_DATA_NOT_SELF_ERROR);
        }

        return this.removeById(id);
    }

    @Override
    public Page<AccountBook> pageQuery(BasePageQuery query) {
        // 组合分页查询条件
        Page<AccountBook> page = query.toPage();

        // 查询
        this.lambdaQuery().eq(AccountBook::getUserId, ThreadLocalHelper.get().getId())
                .orderByAsc(AccountBook::getSort)
                .page(page);

        return page;
    }

}




