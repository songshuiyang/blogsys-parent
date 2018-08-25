package com.songsy.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring 工具类
 *
 * ApplicationContextAware:
 *      实现该接口的setApplicationContext(ApplicationContext context)方法，
 *      并保存ApplicationContext 对象。Spring初始化时，会通过该方法将ApplicationContext对象注入。
 * DisposableBean
 *      DisposableBean 接口实现销毁前操作
 * @author songshuiyang
 * @date 2018/3/11 21:48
 */
@Service
public class SpringContextUtils implements ApplicationContextAware, DisposableBean{

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        applicationContext=null;
    }
}
