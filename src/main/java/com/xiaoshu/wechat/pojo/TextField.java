package com.xiaoshu.wechat.pojo;

import java.io.Serializable;
/**
 *
 * @author XGB
 */
public class TextField implements Serializable {

    private static final long serialVersionUID = 399914261689992885L;
    /**
     *
     */
    private String value;
    /**
     *
     */
    private String color;

    public TextField(){

    }

    public TextField(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
