package com.glue.club.web.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    private Long id;

    private String accountId;

    private String name;

    private String token;

    private Long gmtCreate;

    private Long gmtModified;

    private String bio;

    private String avatarUrl;

    private static final long serialVersionUID = 1L;
}