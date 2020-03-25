
package com.example.t1.RetrofitLoginpage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sendlgformat {

    @SerializedName("email")
    @Expose
    private String email;

    public sendlgformat(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @SerializedName("password")
    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
