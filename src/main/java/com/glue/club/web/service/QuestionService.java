package com.glue.club.web.service;

import com.glue.club.common.enums.SortEnum;
import com.glue.club.common.exception.CustomizeErrorCode;
import com.glue.club.common.exception.CustomizeException;
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
public class QuestionService {

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
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            insertSelective(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.updateByPrimaryKeySelective(question);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
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
        Question updateRecord = new Question();
        updateRecord.setViewCount(1);
        updateRecord.setId(id);
        int update = questionExtMapper.incView(updateRecord);
        if (update != 1) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }

    public List<QuestionDTO> selectRelated(QuestionDTO question) {
        String regexpTag = Arrays.asList(question.getTag().split(",")).stream().collect(Collectors.joining("|"));
        Question selectQuestion = new Question();
        selectQuestion.setId(question.getId());
        selectQuestion.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelated(selectQuestion);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
