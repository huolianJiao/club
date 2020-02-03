package com.glue.club.web.service;

import com.github.pagehelper.PageHelper;
import com.glue.club.common.enums.SortEnum;
import com.glue.club.web.dao.QuestionExtMapper;
import com.glue.club.web.dao.UserMapper;
import com.glue.club.web.dto.QuestionDTO;
import com.glue.club.web.dto.QuestionQueryDTO;
import com.glue.club.web.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.glue.club.web.model.Question;
import com.glue.club.web.dao.QuestionMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiao
 */
@Service
public class QuestionService{

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionExtMapper questionExtMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return questionMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Question record) {
        return questionMapper.insert(record);
    }

    
    public int insertSelective(Question record) {
        return questionMapper.insertSelective(record);
    }

    
    public Question selectByPrimaryKey(Long id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Question record) {
        return questionMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Question record) {
        return questionMapper.updateByPrimaryKey(record);
    }

    public QuestionDTO getById(Long id) {
        return null;
    }

    public void createOrUpdate(Question question) {

    }

    public List<QuestionDTO> getPageList(String search, String tag, String sort) {

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays
                    .stream(tags)
                    .filter(StringUtils::isNotBlank)
                    .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("|"));
        }

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }

        for (SortEnum sortEnum : SortEnum.values()) {
            if (sortEnum.name().toLowerCase().equals(sort)) {
                questionQueryDTO.setSort(sort);

                if (sortEnum == SortEnum.HOT7) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
                }
                if (sortEnum == SortEnum.HOT30) {
                    questionQueryDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
                }
                break;
            }
        }

        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }

    public void incView(Long id) {

    }

    public List<QuestionDTO> selectRelated(QuestionDTO question) {
        return null;
    }
}
