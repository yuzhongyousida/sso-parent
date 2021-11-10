package org.example.constant;

/**
 * Result类对应的枚举类
 */
public enum ResultEnum {
    SUCCESS(200, "login success"),
    FAIL(-1, "not login or login failed");

    private Integer code;
    private String description;

    ResultEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
