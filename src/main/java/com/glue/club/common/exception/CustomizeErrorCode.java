package com.glue.club.common.exception;

/**
 * @author jiao
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"这个问题服务器没有找到，请查看其他的问题"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"您还没有登录哦!"),
    COMMENT_FAIL(2004,"你的评论失败了，请换个姿势再次尝试"),
    SYS_ERROR(2005,"服务器冒烟了，请稍后再进行尝试！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在了，换一个吧"),
    CONTENT_IS_EMPTY(2007, "请输入你要回复的内容"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    ;
    private String message;

    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
