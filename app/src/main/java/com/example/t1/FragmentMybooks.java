package com.example.t1;


import android.bluetooth.BluetoothAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class FragmentMybooks extends Fragment {

    private ownbookAdapter mallbookadpater;

    private ArrayList<mybookitem> mallbooklist;
    public FragmentMybooks() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_mybooks, container, false);

        recyclerView=view.findViewById(R.id.mybooksrecycler);
        mallbooklist=new ArrayList<>();


        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        // LinearLayoutManager horizontalLayoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);//for horizontal recycler view
        recyclerView.setLayoutManager(gridLayoutManager);

        serverend();


        return view;
    }

    private void serverend() {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("Secrets",MODE_PRIVATE);
        String currenttoken=sharedPreferences.getString("token","");
        ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
        Call<Getmybooksformat> c=apiInterface.mybooksdetails(currenttoken);
        c.enqueue(new Callback<Getmybooksformat>() {
            @Override
            public void onResponse(Call<Getmybooksformat> call, Response<Getmybooksformat> response) {
                if(response.isSuccessful())
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
