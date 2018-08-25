package com.songsy.base;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 实体抽象基类 提取公共属性
 * @author songshuiyang
 * @date 2018/2/11 20:12
 */
@Getter
@Setter
@ToString
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3873745966284869947L;

    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * id
     */
    private Integer id;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 最后修改人
     */
    private String lastModifiedBy;
    /**
     * 最后修改时间
     */
    private Date lastModifiedDate;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 逻辑删除标志
     */
    private Boolean enable;

    /**
     * 逻辑删除标志中文
     * @return
     */
    public String getEnableAlias() {
        if (this.enable != null) {
            return this.enable != null?System.Enable.getAliasByIndex(1):System.Enable.getAliasByIndex(0);
        }
        return null;
    }

    /**
     * 创建时间格式化
     * @return
     */
    public String getcreatedDateAlias() {
        if (this.createdDate != null) {
            String returnValue = "";
            if (this.createdDate != null) {
                SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
                returnValue = df.format(this.createdDate);
            }
            return (returnValue);
        } else {
            return null;
        }
    }
    /**
     *
     * 系统枚举类
     *
     */
    private static final class System {
        /**
         * 逻辑删除标志
         */
        public enum Enable {

            正常("正常", 1),

            已删除("已删除", 0);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Enable(String alias, Integer index) {
                this.alias = alias;
                this.index = index;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(Integer index) {
                this.index = index;
            }

            public String getAlias() {
                return alias;
            }

            public static Map<Integer, String> getMap() {
                if (map == null) {
                    map = Maps.newLinkedHashMap();
                    for (Enable value : Enable.values()) {
                        map.put(value.getIndex(), value.getAlias());
                    }
                }
                return map;
            }

            public static String getAliasByIndex(Integer index) {
                getMap();
                return map.get(index);
            }

            public static Integer getIndexByAlias(String alias) {
                getMap();
                for (Map.Entry entry : map.entrySet()) {
                    if (alias.equals(entry.getValue())) {
                        return (Integer) entry.getKey();
                    }
                }
                return null;
            }

            public static boolean contain(Integer fdType) {
                return fdType != null && getMap().get(fdType) != null;
            }
        }
    }
}
