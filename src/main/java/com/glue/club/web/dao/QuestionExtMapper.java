package com.glue.club.web.dao;

import com.glue.club.web.dto.QuestionQueryDTO;
import com.glue.club.web.model.Question;

import java.util.List;

/**
 * @author jiao
 */
public interface QuestionExtMapper {

    int incView(Question record);

    int incComment(Question record);

    List<Question> selectRelated(Question record);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}