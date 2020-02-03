package com.glue.club.web.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.glue.club.web.dao.CommentMapper;
import com.glue.club.web.model.Comment;
@Service
public class CommentService{

    @Resource
    private CommentMapper commentMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    
    public Comment selectByPrimaryKey(Long id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

}
