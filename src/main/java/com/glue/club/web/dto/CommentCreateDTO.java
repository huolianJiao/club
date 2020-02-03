package com.glue.club.web.dto;

import lombok.Data;

/**
 * @author jiao
 */
@Data
public class CommentCreateDTO {

    private Long parentId;

    private String content;

    private Integer type;

}
