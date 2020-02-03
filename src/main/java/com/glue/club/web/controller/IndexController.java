package com.glue.club.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.glue.club.web.dto.QuestionDTO;
import com.glue.club.web.service.QuestionService;
import com.glue.club.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiao
 */
@Controller
public class IndexController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort) {
        /*改造为pagehelper实现*/
        PageHelper.startPage(page, size);
        List<QuestionDTO> list = questionService.getPageList(search, tag, sort);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>(list);
        model.addAttribute("pagination",pageInfo);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("sort", sort);
        return "index";
    }

}
