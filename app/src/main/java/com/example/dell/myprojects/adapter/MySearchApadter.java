package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.myprojects.R;
import com.example.dell.myprojects.activity.DetalisActivity;
import com.example.dell.myprojects.bean.SearchBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MySearchApadter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
      private List<SearchBean.ResultBean> mResultBeans;
      private Context context;

    public MySearchApadter(Context context) {
        this.context = context;
        mResultBeans=new ArrayList<>();
    }

    public void setResultBeans(List<SearchBean.ResultBean> resultBeans) {
        mResultBeans.clear();
        mResultBeans.addAll(resultBeans);
        notifyDataSetChanged();
    }
    public void addResultBeans(List<SearchBean.ResultBean> resultBeans) {
        mResultBeans.addAll(resultBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_search_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
       ViewHolder viewHolder1= (ViewHolder) viewHolder;
       viewHolder1.item_search_name.setText(mResultBeans.get(i).getCommodityName());
       viewHolder1.item_search_price.setText("￥"+mResultBeans.get(i).getPrice()+"");
       viewHolder1.item_search_sale.setText("已售"+mResultBeans.get(i).getSaleNum()+"件");
        Uri uri = Uri.parse(mResultBeans.get(i).getMasterPic());
        viewHolder1.item_search_image.setImageURI(uri);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",mResultBeans.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
         @BindView(R.id.item_search_image)
        SimpleDraweeView item_search_image;
         @BindView(R.id.item_search_name)
        TextView item_search_name;
        @BindView(R.id.item_search_price)
        TextView item_search_price;
        @BindView(R.id.item_search_sale)
        TextView item_search_sale;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
