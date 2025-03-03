package com.icu.mybill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.dto.attachment.AttachmentBaseDTO;
import com.icu.mybill.dto.attachment.BindAttachmentDTO;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.common.FrontendErrorPromptException;
import com.icu.mybill.mapper.BillMapper;
import com.icu.mybill.pojo.Attachment;
import com.icu.mybill.pojo.Bill;
import com.icu.mybill.service.AttachmentService;
import com.icu.mybill.mapper.AttachmentMapper;
import com.icu.mybill.util.ThreadLocalHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【attachment(附件表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment>
    implements AttachmentService{

    @Value("${com.icu.mybill.one-bill-attach-max-size}")
    private int oneBillAttachMaxSize;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private BillMapper billMapper;

    @Override
    public boolean updateSort(List<UpdateSortDTO> list) {
        Long userId = ThreadLocalHelper.get().getId();

        List<Attachment> queryList = this.lambdaQuery()
                // .eq(Attachment::getUserId, userId)
                .in(Attachment::getId, list.stream().map(UpdateSortDTO::getId).toList())
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

        return this.attachmentMapper.updateSort(userId, list);
    }

    @Override
    @Transactional
    public boolean bindToBill(BindAttachmentDTO bindAttachmentDTO){
        Long userId = ThreadLocalHelper.get().getId();

        Bill bill = this.billMapper.selectById(bindAttachmentDTO.getBillId());
        if (bill == null) {
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!bill.getUserId().equals(userId)) {
            throw new FrontendErrorPromptException(ResultCode.CREATE_DATA_NOT_SELF_ERROR);
        }

        // 校验本账单中附件总数量是否未超出最大范围
        int count = Math.toIntExact(this.lambdaQuery().eq(Attachment::getBillId, bill.getId()).count());
        if(count >= oneBillAttachMaxSize){
            throw new FrontendErrorPromptException(ResultCode.ATTACHMENT_ONE_BILL_EXCEED_MAX_SIZE_ERROR);
        }

        List<AttachmentBaseDTO> dtoList = bindAttachmentDTO.getList().subList(0, oneBillAttachMaxSize - count);
        List<Attachment> attachmentList = BeanUtil.copyToList(dtoList, Attachment.class);
        attachmentList.forEach(item -> {
            item.setBillId(bindAttachmentDTO.getBillId());
            item.setUserId(userId);
        });
        return this.saveBatch(attachmentList);
    }

    @Override
    public List<Attachment> findListByBillId(long billId) {
        Bill bill = this.billMapper.selectById(billId);

        if (bill == null){
            throw new FrontendErrorPromptException(ResultCode.NOT_QUERY_NEED_OPERATE_DATA_ERROR);
        }
        if (!bill.getUserId().equals(ThreadLocalHelper.get().getId())){
            throw new FrontendErrorPromptException(ResultCode.QUERY_DATA_NOT_SELF_ERROR);
        }

        return this.lambdaQuery()
                .eq(Attachment::getBillId, billId)
                .list();
    }
}




