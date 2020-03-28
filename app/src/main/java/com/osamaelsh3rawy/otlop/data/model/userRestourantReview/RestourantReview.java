
package com.osamaelsh3rawy.otlop.data.model.userRestourantReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestourantReview {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private UserRestaurantReviewPagination data;

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

    public UserRestaurantReviewPagination getData() {
        return data;
    }

    public void setData(UserRestaurantReviewPagination data) {
        this.data = data;
    }

}
