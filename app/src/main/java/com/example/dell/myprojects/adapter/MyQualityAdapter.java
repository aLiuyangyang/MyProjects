package com.example.dell.myprojects.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.dell.myprojects.bean.HotProBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyQualityAdapter extends RecyclerView.Adapter<MyQualityAdapter.ViewHolder> {
    private List<HotProBean.ResultBean.PzshBean.CommodityListBeanX> listBeanXES;
    private Context context;

    public MyQualityAdapter(Context context) {
        this.context = context;
        listBeanXES=new ArrayList<>();
    }

    public void setListBeanXES(List<HotProBean.ResultBean.PzshBean.CommodityListBeanX> listBeanXES) {
        this.listBeanXES = listBeanXES;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyQualityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_frag_quality_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyQualityAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.item_home_quality_name.setText(listBeanXES.get(i).getCommodityName());
        viewHolder.item_home_quality_price.setText("￥"+listBeanXES.get(i).getPrice()+"");
        Glide.with(context).load(listBeanXES.get(i).getMasterPic()).into(viewHolder.item_home_quality_image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",listBeanXES.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanXES.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_quality_image)
        SimpleDraweeView item_home_quality_image;
        @BindView(R.id.item_home_quality_name)
        TextView item_home_quality_name;
        @BindView(R.id.item_home_quality_price)
        TextView item_home_quality_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
