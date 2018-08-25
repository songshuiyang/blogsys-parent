package ${serviceImpl_packege};

import ${entity_packege}.${entity_name};
import ${mapper_packege}.${entity_name}Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* ${table_annotation} service接口
* @author ${author}
* @date ${date}
*/
@Service
public class ${entity_name}ServiceImpl extends BaseServiceImpl<${entity_name},${id_java_type}> implements ${entity_name}Service{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BaseMapper<${entity_name}, Integer> getMappser() {
        return mapper;
    }

    @Autowired
    private ${entity_name}Mapper mapper;

    @Override
    public int deleteByPrimaryKey(${id_java_type} id){
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(${entity_name} record){
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(${entity_name} record){
        return mapper.insertSelective(record);
    }
    @Override
    public ${entity_name} selectByPrimaryKey(${id_java_type} id){
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(${entity_name} record){
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(${entity_name} record){
        return mapper.updateByPrimaryKey(record);
    }
}