
package com.osamaelsh3rawy.otlop.data.model.userRestourantFilter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestourantFilter {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestouranteFilterPagination data;

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

    public RestouranteFilterPagination getData() {
        return data;
    }

    public void setData(RestouranteFilterPagination data) {
        this.data = data;
    }

}
