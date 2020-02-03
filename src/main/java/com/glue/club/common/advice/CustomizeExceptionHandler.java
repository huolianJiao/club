package com.glue.club.common.advice;

import com.alibaba.fastjson.JSON;
import com.glue.club.common.constant.Const;
import com.glue.club.common.exception.CustomizeErrorCode;
import com.glue.club.common.exception.CustomizeException;
import com.glue.club.web.dto.ResultDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jiao
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request,
                                     Throwable ex, Model model,
                                     HttpServletResponse response) {
        String contentType = request.getContentType();
        if (Const.SITE_ERROR_TYPE_JSON.equals(contentType)) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf(((CustomizeException) ex));
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType(Const.SITE_ERROR_TYPE_JSON);
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ((CustomizeException) ex).getCode() + ":" + ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

}
