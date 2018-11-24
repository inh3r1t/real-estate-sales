package com.zx.base.model;

/**
 * @author feidengke
 */
public class ReturnModel {
    /**
     * 状态：true 成功  false 失败
     */
    private boolean state;
    /**
     * 返回提示信息
     */
    private String message;

    /**
     * 返回model类型
     */
    private ReturnType type;

    private Object model;

    public ReturnModel() {
        type = ReturnType.Normal;
    }

    public ReturnModel(boolean state, String message) {
        this.setState(state);
        this.setMessage(message);
    }

    public ReturnModel(boolean state, String message, Object data) {
        this.setState(state);
        this.setMessage(message);
        this.setModel(data);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public void setInfo(boolean state, String message) {
        this.setState(state);
        this.setMessage(message);
    }

    public ReturnType getType() {
        return type;
    }

    public void setType(ReturnType type) {
        this.type = type;
    }


    /**
     * Normal：默认，
     * FormValidate：表单验证
     */
    public enum ReturnType {
        Normal(0), FormValidate(1);
        private int value;

        ReturnType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static String getName(int value) {
            for (ReturnType type : ReturnType.values()) {
                if (type.getValue() == value) {
                    return type.toString();
                }
            }
            return "";
        }
    }
}
