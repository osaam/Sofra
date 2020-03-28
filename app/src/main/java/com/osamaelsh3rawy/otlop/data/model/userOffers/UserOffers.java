
package com.osamaelsh3rawy.otlop.data.model.userOffers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserOffers {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private UserOffersPagination data;

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

    public UserOffersPagination getData() {
        return data;
    }

    public void setData(UserOffersPagination data) {
        this.data = data;
    }

}
