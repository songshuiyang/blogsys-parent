package ${package_name};
import javax.persistence.*;
import com.songsy.base.BaseEntity;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* ${table_annotation} 实体类
* @author ${author}
* @date ${date}
*/
@Table(name="${table_name}")
@Getter
@Setter
@ToString
public class ${entity_name} extends BaseEntity {

<#if entity_column?exists>
  <#list entity_column as entity>
    /**
    * ${entity.columnComment!}
    * jdbcType: ${entity.columnType!}
    * javaType: ${entity.javaType!}
    */
    private ${entity.javaType!} ${entity.changeColumnName?uncap_first};

  </#list>
</#if>
<#--get set 生成-->

<#--<#if entity_column?exists>-->
  <#--<#list entity_column as entity>-->

    <#--public ${entity.javaType!} get${entity.changeColumnName}() {-->
        <#--return this.${entity.changeColumnName?uncap_first};-->
    <#--}-->

    <#--public void set${entity.changeColumnName}(String ${entity.changeColumnName?uncap_first}) {-->
        <#--this.${entity.changeColumnName?uncap_first} = ${entity.changeColumnName?uncap_first};-->
    <#--}-->

  <#--</#list>-->
<#--</#if>-->

}