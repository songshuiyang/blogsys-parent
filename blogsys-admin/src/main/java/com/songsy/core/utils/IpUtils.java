package com.songsy.core.utils;

import com.songsy.admin.entity.Address;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户id工具类
 * @author songshuiyang
 * @date 2018/3/16 23:12
 */
public class IpUtils {

    private final static Logger logger = LoggerFactory.getLogger(IpUtils.class);


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
//        if (!isIpv4(ip)) {
//            ip= "000.000.000.000";
//        }
        return ip;
    }
    /**
     * 校验IP地址
     * @param ipAddress IP 地址
     * @return true or false
     */
    public static boolean isIpv4(String ipAddress) {
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }


    /**
     * 调用 api.map.baidu.com/location/ip 获取城市信息
     * @param ip
     * @return
     * @throws IOException
     */
    private static String getCityInfoByUrlAPI(String ip) throws IOException {
        String url = "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip;
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = getStrByReader(rd);
            jsonText = decodeUnicode(jsonText);
            return jsonText;
        }
    }
    public static String getCityInfoStrByIp(String ip) {
        return getCityInfoByIp(ip).toString();
    }
    /**
     * 根据ip获取城市信息
     * @param ip
     * @return
     */
    public static Address getCityInfoByIp(String ip){
        Address result = new Address();
        result.setStatus("success");
        String jsonInfo = null;
        try {
            jsonInfo = getCityInfoByUrlAPI(ip);
        } catch (IOException e) {
            logger.error("调用 api.map.baidu.com/location/ip 获取城市信息异常, ip:" + ip, e);
            result.setStatus("failed");
            return  result;
        }
        try {
            logger.info("Json" + JsonUtils.formatJson(jsonInfo));
            JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
            if (jsonObject != null) {
                if (jsonObject.get("address") != null) {
                    result.setHeadInfo((String)jsonObject.get("address"));
                }
                if (jsonObject.getJSONObject("content") != null) {
                    JSONObject addressDetail = jsonObject.getJSONObject("content").getJSONObject("address_detail");
                    // 省
                    result.setProvince((String)addressDetail.get("province"));
                    // 市
                    result.setCity((String)addressDetail.get("city"));
                    // 区
                    result.setDistrict((String)addressDetail.get("district"));
                    // 街道
                    result.setStreet((String)addressDetail.get("street"));
                    // 街道号
                    result.setStreetNumber((String)addressDetail.get("street_number"));
                    // 城市code
                    result.setCityCode((Integer) addressDetail.get("city_code"));

                    JSONObject point = jsonObject.getJSONObject("content").getJSONObject("point");
                    result.setPointX((String)point.get("x"));
                    result.setPointY((String)point.get("y"));
                }
            }
        } catch (Exception e) {
            logger.error("调用 api.map.baidu.com/location/ip 获取城市信息异常,解析JSON异常 ip:" + ip, e);
            result.setStatus("failed");
            return  result;
        }
        return result;
    }
    /**
     * unicode 转换成 中文
     *
     * @author fanhui 2007-3-15
     * @param theString 字符串
     * @return String
     */
    private static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuilder outBuilder = new StringBuilder(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuilder.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuilder.append(aChar);
                }
            } else {
                outBuilder.append(aChar);
            }
        }
        return outBuilder.toString();
    }
    /**
     * 获取流数据
     * @param rd
     * @return
     * @throws IOException
     */
    private static String getStrByReader(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    // 测试
    public static void main(String[] args) throws Exception {
        System.out.println(getCityInfoStrByIp("121.76.19.83"));
       // System.out.println(getCityInfoByIp("121.76.19.83"));
//        AddressUtils addressUtils = new AddressUtils();
    }
}
