package com.glue.club.web.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Question implements Serializable {
    private Long id;

    private String title;

    private String description;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private Long creator;

    private static final long serialVersionUID = 1L;
}