package com.icu.mybill.controller;

import cn.hutool.core.bean.BeanUtil;
import com.icu.mybill.common.Result;
import com.icu.mybill.dto.attachment.BindAttachmentDTO;
import com.icu.mybill.dto.common.UpdateSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.pojo.Attachment;
import com.icu.mybill.service.AttachmentService;
import com.icu.mybill.vo.attachment.AttachmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("attach")
// 接口分组名称和分组描述
@Tag(name = "附件", description = "所有和附件相关的接口")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    /**
     * 绑定附件信息到账单
     *
     * @param bindAttachmentDTO
     * @return
     */
    @PostMapping("bind")
    // 接口名和接口描述
    @Operation(summary = "绑定附件信息到账单", description = "绑定附件信息到账单")
    public Result<Boolean> bind(
            // 接口参数描述
            @Parameter(description = "要绑定附件信息到账单的对象", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Validated BindAttachmentDTO bindAttachmentDTO
    ) {
        boolean result = attachmentService.bindToBill(bindAttachmentDTO);

        return Result.ok(result);
    }

    /**
     * 通过账单id获取账单的所有附件列表
     *
     * @return
     */
    @GetMapping("list")
    @Operation(summary = "通过账单id获取账单的所有附件列表", description = "通过账单id获取账单的所有附件列表")
    public Result<List<AttachmentVO>> list(
            @Parameter(description = "账单id", required = true)
            @RequestParam(value = "billId")
            Long billId
    ) {
        List<Attachment> list = this.attachmentService.findListByBillId(billId);
        List<AttachmentVO> memberTypeVOS = BeanUtil.copyToList(list, AttachmentVO.class);

        return Result.ok(memberTypeVOS);
    }

    @PutMapping("sort")
    @Operation(summary = "更新附件排序", description = "更新附件排序")
    public Result<Boolean> updateSort(
            @Parameter(description = "更新附件排序", required = true)
            // 使用@Valid校验List中的DTO对象字段，因为@Validation只会校验参数类型的DTO字段，不会校验List中的
            @RequestBody @Valid List<UpdateSortDTO> list
    ) {
        if (list.isEmpty()) {
            return Result.fail(ResultCode.UPDATE_SORT_LIST_EMPTY_ERROR);
        }

        Boolean result = attachmentService.updateSort(list);

        return Result.ok(result);
    }
}
