package com.icu.mybill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icu.mybill.pojo.MemberType;
import com.icu.mybill.service.MemberTypeService;
import com.icu.mybill.mapper.MemberTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author zhangjianzhong
* @description 针对表【member_type(成员类型表)】的数据库操作Service实现
* @createDate 2025-02-15 12:35:29
*/
@Service
public class MemberTypeServiceImpl extends ServiceImpl<MemberTypeMapper, MemberType>
    implements MemberTypeService{

}




