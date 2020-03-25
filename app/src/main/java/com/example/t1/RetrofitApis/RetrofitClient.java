package com.example.t1.RetrofitApis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit = null;
    public static Retrofit getClient()
    {
        //String BaseUrl="http://172.16.191.196:3000/";//by default
        String BaseUrl="http://172.16.181.119:3000/";
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl(BaseUrl)//change it afterwards when everthing is hosted
                .addConverterFactory(GsonConverterFactory.create());
        retrofit=builder.build();
        return retrofit;
    }


}
