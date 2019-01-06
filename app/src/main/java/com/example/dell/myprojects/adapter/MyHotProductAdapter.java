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

public class MyHotProductAdapter extends RecyclerView.Adapter<MyHotProductAdapter.ViewHolder> {
    private List<HotProBean.ResultBean.RxxpBean.CommodityListBean> list;
    private Context context;

    public MyHotProductAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setHotList(List<HotProBean.ResultBean.RxxpBean.CommodityListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHotProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_frag_hot_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHotProductAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.item_home_hot_name.setText(list.get(i).getCommodityName());
        viewHolder.item_home_hot_price.setText("￥"+list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.item_home_hot_image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",list.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_hot_image)
         ImageView item_home_hot_image;
        @BindView(R.id.item_home_hot_name)
         TextView item_home_hot_name;
        @BindView(R.id.item_home_hot_price)
         TextView item_home_hot_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private HotProductLiskener hotProductLiskener;

    public void setHotProductLiskener(HotProductLiskener hotProductLiskener) {
        this.hotProductLiskener = hotProductLiskener;
    }

    public interface HotProductLiskener{
        void setHotPro(int hid);
  }
}
