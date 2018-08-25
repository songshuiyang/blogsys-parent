package com.songsy.web.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author songsy
 * @Package com.songsy.web.test
 * @Title: 测试基类
 * @Description: junit启动时加载springIOC容器 spring-test,junit
 * @date 2017/11/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/applicationContext.xml"})
public class BaseTest {

}
