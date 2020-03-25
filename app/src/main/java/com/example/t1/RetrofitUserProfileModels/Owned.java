
package com.example.t1.RetrofitUserProfileModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Owned {

    @SerializedName("8902519001993")
    @Expose
    private Boolean _8902519001993;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Owned() {
    }

    /**
     * 
     * @param _8902519001993
     */
    public Owned(Boolean _8902519001993) {
        super();
        this._8902519001993 = _8902519001993;
    }

    public Boolean get8902519001993() {
        return _8902519001993;
    }

    public void set8902519001993(Boolean _8902519001993) {
        this._8902519001993 = _8902519001993;
    }

}
