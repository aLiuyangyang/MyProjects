package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.activity.DetalisActivity;
import com.example.dell.myprojects.bean.LinkageBean;

import java.util.ArrayList;
import java.util.List;

public class MyLinkAgeAdapter extends RecyclerView.Adapter<MyLinkAgeAdapter.ViewHolder> {
    private List<LinkageBean.ResultBean> list=new ArrayList<>();
    private Context context;

    public MyLinkAgeAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<LinkageBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyLinkAgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_linkage,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLinkAgeAdapter.ViewHolder viewHolder, final int i) {
       viewHolder.item_linkage_name.setText(list.get(i).getName());
       viewHolder.item_linkage_name.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               linkageListener.lin(String.valueOf(list.get(i).getId()));
           }
       });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         private TextView item_linkage_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_linkage_name=itemView.findViewById(R.id.item_linkage_name);
        }
    }
    private LinkageListener linkageListener;

    public void setLinkageListener(LinkageListener linkageListener) {
        this.linkageListener = linkageListener;
    }

    public interface LinkageListener{
        void lin(String id);
    }
}
