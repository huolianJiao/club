package com.glue.club.web.dto;

import com.glue.club.common.exception.CustomizeErrorCode;
import com.glue.club.common.exception.CustomizeException;
import lombok.Data;

/**
 * @author jiao
 */
@Data
public class ResultDTO<T> {

    private Integer code;

    private String message;

    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO successOf(String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static <T> ResultDTO successOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException exception) {
        return errorOf(exception.getCode(), exception.getMessage());
    }
}
