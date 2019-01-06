package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.myprojects.R;
import com.example.dell.myprojects.activity.DetalisActivity;
import com.example.dell.myprojects.bean.ChildHomeBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyChildHotAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
         private List<ChildHomeBean.ResultBean> hotlist;
         private Context context;

    public MyChildHotAdapter(Context context) {
        this.context = context;
        hotlist=new ArrayList<>();
    }

    public void setHotlist(List<ChildHomeBean.ResultBean> hotlists) {
        hotlist.clear();
        hotlist.addAll(hotlists);
        notifyDataSetChanged();
    }
    public void addHotlist(List<ChildHomeBean.ResultBean> hotlists) {
        hotlist.addAll(hotlists);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_child_home_hot,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
             ViewHolder holder= (ViewHolder) viewHolder;
             holder.item_child_hot_name.setText(hotlist.get(i).getCommodityName());
             holder.item_child_hot_price.setText("￥"+hotlist.get(i).getPrice()+"");
             holder.item_child_hot_sale.setText("已售"+hotlist.get(i).getSaleNum()+"件");
             Uri uri = Uri.parse(hotlist.get(i).getMasterPic());
             holder.item_child_hot_image.setImageURI(uri);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",hotlist.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_child_hot_name)
        TextView item_child_hot_name;
        @BindView(R.id.item_child_hot_price)
        TextView item_child_hot_price;
        @BindView(R.id.item_child_hot_sale)
        TextView item_child_hot_sale;
        @BindView(R.id.item_child_hot_image)
        SimpleDraweeView item_child_hot_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
