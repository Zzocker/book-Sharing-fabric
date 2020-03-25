package com.example.t1.MyBooksModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t1.R;
import com.example.t1.particularbkdetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ownbookAdapter extends RecyclerView.Adapter<ownbookAdapter.ownbookView> {
    public ownbookAdapter(Context mContext, ArrayList<mybookitem> mallbookitemlist) {
        this.mContext = mContext;
        this.mallbookitemlist = mallbookitemlist;
    }

    Context mContext;
    private ArrayList<mybookitem> mallbookitemlist;
    @NonNull
    @Override
    public ownbookView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_mybooks,parent,false);
        return new ownbookView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ownbookView holder, final int position) {
        holder.mbkname.setText(mallbookitemlist.get(position).getBookname());
        holder.mbkauthor.setText("Author:"+mallbookitemlist.get(position).getAuthor());
        holder.mbkisbn.setText("ISBN:"+mallbookitemlist.get(position).getIsbn());
        String path=mallbookitemlist.get(position).imageurl;
        if(!path.isEmpty())
        {
            Picasso.with(mContext).load(mallbookitemlist.get(position).imageurl)
                    .placeholder(R.drawable.bkimage).into(holder.mbkcover);
        }
       /* Picasso.with(mContext).load(mallbookitemlist.get(position).imageurl)
                .placeholder(R.drawable.bkimage).into(holder.mbkcover);

        */


        //click item listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mybookitem m=mallbookitemlist.get(position);
                String isbn=m.getIsbn();
                String name=m.getBookname();
                String author=m.getAuthor();
                String imurl=m.getImageurl();
                Toast.makeText(mContext, isbn, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(view.getContext(), particularbkdetails.class);
                i.putExtra("ID_EXTRA",new String[]{isbn,name,author,imurl});
                view.getContext().startActivity(i);




            }
        });

    }

    @Override
    public int getItemCount() {

        if(!(mallbookitemlist==null))
        {
            return mallbookitemlist.size();
        }
        else
        {
            return 0;
        }

    }

    public class ownbookView extends RecyclerView.ViewHolder{

        private Context context;

        public TextView mbkname;
        public TextView mbkauthor;
        public TextView mbkisbn;
        public ImageView mbkcover;

        public ownbookView(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            mbkname=itemView.findViewById(R.id.mybksinglename);
            mbkauthor=itemView.findViewById(R.id.mybksingleauthor);
            mbkisbn=itemView.findViewById(R.id.mybksingleisbn);
            mbkcover=itemView.findViewById(R.id.itembkcover);
        }
    }

}
