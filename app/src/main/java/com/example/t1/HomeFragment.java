package com.example.t1;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t1.HomeAllcurrentbookModel.Getallcurrentbooks;
import com.example.t1.HomeAllcurrentbookModel.allbookitemrecy;
import com.example.t1.HomeAllcurrentbookModel.allcurrbooksAdapter;
import com.example.t1.MyBooksModels.Book;
import com.example.t1.MyBooksModels.Getmybooksformat;
import com.example.t1.MyBooksModels.mybookitem;
import com.example.t1.MyBooksModels.ownbookAdapter;
import com.example.t1.RetrofitApis.ApiInterface;
import com.example.t1.RetrofitApis.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ownbookAdapter mallbookadpater;

    private allcurrbooksAdapter mallcurrbookadapter;

    private ArrayList<allbookitemrecy> mallcurrbooklist;

    private ArrayList<mybookitem> mallbooklist;
    RecyclerView recyclerView;

    RecyclerView recyclerView1;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        mallbooklist=new ArrayList<>();
        mallcurrbooklist=new ArrayList<>();
        recyclerView=view.findViewById(R.id.first_home_recyler);
        recyclerView1=view.findViewById(R.id.second_home_recyler);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);

        LinearLayoutManager horizontalLayoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);//for horizontal recycler view
        //remember to use when using horizontal scroll viewer
        LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView1.setLayoutManager(horizontalLayoutManager1);



        firsthorizontalviewer();
        secondhorixzontalviewer();

        return view;


    }

    //for all books in the database
    private void secondhorixzontalviewer() {

        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Secrets",MODE_PRIVATE);
        String currenttoken=sharedPreferences.getString("token","");
        ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
       Call<Getallcurrentbooks> c=apiInterface.getallcurrentbk(currenttoken);
       c.enqueue(new Callback<Getallcurrentbooks>() {
           @Override
           public void onResponse(Call<Getallcurrentbooks> call, Response<Getallcurrentbooks> response) {
               if(response.isSuccessful())
               {
                   if(!(response.body().getBooks()==null))
                   {
                       List<com.example.t1.HomeAllcurrentbookModel.Book> allbooks=response.body().getBooks();
                       for(int i=0;i<allbooks.size();i++)
                       {
                           String isbn=allbooks.get(i).getIsbn();
                           String bookname=allbooks.get(i).getBookName();
                           String bookauthor=allbooks.get(i).getAuthor();
                           String bookowner=allbooks.get(i).getOwner();
                           String bookcurentown=allbooks.get(i).getCurrent();
                           String bookcoverimg=allbooks.get(i).getCover();
                           String bookgottime="0";
                           mallcurrbooklist.add(new allbookitemrecy(bookname,bookauthor,bookcoverimg,bookcurentown,bookowner,bookgottime,isbn));


                       }
                       mallcurrbookadapter=new allcurrbooksAdapter(getActivity(),mallcurrbooklist);
                       recyclerView1.setAdapter(mallcurrbookadapter);

                   }

               }
               else
               {
                   Toasty.error(getContext(),response.message(),Toasty.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<Getallcurrentbooks> call, Throwable t) {
               Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT).show();
           }
       });


    }

    private void firsthorizontalviewer() {


        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Secrets",MODE_PRIVATE);
        String currenttoken=sharedPreferences.getString("token","");
        ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
        Call<Getmybooksformat> c=apiInterface.mybooksdetails(currenttoken);
        c.enqueue(new Callback<Getmybooksformat>() {
            @Override
            public void onResponse(Call<Getmybooksformat> call, Response<Getmybooksformat> response) {
                if(response.isSuccessful())
                {
                    if(!(response.body().getBooks()==null))
                    {
                        List<Book> mybooks=response.body().getBooks();
                        for(int i=0;i<mybooks.size();i++)
                        {
                            String bname=mybooks.get(i).getBookName();
                            String bauth=mybooks.get(i).getAuthor();
                            String bisbn=mybooks.get(i).getIsbn();
                            String bisimgurl=mybooks.get(i).getCover();
                            mallbooklist.add(new mybookitem(bname,bauth,bisimgurl,bisbn));

                        }
                        mallbookadpater=new ownbookAdapter(getActivity(),mallbooklist);
                        recyclerView.setAdapter(mallbookadpater);


                    }

                }
                else
                {
                    Toasty.error(getContext(),response.message(),Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Getmybooksformat> call, Throwable t) {
                Toasty.error(getContext(),t.getMessage(),Toasty.LENGTH_SHORT).show();
            }
        });


    }
}


