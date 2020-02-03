package com.glue.club.web.controller;

import com.glue.club.common.cache.TagCache;
import com.glue.club.common.util.UserUtil;
import com.glue.club.web.dto.QuestionDTO;
import com.glue.club.web.model.Question;
import com.glue.club.web.model.User;
import com.glue.club.web.service.QuestionService;
import com.glue.club.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jiao
 */
@Controller

public class PublishController {

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String publish(Model model,
                          @PathVariable("id") Long id) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(Question question, HttpServletRequest request, Model model) {
        model.addAttribute("question", question);
        model.addAttribute("tags", TagCache.get());
        if (StringUtils.isEmpty(question.getTitle())) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        } else if (StringUtils.isEmpty(question.getDescription())) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        } else if (StringUtils.isEmpty(question.getTag())) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(question.getTag());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }

        User user = UserUtil.getUser();
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

}
