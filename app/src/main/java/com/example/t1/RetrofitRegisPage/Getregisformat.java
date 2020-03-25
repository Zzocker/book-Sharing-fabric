
package com.example.t1.RetrofitRegisPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getregisformat {

    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Getregisformat() {
    }

    /**
     * 
     * @param msg
     */
    public Getregisformat(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
