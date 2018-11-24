package com.zx.base.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色所拥有的权限配置表，一个角色有若干权限 数据库操作接口
 *
 * @author wanght
 * @version 2017-12-05 1.0.0
 */
public class TypeEnums {
    /**
     * 功能模块类型
     */
    public enum ModuleType {
        菜单(0), 功能(1);

        private int value;

        ModuleType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(Integer value) {
            for (ModuleType type : ModuleType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }
    }

    /**
     * 角色类型
     */
    public enum RoleType {
        普通(2), 内置(1), 超管(0);

        private int value;

        RoleType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (RoleType type : RoleType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }

        /**
         * 枚举名称转为List
         */
        public static List<SelectItem> toList() {
            List<SelectItem> list = new ArrayList<SelectItem>();
            for (RoleType type : RoleType.values()) {
                SelectItem item = new SelectItem();
                item.setText(type.toString());
                item.setValue(String.valueOf(type.getValue()));
                list.add(item);
            }
            return list;
        }

    }

    public enum Sex {
        男(1), 女(2), 保密(3);

        int value;

        Sex(int value) {
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

        public static String getName(int value){
            for (CategoryType type : CategoryType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }

        /**
         * 枚举名称转为List
         */
        public static List<SelectItem> toList() {
            List<SelectItem> list = new ArrayList<SelectItem>();
            for (Sex type : Sex.values()) {
                SelectItem item = new SelectItem();
                item.setText(type.toString());
                item.setValue(String.valueOf(type.getValue()));
                list.add(item);
            }
            return list;
        }
    }

    /**
     * 分组类别
     */
    public enum CategoryType {
        默认分类(0), 默认分类1(1), 默认分类2(2), 默认分类3(3);

        private int value;

        CategoryType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (CategoryType type : CategoryType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }

        /**
         * 枚举名称转为List
         */
        public static List<SelectItem> toList() {
            List<SelectItem> list = new ArrayList<SelectItem>();
            for (CategoryType type : CategoryType.values()) {
                SelectItem item = new SelectItem();
                item.setText(type.toString());
                item.setValue(String.valueOf(type.getValue()));
                list.add(item);
            }
            return list;
        }

    }

    /**
     * 状态
     */
    public enum State {
        删除(-1), 正常(0), 禁用(1);

        private int value;

        State(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (State type : State.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }


        /**
         * 枚举名称转为List
         */
        public static List<SelectItem> toList() {
            List<SelectItem> list = new ArrayList<SelectItem>();
            for (State type : State.values()) {
                SelectItem item = new SelectItem();
                item.setText(type.toString());
                item.setValue(String.valueOf(type.getValue()));
                list.add(item);
            }
            return list;
        }

    }

    /**
     * 操作是否成功
     */
    public enum IsSuccess {
        成功(1), 失败(-1), 未知(0);
        private int value;

        IsSuccess(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (IsSuccess type : IsSuccess.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }
    }

    /**
     * SelectItem
     */
    static class SelectItem {

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        private String Text;

        private String Value;
    }

    /**
     * 任务状态枚举类型
     */
    public enum StatusType {
        待分派(0), 待签收(3), 处理中(6), 处理结束(9), 已确认(12);
        private int value;

        StatusType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (StatusType type : StatusType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }

    }

}
