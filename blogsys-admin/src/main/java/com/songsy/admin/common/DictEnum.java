package com.songsy.admin.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author songshuiyang
 * @date 2018/2/27 21:04
 */
public final class DictEnum {
    /**
     *
     * 系统枚举类
     *
     */
    public static final class System {
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

        /**
         * 菜单类型
         */
        public enum MenuType {

            一级菜单("一级菜单", 0),

            二级菜单("二级菜单", 1);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            MenuType(String alias, Integer index) {
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
                    for (MenuType value : MenuType.values()) {
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

        /**
         * 权限类型
         */
        public enum PermissionType {

            菜单("菜单", 0),

            按钮("按钮", 1),

            权限("权限", 2);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            PermissionType(String alias, Integer index) {
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
                    for (PermissionType value : PermissionType.values()) {
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
    /**
     *
     * 用户枚举类
     *
     */
    public static final class User {
        /**
         * 用户类型
         */
        public enum Gender {

            male("男", 1),

            female("女", 0);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Gender(String alias, Integer index) {
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
                    for (Gender value : Gender.values()) {
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

    /**
     *
     * 博客文章枚举类
     *
     */
    public static final class BlogArticles {
        /**
         * 文章分类
         */
        public enum Type {

            转载("转载", 0),

            原创("原创", 1),

            精贴("精贴", 2);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Type(String alias, Integer index) {
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
                    for (Type value : Type.values()) {
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
        /**
         * 文章状态
         */
        public enum Status {

            未发布("未发布", 0),

            发布("发布", 1);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Status(String alias, Integer index) {
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
                    for (Status value : Status.values()) {
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

    /**
     *
     * 评论枚举类
     *
     */
    public static final class CommentArticles {
        /**
         * 评论分类
         */
        public enum Type {

            评论("评论", 0),

            留言("留言", 1);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Type(String alias, Integer index) {
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
                    for (Type value : Type.values()) {
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
        /**
         * 文章状态
         */
        public enum Status {

            可见("可见", 0),

            不可见("不可见", 1);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Status(String alias, Integer index) {
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
                    for (Status value : Status.values()) {
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

    /**
     *
     * 相册枚举类
     *
     */
    public static final class PhotoAlbum {
        /**
         * 分类
         */
        public enum Type {

            转载("转载", 0),

            原创("原创", 1),

            精图("精图", 2);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Type(String alias, Integer index) {
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
                    for (Type value : Type.values()) {
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
        /**
         * 图片状态
         */
        public enum Status {

            未发布("未发布", 0),

            发布("发布", 1);

            private static Map<Integer, String> map;

            private String alias;

            private Integer index;

            /**
             * 构造方法
             *
             * @param alias
             * @param index
             */
            Status(String alias, Integer index) {
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
                    for (Status value : Status.values()) {
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
