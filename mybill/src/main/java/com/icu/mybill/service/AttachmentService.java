package com.icu.mybill.service;

import com.icu.mybill.dto.attachment.BindAttachmentDTO;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.pojo.Attachment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zhangjianzhong
* @description 针对表【attachment(附件表)】的数据库操作Service
* @createDate 2025-02-15 12:35:29
*/
public interface AttachmentService extends IService<Attachment> {
    /**
     * 更新排序
     * @param list
     * @return
     */
    boolean updateSort(List<UpdateSortDTO> list);

    boolean bindToBill(BindAttachmentDTO bindAttachmentDTO);

    List<Attachment> findListByBillId(long billId);
}
