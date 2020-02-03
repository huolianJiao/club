package com.glue.club.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author jiao
 */
public class ControllerUtil {

    private ControllerUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 判断是否为Ajav请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){

        if (!"XMLHttpRequest" .equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return false;
        }
        return true;
    }

    /**
     * post请求
     * @return
     */
    public static  boolean isPost() {
        HttpServletRequest request = getHttpServletRequest();
        String requersMethod = request.getMethod();
        if (requersMethod.equals("POST") || "POST".equals(requersMethod)) {
            return true;
        }
        return false;
    }

    /**
     * get请求
     *
     * @return
     */
    public static  boolean isGet() {
        HttpServletRequest request = getHttpServletRequest();
        String requersMethod = request.getMethod();
        if (requersMethod.equals("GET") || "GET".equals(requersMethod)) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求域名，域名不包括http请求协议头
     *
     * @return 返回域名地址
     *
     */
    public static  String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        String path =  request.getContextPath();
        String domain = request.getServerName();
        if (request.getServerPort() == 80) {
            domain += path;
        } else {
            domain += ":" + request.getServerPort() + path;
        }
        return domain;
    }

    /**
     * 读取服务器主机ip信息
     *
     * @return 返回主机IP，异常将会获取不到ip
     */
    public static  String getHostIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostAddress().toString();// 获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     * 获取请求客户端ip
     *
     * @param request
     *
     * @return ip地址
     *
     */
    public static  String getRemoteAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static HttpServletRequest getHttpServletRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }

    public static HttpServletResponse getHttpServletResponse(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        return response;
    }


    public static HttpSession getHttpSession(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getSession();
    }
}
