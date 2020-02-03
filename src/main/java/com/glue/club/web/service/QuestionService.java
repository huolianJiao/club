package com.glue.club.web.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.glue.club.web.model.Question;
import com.glue.club.web.dao.QuestionMapper;
@Service
public class QuestionService{

    @Resource
    private QuestionMapper questionMapper;

    
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

}
