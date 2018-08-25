package com.songsy.core.utils;

import com.songsy.admin.common.CommonConstant;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Mvc控制层消息
 * @author songshuiyang
 * @date 2018/2/10 21:51
 */
public class MessageUtils {

    public static Map<String,Object> success(){
        return success("操作成功");
    }

    public static Map<String,Object> success(String content){
        Map<String,Object> success = Maps.newHashMap();
        success.put("code", CommonConstant.RESULT_STATUS_SUCCESS_CODE);
        success.put("content", content);
        success.put("level", "success");
        return success;
    }

    public static Map<String,Object> fail(String content){
        Map<String,Object> error = Maps.newHashMap();
        error.put("code", CommonConstant.RESULT_STATUS_FAIL_CODE);
        error.put("content", content);
        error.put("level", "danger");
        return error;
    }

    public static Map<String,Object> exception(String content){
        Map<String,Object> exception = Maps.newHashMap();
        exception.put("code", CommonConstant.RESULT_STATUS_EXCEPTION_CODE);
        exception.put("content", content);
        exception.put("level", "danger");
        return exception;
    }

    public static Map<String, Object> accessDenied() {
        Map<String, Object> accessDenied = Maps.newHashMap();
        accessDenied.put("code", 401);
        accessDenied.put("content", "当前不处于登录状态");
        return accessDenied;
    }

    public static Map<String, Object> accessUnauthorized() {
        Map<String, Object> accessDenied = Maps.newHashMap();
        accessDenied.put("code", 403);
        accessDenied.put("content", "没有权限访问当前页面");
        return accessDenied;
    }
}
