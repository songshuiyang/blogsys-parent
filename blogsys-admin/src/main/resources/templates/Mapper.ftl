<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapper_packege}.${entity_name}Mapper" >
    <resultMap id="BaseResultMap" type="${package_name}.${entity_name}" >
<#if entity_column?exists>
    <#list entity_column as entity>
        <#--生成主键 id-->
      <#if entity.columnName == 'id' && entity.columnType == 'INT'>
        <id column="id" property="id" jdbcType="INTEGER" />
      <#elseif entity.columnName == 'id' && entity.columnType == 'VARCHAR'>
        <id column="id" property="id" jdbcType="VARCHAR" />
      <#else>
        <result column="${entity.columnName}" property="${entity.changeColumnName?uncap_first}" jdbcType="${entity.columnType}" />
      </#if>
    </#list>
</#if>
    </resultMap>
    <sql id="Base_Column_List" >
    <#if entity_column?exists>
        <#list entity_column as entity>
            <#if entity_has_next>
        ${entity.columnName},
            <#else>
        ${entity.columnName}
            </#if>
        </#list>
    </#if>
    </sql>

    <!--分页查询-->
    <select id="findPageList" parameterType="${package_name}.${entity_name}" resultType="${package_name}.${entity_name}">
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        ${table_name}
        <where>
            <#if entity_column?exists>
            <#list entity_column as entity>
                <#--字符类型使用like-->
                <#if entity.javaType == 'String'>
            <if test="${entity.changeColumnName?uncap_first} != null and ${entity.changeColumnName?uncap_first} !=''" >
              AND ${entity.columnName} LIKE CONCAT('%','${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}}','%')
            </if>
                <#else>
            <if test="${entity.changeColumnName?uncap_first} != null" >
              AND ${entity.columnName} = ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}}
            </if>
                </#if>
            </#list>
            </#if>
        </where>
    </select>

    <!--根据主键id获取记录-->
    <select id="selectByPrimaryKey" resultMap="${package_name}.${entity_name}" parameterType="java.lang.${id_java_type}" >
        SELECT
          <include refid="Base_Column_List" />
        FROM
          ${table_name}
        WHERE id = ${char_1}{id,jdbcType=${id_jdbc_type}}
    </select>

    <!--根据主键id删除记录-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.${id_java_type}">
        DELETE
        FROM
          ${table_name}
        WHERE id = ${char_1}{id,jdbcType=${id_jdbc_type}}
    </delete>

    <!--插入新的记录-->
    <insert id="insert" parameterType="${package_name}.${entity_name}" >
        INSERT INTO ${table_name} (
            <#if entity_column?exists>
                <#list entity_column as entity>
                    <#if entity_has_next>
                    ${entity.columnName},
                    <#else>
                    ${entity.columnName}
                    </#if>
                </#list>
            </#if>
        )
        values (
            <#if entity_column?exists>
                <#list entity_column as entity>
                    <#if entity_has_next>
                    ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}}),
                    <#else>
                    ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}})
                    </#if>
                </#list>
            </#if>
        )
    </insert>

    <!--选择性插入一条记录-->
    <insert id="insertSelective" parameterType="${package_name}.${entity_name}" >
        INSERT INTO ${table_name}
        <trim prefix="(" suffix=")" suffixOverrides=",">

    <#if entity_column?exists>
        <#list entity_column as entity>
            <if test="${entity.changeColumnName?uncap_first} != null">
                ${entity.columnName},
            </if>
        </#list>
    </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#if entity_column?exists>
            <#list entity_column as entity>
             <if test="${entity.changeColumnName?uncap_first} != null">
                ${char_1}{${entity.columnName},jdbcType =${entity.columnType}}
             </if>
            </#list>
        </#if>
        </trim>
    </insert>

    <!--更新非空字段-->
    <update id="updateByPrimaryKeySelective" parameterType="${package_name}.${entity_name}" >
        UPDATE ${table_name}
        <set >
    <#if entity_column?exists>
        <#list entity_column as entity>
            <if test="${entity.changeColumnName?uncap_first} != null">
            ${char_1}{${entity.columnName},jdbcType =${entity.columnType}},
            </if>
        </#list>
    </#if>
        </set>
        WHERE id = ${char_1}{id,jdbcType=${id_jdbc_type}}
    </update>

    <!--根据主键更新记录-->
    <update id="updateByPrimaryKey" parameterType="com.songsy.admin.entity.Role" >
        UPDATE ${table_name}
        SET
        <#if entity_column?exists>
            <#list entity_column as entity>
                <#if entity_has_next>
           ${entity.columnName} = ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}}),
                <#else>
           ${entity.columnName} = ${char_1}{${entity.changeColumnName?uncap_first},jdbcType=${entity.columnType}})
                </#if>
            </#list>
        </#if>
        WHERE id = ${char_1}{id,jdbcType=${id_jdbc_type}}
    </update>
</mapper>