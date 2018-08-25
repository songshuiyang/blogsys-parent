package ${package_name};

import ${entity_packege}.${entity_name};
import com.songsy.base.BaseMapper;

/**
* ${table_annotation} dao接口
* @author ${author}
* @date ${date}
*/
public interface ${entity_name}Mapper extends BaseMapper<${entity_name},${id_java_type}>{

    int deleteByPrimaryKey(${id_java_type} id);

    int insert(${entity_name} record);

    int insertSelective(${entity_name} record);

    ${entity_name} selectByPrimaryKey(${id_java_type} id);

    int updateByPrimaryKeySelective(${entity_name} record);

    int updateByPrimaryKey(${entity_name} record);

}