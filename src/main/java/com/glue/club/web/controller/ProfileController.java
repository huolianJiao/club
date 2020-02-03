package com.glue.club.web.controller;

import com.glue.club.common.util.UserUtil;
import com.glue.club.web.model.User;
import com.glue.club.web.service.NotificationService;
import com.glue.club.web.service.QuestionService;
import com.glue.club.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jiao
 */
@Controller
public class ProfileController {

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    @Resource
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = UserUtil.getUser();
        if (user == null) {
            return "redirect:/";
        }
        /*改造为pagehelper实现*/
        if ("questions".equals(action)) {
//            PaginationDTO pagination = questionService.list(user.getId(), page, size);
//            model.addAttribute("pagination", pagination);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
        } else if ("replies".equals(action)) {
//            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
//            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }

}
