
package com.example.t1.RetrofitUserProfileModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msg {

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
    @SerializedName("owned")
    @Expose
    private Owned owned;
    @SerializedName("current")
    @Expose
    private Current current;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Msg() {
    }

    /**
     * 
     * @param current
     * @param roomNo
     * @param owned
     * @param name
     * @param email
     * @param phoneNo
     */
    public Msg(String email, String name, String roomNo, String phoneNo, Owned owned, Current current) {
        super();
        this.email = email;
        this.name = name;
        this.roomNo = roomNo;
        this.phoneNo = phoneNo;
        this.owned = owned;
        this.current = current;
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

    public Owned getOwned() {
        return owned;
    }

    public void setOwned(Owned owned) {
        this.owned = owned;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

}
