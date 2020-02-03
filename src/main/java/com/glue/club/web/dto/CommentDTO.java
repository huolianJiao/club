package com.glue.club.web.dto;

import com.glue.club.web.model.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author jiao
 */
@Data
public class CommentDTO extends Comment {
    private String name;

    private String avatarUrl;

}
