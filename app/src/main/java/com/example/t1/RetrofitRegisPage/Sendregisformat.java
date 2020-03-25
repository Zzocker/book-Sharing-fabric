
package com.example.t1.RetrofitRegisPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sendregisformat {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("room_no")
    @Expose
    private String roomNo;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sendregisformat() {
    }

    /**
     * 
     * @param password
     * @param roomNo
     * @param name
     * @param email
     * @param phoneNo
     */
    public Sendregisformat(String email, String name, String roomNo, String phoneNo, String password) {
        super();
        this.email = email;
        this.name = name;
        this.roomNo = roomNo;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
