package com.songsy.core.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author songshuiyang
 * @title: ID 生成工具类
 * @description:
 * @date 2017/12/11 21:35
 */
public class IDGenerator {
    /**
     * 生成主键（32位）
     *
     * @return
     */
    public static String generateID() {
        return generateID(System.currentTimeMillis());
    }

    /**
     * 根据指定时间生成主键
     *
     * @param time
     * @return
     */
    public static String generateID(long time) {
        String rtnVal = Long.toHexString(time);
        rtnVal += UUID.randomUUID();
        rtnVal = rtnVal.replaceAll("-", "");
        return rtnVal.substring(0, 32);
    }

    /**
     * 根据ID获取该ID创建的时间
     *
     * @param id
     * @return
     */
    public static Date getIDCreateTime(String id) {
        String timeInfo = id.substring(0, 11);
        return new Date(Long.parseLong(timeInfo, 16));
    }

    /**
     * 获取SnCode
     *
     * @return
     */
    public static String getSnCode() {
        // 默认当前系统时间对应的相对时间有关的数字作为种子数
        Random random = new Random();
        Long current = Long.valueOf(DateFormatUtils.format(new Date(), "yyMMddHHmmssSSS") + "00");
        return String.valueOf((current + random.nextInt(99)));
    }

    public static void main(String[] args) {
        System.out.println("获取SnCode:             " + getSnCode());
        System.out.println("生成主键（32位）:        " + generateID());
        System.out.println("根据ID获取该ID创建的时间: " + DateFormatUtils.format(getIDCreateTime("16045d17c37284955f251594d9daa407"), "yy-MM-dd HH:mm:ss SSS"));


    }
}
