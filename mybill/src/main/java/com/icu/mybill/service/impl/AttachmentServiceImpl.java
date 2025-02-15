package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.Attachment;
import com.icu.mybill.service.AttachmentService;
import com.icu.mybill.mapper.AttachmentMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【attachment(附件表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment>
    implements AttachmentService{

}




