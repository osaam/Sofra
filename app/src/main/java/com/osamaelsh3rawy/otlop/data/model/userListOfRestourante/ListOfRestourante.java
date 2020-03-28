
package com.osamaelsh3rawy.otlop.data.model.userListOfRestourante;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfRestourante {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestourantesPagenation data;

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

    public RestourantesPagenation getData() {
        return data;
    }

    public void setData(RestourantesPagenation data) {
        this.data = data;
    }

}
