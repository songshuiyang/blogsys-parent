package com.songsy.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 数字工具类
 * @author songshuiyang
 * @date 2018/4/5 1:05
 */
public class DigitUtils {


    private static String[] hanArr = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static String[] unitArr = {"十", "百", "千", "万", "十", "白", "千", "亿", "十", "百", "千"};
    /**
     * 数字转中文
     *
     * @return
     */
    public static int countIdsNum(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return 0;
        }
        return StringUtils.split(ids, ",").length;
    }



    public static String toHanStr(Integer number) {
        String numStr = number + "";
        String result = "";
        int numLen = numStr.length();
        for (int i = 0; i < numLen; i++) {
            int num = numStr.charAt(i) - 48;
            if (i != numLen - 1 && num != 0) {
                result += hanArr[num] + unitArr[numLen - 2 - i];
                if (number >= 10 && number < 20) {
                    result = result.substring(1);
                }
            } else {
                if (!(number >= 10 && number % 10 == 0)) {
                    result += hanArr[num];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            System.out.println(toHanStr(i));
        }
    }
}
