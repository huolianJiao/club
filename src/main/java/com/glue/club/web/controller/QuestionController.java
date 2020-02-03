package com.glue.club.web.controller;

import com.glue.club.common.enums.CommentTypeEnum;
import com.glue.club.web.dto.CommentDTO;
import com.glue.club.web.dto.QuestionDTO;
import com.glue.club.web.service.CommentService;
import com.glue.club.web.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiao
 */
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
        //累加阅读数
        questionService.incView(id);
        QuestionDTO question = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(question);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question",question);
        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("comments",commentDTOS);
        return "question";
    }

}
