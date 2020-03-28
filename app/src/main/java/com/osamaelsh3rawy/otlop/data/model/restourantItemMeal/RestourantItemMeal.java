
package com.osamaelsh3rawy.otlop.data.model.restourantItemMeal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestourantItemMeal {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private restourantItemMealPagination data;

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

    public restourantItemMealPagination getData() {
        return data;
    }

    public void setData(restourantItemMealPagination data) {
        this.data = data;
    }

}
