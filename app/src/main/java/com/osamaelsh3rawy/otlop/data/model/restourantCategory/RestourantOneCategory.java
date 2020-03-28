package com.osamaelsh3rawy.otlop.data.model.restourantCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestourantOneCategory {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestourantCategoryData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestourantCategoryData getData() {
        return data;
    }

    public void setData(RestourantCategoryData data) {
        this.data = data;
    }


}