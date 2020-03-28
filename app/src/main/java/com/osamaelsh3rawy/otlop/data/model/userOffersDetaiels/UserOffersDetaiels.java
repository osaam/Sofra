
package com.osamaelsh3rawy.otlop.data.model.userOffersDetaiels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserOffersDetaiels {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private UserOffersDetaielsData data;

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

    public UserOffersDetaielsData getData() {
        return data;
    }

    public void setData(UserOffersDetaielsData data) {
        this.data = data;
    }

}
