package com.example.t1;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.t1.MyBooksModels.Book;
import com.example.t1.MyBooksModels.Getmybooksformat;
import com.example.t1.RetrofitApis.ApiInterface;
import com.example.t1.RetrofitApis.RetrofitClient;
import com.example.t1.RetrofitUserProfileModels.Getpersonaldet;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class profilefragment extends Fragment {

    TextView pownedbk,proomno,pphn,pupemail,pname,pupname;

    public profilefragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profilefragment, container, false);

        pownedbk=view.findViewById(R.id.proownedbk);
        proomno=view.findViewById(R.id.proroomno);
        pphn=view.findViewById(R.id.prophn);
        pupemail=view.findViewById(R.id.upperemailpro);
        pupname=view.findViewById(R.id.upperproname);
        pname=view.findViewById(R.id.proname);

        //backend starts
        getprofiledetails();
        //backend ends


        // Inflate the layout for this fragment
        return view;
    }

    private void getprofiledetails() {

        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Secrets",MODE_PRIVATE);
        final String currenttoken=sharedPreferences.getString("token","");

        final ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
        Call<Getpersonaldet> c=apiInterface.getmedetails(currenttoken);
        c.enqueue(new Callback<Getpersonaldet>() {
            @Override
            public void onResponse(Call<Getpersonaldet> call, Response<Getpersonaldet> response) {
                    if(response.isSuccessful())
                    {
                        String username=response.body().getMsg().getName();
                        String useremail=response.body().getMsg().getEmail();
                        String userphn=response.body().getMsg().getPhoneNo();
                        String userroomno=response.body().getMsg().getRoomNo();
                        //ownedbooks and current books needed
                        ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
                            Call<Getmybooksformat> c=apiInterface.mybooksdetails(currenttoken);
                            c.enqueue(new Callback<Getmybooksformat>() {
                                @Override
                                public void onResponse(Call<Getmybooksformat> call, Response<Getmybooksformat> response) {
                                    if(response.isSuccessful())
                                    {
                                        List<Book> mybooks=response.body().getBooks();
                                        String m= String.valueOf(mybooks.size());
                                        pownedbk.setText(m);
                                    }
                                    else
                                    {
                                        pownedbk.setText("0");
                                    }
                                }

                                @Override
                                public void onFailure(Call<Getmybooksformat> call, Throwable t) {
                                    pownedbk.setText("0");
                                }
                            });


                        //setting details to ui
                        pupname.setText(username);
                        pupemail.setText(useremail);
                        proomno.setText(userroomno);
                        pphn.setText(userphn);
                        pname.setText(username);


                    }
                    else
                    {
                        Toasty.error(getContext(),response.message(),Toasty.LENGTH_SHORT).show();
                    }


            }

            @Override
            public void onFailure(Call<Getpersonaldet> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT).show();
            }
        });


    }

}
