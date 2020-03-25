
package com.example.t1.RetrofitUserProfileModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getpersonaldet {

    @SerializedName("msg")
    @Expose
    private Msg msg;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Getpersonaldet() {
    }

    /**
     * 
     * @param msg
     */
    public Getpersonaldet(Msg msg) {
        super();
        this.msg = msg;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

}
