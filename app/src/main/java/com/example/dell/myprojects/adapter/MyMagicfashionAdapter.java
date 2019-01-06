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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMagicfashionAdapter extends RecyclerView.Adapter<MyMagicfashionAdapter.ViewHolder> {
    private List<HotProBean.ResultBean.MlssBean.CommodityListBeanXX> listBeanXXES;
    private Context context;

    public MyMagicfashionAdapter(Context context) {
        this.context = context;
        listBeanXXES=new ArrayList<>();
    }

    public void setListBeanXXES(List<HotProBean.ResultBean.MlssBean.CommodityListBeanXX> listBeanXXES) {
        this.listBeanXXES = listBeanXXES;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyMagicfashionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_frag_magic_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMagicfashionAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.item_home_magic_name.setText(listBeanXXES.get(i).getCommodityName());
        viewHolder.item_home_magic_price.setText("￥"+listBeanXXES.get(i).getPrice()+"");
        Glide.with(context).load(listBeanXXES.get(i).getMasterPic()).into(viewHolder.item_home_magic_image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",listBeanXXES.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanXXES.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_magic_image)
        ImageView item_home_magic_image;
        @BindView(R.id.item_home_magic_name)
        TextView item_home_magic_name;
        @BindView(R.id.item_home_magic_price)
        TextView item_home_magic_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
