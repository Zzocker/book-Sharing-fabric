
package com.example.t1.RetrofitLoginpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getlgformat {

    @SerializedName("token")
    @Expose
    private String token;

    public getlgformat(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
