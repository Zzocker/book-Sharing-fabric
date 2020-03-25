package com.example.t1.HomeAllcurrentbookModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1.ClickOnBookDetexpand;
import com.example.t1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class allcurrbooksAdapter extends RecyclerView.Adapter<allcurrbooksAdapter.MyViewHolder> {
    private Context mContext;

    public allcurrbooksAdapter(Context mContext, ArrayList<allbookitemrecy> mallcurrbookitemlist) {
        this.mContext = mContext;
        this.mallcurrbookitemlist = mallcurrbookitemlist;
    }

    private ArrayList<allbookitemrecy> mallcurrbookitemlist;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_mybooks,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.mbkname.setText(mallcurrbookitemlist.get(position).getBookname());
        holder.mbkauthor.setText("Author:"+mallcurrbookitemlist.get(position).getAuthor());
        holder.mbkisbn.setText("ISBN:"+mallcurrbookitemlist.get(position).getBookIsbn());
        String imgurl=mallcurrbookitemlist.get(position).getCover();
        if(!imgurl.isEmpty())
        {
            Picasso.with(mContext).load(mallcurrbookitemlist.get(position).getCover())
                    .placeholder(R.drawable.bkimage).into(holder.mbkcover);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allbookitemrecy m=mallcurrbookitemlist.get(position);
                String isbn=m.getBookIsbn();
                String name=m.getBookname();
                String author=m.getAuthor();
                String imurl=m.getCover();
                Intent i=new Intent(v.getContext(), ClickOnBookDetexpand.class);
                i.putExtra("ID_EXTRA",new String[]{isbn,name,author,imurl});
                v.getContext().startActivity(i);
            }
        });




    }

    @Override
    public int getItemCount() {

        if(!(mallcurrbookitemlist ==null))
        {
            return mallcurrbookitemlist.size();
        }
        else
        {
            return 0;
        }

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private Context context;

        public TextView mbkname;
        public TextView mbkauthor;
        public TextView mbkisbn;
        public ImageView mbkcover;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            mbkname=itemView.findViewById(R.id.mybksinglename);
            mbkauthor=itemView.findViewById(R.id.mybksingleauthor);
            mbkisbn=itemView.findViewById(R.id.mybksingleisbn);
            mbkcover=itemView.findViewById(R.id.itembkcover);
        }
    }
}
