package com.songsy.base;

import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 控制层抽象类
 * @author songshuiyang
 * @date 2018/2/10 21:55
 */
public abstract class BaseController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * page对象转map,同时加上分页属性
     * @param page
     * @param <T>
     * @return
     */
    protected <T> Map<String, Object> covertPageMap(Page<T> page) {
        Map<String, Object> pageMap = Maps.newHashMap();
        pageMap.put("data", page);
        pageMap.put("code", 00);
        // 总数
        pageMap.put("count", page.getTotal());
        // 总页数
        pageMap.put("pageCount", page.getPages());
        // 当前页数量
        pageMap.put("pageSize",page.getPageSize());
        // 当前是多少页
        pageMap.put("pageNum",page.getPageNum());
        pageMap.put("msg", "success");
        return pageMap;
    }

    /**
     * list转map对象
     * @param page
     * @param <T>
     * @return
     */
    protected <T> Map<String, Object> covertListMap(List<T> page) {
        Map<String, Object> pageMap = Maps.newHashMap();
        pageMap.put("data", page);
        pageMap.put("code", 00);
        pageMap.put("count", page.size());
        pageMap.put("msg", "success");
        return pageMap;
    }

    /**
     * 无权访问
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        Map<String, Object> accessDenied = Maps.newHashMap();
        accessDenied.put("code", 403);
        accessDenied.put("content", "没有权限访问当前页面");
        return accessDenied;
    }
}
