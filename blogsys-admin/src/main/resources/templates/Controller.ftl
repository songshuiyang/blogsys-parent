package ${controller_package};

import ${entity_packege}.${entity_name};
import ${mapper_packege}.${entity_name}Mapper;
import ${service_packege}.${entity_name}Service;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import com.songsy.base.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* ${table_annotation} controller 控制层
* @author ${author}
* @date ${date}
*/
@Controller
@RequestMapping("/${entity_name?uncap_first}")
public class ${entity_name}Controller extends BaseController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ${entity_name}Service ${entity_name?uncap_first}Service;


    /**
    * 加载${table_annotation} 列表查询页面
    * @param model
    * @return
    */
    @RequestMapping(value = "loadListPage")
    public String loadListPage(Model model) {
        return "admin/${entity_name?uncap_first}/${entity_name?uncap_first}List";
    }
    /**
    * 分页查询
    *
    * @param pageNum
    * @param pageSize
    * @param ${entity_name?uncap_first}
    * @return
    */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> findPageList(@RequestParam("pageIndex") int pageNum, @RequestParam("pageSize") int pageSize, ${entity_name}DTO ${entity_name?uncap_first}) {
        Map<String, Object> map = new HashMap<>();
        ${entity_name?uncap_first}.setEnable(true);
        Page<${entity_name}> record = ${entity_name?uncap_first}Service.findPageList(pageNum, pageSize, ${entity_name?uncap_first});
        return covertPageMap(record);
    }
    
    /**
    * 加载新增${table_annotation}界面
    * @param model
    * @return
    */
    @RequestMapping(value = "load${entity_name}AddPage")
    public String ${entity_name?uncap_first}Add(Model model) {
        return "admin/${entity_name?uncap_first}/${entity_name?uncap_first}Add";
    }
    /**
    * 新增${table_annotation}
    * @param ${entity_name?uncap_first}DTO
    * @return
    */
    @RequestMapping(value = "/add${entity_name}", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> add${entity_name}(${entity_name}DTO ${entity_name?uncap_first}DTO) {
        ${entity_name} ${entity_name?uncap_first} = new ${entity_name}();
        BeanUtils.copyProperties(${entity_name?uncap_first}DTO, ${entity_name?uncap_first});
        if (${entity_name?uncap_first}DTO.getId() == null) {
            ${entity_name?uncap_first}Service.insertSelective(${entity_name?uncap_first});
        } else {
            ${entity_name?uncap_first}Service.updateByPrimaryKeySelective(${entity_name?uncap_first});
        }
        return MessageUtils.success();
    }
    /**
    * 加载修改${table_annotation}界面
    * @param model
    * @return
    */
    @RequestMapping(value = "load${entity_name}UpdatePage")
    public String load${entity_name}UpdatePage(Model model, Integer id) {
        model.addAttribute("isUpdate", true);
        model.addAttribute("id",id);
        return "admin/${entity_name?uncap_first}/${entity_name?uncap_first}Add";
    }
    /**
    * 根据用户id获取${table_annotation}信息
    * @param id
    * @return
    */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET})
    @ResponseBody
    public ${entity_name} view(@PathVariable Integer id) {
        return ${entity_name?uncap_first}Service.selectByPrimaryKey(id);
    }
    /**
    * 根据id删除${table_annotation}信息
    * @param id
    * @return
    */
    @RequestMapping(value = "{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public Map<String, Object> delete${entity_name}(@PathVariable Integer id) {
        ${entity_name} ${entity_name?uncap_first} = new ${entity_name}();
        ${entity_name?uncap_first}.setId(id);
        ${entity_name?uncap_first}.setEnable(false);
        ${entity_name?uncap_first}Service.updateByPrimaryKeySelective(${entity_name?uncap_first});
        return MessageUtils.success();
    }
    /**
    * 批量删除${table_annotation}
    * @param ids
    * @return
    */
    @RequestMapping(value = "/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        List<String> idStrList = Arrays.asList(ids.split(","));
        for (String idStr: idStrList) {
        Integer id = Integer.parseInt(idStr);
        ${entity_name} deleteRecord = new ${entity_name}();
        deleteRecord.setId(id);
        deleteRecord.setEnable(false);
        ${entity_name?uncap_first}Service.updateByPrimaryKeySelective(deleteRecord);
        }
        return MessageUtils.success();
    }
}

