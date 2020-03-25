
package com.example.t1.RetrofitUserProfileModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

    @SerializedName("8902519001993")
    @Expose
    private Integer _8902519001993;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Current() {
    }

    /**
     * 
     * @param _8902519001993
     */
    public Current(Integer _8902519001993) {
        super();
        this._8902519001993 = _8902519001993;
    }

    public Integer get8902519001993() {
        return _8902519001993;
    }

    public void set8902519001993(Integer _8902519001993) {
        this._8902519001993 = _8902519001993;
    }

}
