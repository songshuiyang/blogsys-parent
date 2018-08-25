package com.songsy.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 配置数据工具类
 * @author songshuiyang
 * @date 2018/1/20 20:44
 */
public class PropertisUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertisUtils.class);
    /**
     * 项目配置文件
     */
    private static final String APPLICATION_FILE_NAME = "project.properties";
    /**
     * project.properties 配置文件属性
     */
    public static Map<String, String> applicationProperties;

    /**
     * 于初始化类，为类的属性初始化。每个静态代码块只会执行一次。由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。
     * 如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
     */
    static {
        applicationProperties = loadProperties(APPLICATION_FILE_NAME);
        System.out.println("1");
    }
    /**
     * 根据文件路径获取参数属性
     *
     * @param filePath
     * @return
     */
    public static Map<String, String> loadProperties(String filePath) {
        Map<String, String> result = new HashMap<>();
        if (null == filePath || "".equals(filePath.trim())) {
            logger.error("The file path is null,return");
            return result;
        }
        filePath = filePath.trim();
        // 获取资源文件
        InputStream is = PropertisUtils.class.getClassLoader().getResourceAsStream(filePath);
        // 属性列表
        Properties prop = new Properties();
        try {
            // 从输入流中读取属性列表
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("load file faile. filePath: " +  filePath + e);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("load file faile." + e);
            return result;
        }
        // 返回Properties中包含的key-value的Set视图
        Set<Map.Entry<Object, Object>> set = prop.entrySet();
        // 返回在此Set中的元素上进行迭代的迭代器
        Iterator<Map.Entry<Object, Object>> it = set.iterator();
        String key, value;
        // 循环取出key-value
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            key = String.valueOf(entry.getKey());
            value = String.valueOf(entry.getValue());
            // 将key-value放入map中
            result.put(key, value);
        }
        return result;
    }

    public static void main(String[] args) {
        loadProperties("project.properties");
    }
}
