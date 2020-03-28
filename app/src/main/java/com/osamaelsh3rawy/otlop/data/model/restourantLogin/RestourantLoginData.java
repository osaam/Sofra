
package com.osamaelsh3rawy.otlop.data.model.restourantLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestourantLoginData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private User user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
