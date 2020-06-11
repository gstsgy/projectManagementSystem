package com.guyue.pms.bean.view;

import org.springframework.stereotype.Component;

@Component
public class ResponseBean {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseBean getSuccess(Object data){
        this.code = 0;
        this.message ="success";
        this.data = data;
        return this;
    }

    public ResponseBean getError(String message){
        this.code = 0;
        this.message =message;
        this.data = null;
        return this;
    }
}
