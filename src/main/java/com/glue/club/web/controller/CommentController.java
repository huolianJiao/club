package com.glue.club.web.controller;

import com.glue.club.common.enums.CommentTypeEnum;
import com.glue.club.common.exception.CustomizeErrorCode;
import com.glue.club.common.util.UserUtil;
import com.glue.club.web.dto.CommentCreateDTO;
import com.glue.club.web.dto.CommentDTO;
import com.glue.club.web.dto.ResultDTO;
import com.glue.club.web.model.Comment;
import com.glue.club.web.model.User;
import com.glue.club.web.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiao
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentDTO) {
        User user = UserUtil.getUser();
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment record = new Comment();
        BeanUtils.copyProperties(commentDTO, record);
        record.setGmtCreate(System.currentTimeMillis());
        record.setGmtModified(record.getGmtCreate());
        record.setCommentator(user.getId());
        int result = commentService.insertSelective(record);
        if (result != 1){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_FAIL);
        }
        return ResultDTO.successOf("评论成功");
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public String comments(@PathVariable("id") Long id, Model model){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        model.addAttribute("subComment",commentDTOS);
        return "question::sub-comment-refresh";
    }

}
