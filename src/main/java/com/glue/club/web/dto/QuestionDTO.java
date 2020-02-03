package com.glue.club.web.dto;

import com.glue.club.web.model.Question;
import com.glue.club.web.model.User;
import lombok.Data;

/**
 * @author jiao
 */
@Data
public class QuestionDTO extends Question {

    private User user;

}
