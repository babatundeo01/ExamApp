package com.example.consultants.examapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.consultants.examapp.models.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    Context context;
    private List<Result> resultlist;

    public MyRecyclerAdapter(List<Result> resultlist) {
        this.resultlist = resultlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //String value = list.get(i);
        myViewHolder.textView.setText(resultlist.get(i).getName().getFirst() +" "+ resultlist.get(i).getName().getLast());
        Picasso.get().load(resultlist.get(i).getPicture().getLarge()).into(myViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        if(resultlist != null){
            return resultlist.size();
        }else {
            return 0;
        }
    }

    public void updateDataSet(List<Result> resultlist) {
        this.resultlist = resultlist;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
