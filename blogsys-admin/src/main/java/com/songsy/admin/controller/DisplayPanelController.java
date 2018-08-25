package com.songsy.admin.controller;

import com.songsy.admin.dto.BlogArticlesDTO;
import com.songsy.admin.dto.BlogArticlesTypeDTO;
import com.songsy.admin.dto.EchartsDTO;
import com.songsy.admin.entity.BlogArticles;
import com.songsy.admin.entity.LoginHistory;
import com.songsy.admin.service.BlogArticlesService;
import com.songsy.admin.service.LoginHistoryService;
import com.songsy.core.utils.DateUtils;
import com.songsy.core.utils.DigitUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

import static com.songsy.core.utils.DateUtils.FORMAT_SHORT;

/**
 * 展示面板控制器
 * @author songshuiyang
 * @date 2018/4/2 22:08
 */
@Controller
@RequestMapping("/displayPanel")
public class DisplayPanelController {

    @Autowired
    private BlogArticlesService blogArticlesService;

    @Autowired
    private LoginHistoryService historyService;

    /**
     * 加载首页页面
     * @param model
     * @return
     */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/displayPanel";
    }

    /**
     * 获取博客文章分类
     * @return list
     */
    @RequestMapping(value = "getBlogArticlesType", method = RequestMethod.GET)
    @ResponseBody
    public List<BlogArticlesTypeDTO> getBlogArticlesType() throws Exception {
        return blogArticlesService.getArticlesTypeCount();
    }

    /**
     * 获取博客文章归档
     * @return list
     */
    @RequestMapping(value = "getBlogArticlesCategory", method = RequestMethod.GET)
    @ResponseBody
    public List<EchartsDTO> getBlogArticlesCategory() throws Exception {
        return blogArticlesService.getBlogArticlesCategoryCount();
    }

    /**
     * 获取文章月均数统计
     * @return list
     */
    @RequestMapping(value = "getBlogArticlesMonthlyNum", method = RequestMethod.GET)
    @ResponseBody
    public List<EchartsDTO> getBlogArticlesMonthlyNum(@RequestParam(required = false, defaultValue = "2018") Integer yearNum) throws Exception {
        List<EchartsDTO> result = new ArrayList<>();
        if (yearNum == LocalDate.now().getYear()) {
            for (int i = 0; i < LocalDate.now().getMonthValue(); i++) {
                EchartsDTO echartsDTO = new EchartsDTO();
                BlogArticlesDTO blogArticlesDTO = new BlogArticlesDTO();
                blogArticlesDTO.setStartDate(DateUtils.getBeginOfMonthTime(yearNum,i));
                blogArticlesDTO.setEndDate(DateUtils.getEndOfMonthTime(yearNum,i));
                List<BlogArticles> blogArticles = blogArticlesService.findBlogArticlesByParam(blogArticlesDTO);
                int mounth = i + 1;
                echartsDTO.setName(DigitUtils.toHanStr(mounth)+ "月");
                echartsDTO.setValue(blogArticles.size());
                result.add(echartsDTO);
            }
        } else {
            for (int n = 0; n < 12; n++) {
                EchartsDTO echartsDTO = new EchartsDTO();
                BlogArticlesDTO blogArticlesDTO = new BlogArticlesDTO();
                blogArticlesDTO.setStartDate(DateUtils.getBeginOfMonthTime(yearNum,n));
                blogArticlesDTO.setEndDate(DateUtils.getEndOfMonthTime(yearNum,n));
                List<BlogArticles> blogArticles = blogArticlesService.findBlogArticlesByParam(blogArticlesDTO);
                int mounth = n + 1;
                echartsDTO.setName(DigitUtils.toHanStr(mounth)+ "月");
                echartsDTO.setValue(blogArticles.size());
                result.add(echartsDTO);
            }
        }
        return result;
    }
    /**
     * 获取博客日迹
     * @return list
     */
    @RequestMapping(value = "getblogArticlesCalendar", method = RequestMethod.GET)
    @ResponseBody
    public List<String[]> getblogArticlesCalendar() throws Exception {
        List<String[]> result = new ArrayList<>();
        List<BlogArticles> record  = blogArticlesService.findBlogArticlesByParam(new BlogArticlesDTO());
        // 用于存放所出现的日期
        ArrayList<String> cityList = new ArrayList<>();
        for (BlogArticles blogArticles : record) {
            Date createdDate = blogArticles.getCreatedDate();
            String createdDateStr = DateUtils.format(createdDate,FORMAT_SHORT);
            cityList.add(createdDateStr);
        }
        // 统计字符数组中字符出现的次数
        List<Map<String, Object>> maps;
        int size=cityList.size();
        String[] arrString =cityList.toArray(new String[size]);
        maps = findMostFrequentOccurrences(arrString);
        for (Map<String, Object> map: maps) {
            String [] s1 = new String[]{(String)map.get("name"),String.valueOf(map.get("value"))};
            result.add(s1);
        }
        return result;
    }

    /**
     * 获取用户登录地分布
     * @return list
     */
    @RequestMapping(value = "getAddressMap", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAddressMap() throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> data;

        List<LoginHistory> addressInfo = historyService.getAddressInfo();
        // 用于存放所出现的城市
        ArrayList<String> cityList = new ArrayList<>();
        for (LoginHistory loginHistory: addressInfo) {
            String city = loginHistory.getIpLocationCity();
            // 如果城市字段为空不返回数据
            if (StringUtils.isNotBlank(city)){
                // 城市名称需要去掉市, 需与Echats城市名称匹配
                city = city.replace("市","");
                cityList.add(city);
            }
        }
        // 统计字符数组中字符出现的次数
        int size=cityList.size();
        String[] arrString =cityList.toArray(new String[size]);
        data = findMostFrequentOccurrences(arrString);
        resultMap.put("data",data);
        return resultMap;
    }
    /**
     * 统计字符数组中字符出现的次数
     * @param strings 字符数组
     * @return list
     */
    private List<Map<String, Object>>  findMostFrequentOccurrences (String [] strings){
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        // 统计出现的次数
        for(String s : strings){
            if(map.containsKey(s)){
                int x = map.get(s);
                x++;
                map.put(s, x);
            }else{
                map.put(s, 1);
            }
        }

        for (String key : map.keySet()) {
            Integer value = map.get(key);
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("name", key);
            itemMap.put("value", value);
            resultList.add(itemMap);
        }
        return resultList;
    }
    /**
     * 获取Echarts china.json文件
     *
     * @return list
     */
    @RequestMapping(value = "getChinaMapJson", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getChinaMapJson(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        BufferedReader reader;
        StringBuilder laststr = new StringBuilder();
        String strDirPath = request.getSession().getServletContext().getRealPath("/");
        try(FileInputStream fileInputStream = new FileInputStream(strDirPath+"/static/plugins/echarts/china.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8")) {
            reader = new BufferedReader(inputStreamReader);
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                laststr.append(tempString);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        result.put("json", laststr.toString());
        return result;
    }

    public static void main(String[] args) {
    }


}
