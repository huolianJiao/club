package com.glue.club.web.dto;

import java.util.List;

import com.glue.club.web.model.User;

import lombok.Data;

/**
 * @author jiao
 */
@Data
public class AccessTokenDTO {

    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;
}
