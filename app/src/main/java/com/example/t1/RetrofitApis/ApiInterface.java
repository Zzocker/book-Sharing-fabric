package com.example.t1.RetrofitApis;

import com.example.t1.RetrofitUserProfileModels.Getpersonaldet;
import com.example.t1.HomeAllcurrentbookModel.Getallcurrentbooks;
import com.example.t1.MyBooksModels.Getmybooksformat;
import com.example.t1.RetrofitLoginpage.getlgformat;
import com.example.t1.RetrofitLoginpage.sendlgformat;
import com.example.t1.RetrofitRegisPage.Getregisformat;
import com.example.t1.RetrofitRegisPage.Sendregisformat;
import com.example.t1.Retrofitaddbooks.Sendadbkformat;
import com.example.t1.RetrofitCoverimageModels.Sendcoverimgurlformat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {


    //login credentials
    @POST("api/user/login")
    Call<getlgformat> getlogindata(@Body sendlgformat sendlgformat);

    //for registeration of new user
    @POST("api/user/register")
    Call<getlgformat> getregdata(@Body Sendregisformat sendregisformat);

    //for adding of new books
    @POST("api/user/addbook")
    Call<Getregisformat> addbookdetails(@Body Sendadbkformat sendadbkformat, @Header("Authorization") String header);

    @GET("api/user/getallownedbook")
    Call<Getmybooksformat> mybooksdetails(@Header("Authorization") String header);

    @PUT("api/user/changecover")
    Call<Getregisformat> changecover(@Body Sendcoverimgurlformat sendcoverimgurlformat, @Header("Authorization") String header);

    //for all books in the database
    @GET("api/user/getallthebook")
    Call<Getallcurrentbooks> getallcurrentbk(@Header("Authorization") String header);

    //profile fragment details of the user
    @GET("api/user/me")
    Call<Getpersonaldet> getmedetails(@Header("Authorization") String header);





}
