package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.bean.LinkageTwoBean;

import java.util.ArrayList;
import java.util.List;

public class MyLinkAgeTwoAdapter extends RecyclerView.Adapter<MyLinkAgeTwoAdapter.ViewHolder> {
    private List<LinkageTwoBean.ResultBean> list;
    private Context context;

    public MyLinkAgeTwoAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<LinkageTwoBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyLinkAgeTwoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_two_linkage,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLinkAgeTwoAdapter.ViewHolder viewHolder, final int i) {
      viewHolder.item_linkagetwo_name.setText(list.get(i).getName());
      viewHolder.item_linkagetwo_name.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              linkAgeChildLineren.linkageChild(list.get(i).getId());
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      private TextView item_linkagetwo_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_linkagetwo_name=itemView.findViewById(R.id.item_linkagetwo_name);
        }
    }
    private LinkAgeChildLineren linkAgeChildLineren;

    public void setLinkAgeChildLineren(LinkAgeChildLineren linkAgeChildLineren) {
        this.linkAgeChildLineren = linkAgeChildLineren;
    }

    public interface LinkAgeChildLineren{
        void linkageChild(String cid);
    }
}
