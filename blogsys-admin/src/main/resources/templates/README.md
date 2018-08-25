## Java 代码自动生成工具 

### 前言 
由于之前代码生成使用的是Mybatis-generator插件，但该插件生成的文件不能满足实际项目的需要，具有如下缺点
* 生成的文件都是固定格式，不能自定义定制，比如某个类需要继承某个抽象类，实现某个接口，用这个Mybatis-generator插件不能解决这些问题
* 实体类字段没有生成将表字段注释，需要自己手动添加
* mapper.xml文件 
```sql
没有 title !='' 字符串判空判断, 只有判断是否是null
<if test="title != null" >
    
</if>
```
* 生成的文件只有`entity.java、 mapper.java、 mapper.xml` 这些文件, 但实际项目中还需要 `service.java serviceImpl.java controller.java` 这些文件，甚至应该有`jsp vue`这些文件


#### 初步想法
1. 利用原生Jdbc获取数据库表元数据
2. 获取到表元数据之后利用freemaker模板框架自动生成Dao Entity Service Controller mapper等文件
3. 后期有时间可以支持jsp模板

#### 使用类
类路径`com.songsy.core.generate.CodeGenerateUtils`, 填入表名及实体类名即可

```java
    public static void main(String[] args) throws Exception{
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        // 表名 + 实体类名 + 实体类中文名
        codeGenerateUtils.generate("sys_account","Account","用戶");
    }
```
