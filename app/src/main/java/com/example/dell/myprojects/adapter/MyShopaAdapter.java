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

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.activity.DetalisActivity;
import com.example.dell.myprojects.bean.ShopBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyShopaAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<ShopBean.ResultBean> mlist;
   private Context context;
    public MyShopaAdapter(Context context) {
       this.context=context;
       mlist=new ArrayList<>();
    }

    public void setList(List<ShopBean.ResultBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<ShopBean.ResultBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_shop_home,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
         ViewHolder viewHolder1= (ViewHolder) viewHolder;
         viewHolder1.item_shop_name.setText(mlist.get(i).getCommodityName());
         viewHolder1.item_shop_price.setText("￥"+mlist.get(i).getPrice()+"");
         viewHolder1.item_shop_sale.setText("已售"+mlist.get(i).getSaleNum()+"件");
         Uri uri = Uri.parse(mlist.get(i).getMasterPic());
         viewHolder1.item_shop_image.setImageURI(uri);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context,DetalisActivity.class);
                intent1.putExtra("hodid",mlist.get(i).getCommodityId());
                context.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView item_shop_image;
       private TextView item_shop_name,item_shop_price,item_shop_sale;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_shop_name=itemView.findViewById(R.id.item_shop_name);
            item_shop_price=itemView.findViewById(R.id.item_shop_price);
            item_shop_sale=itemView.findViewById(R.id.item_shop_sale);
            item_shop_image=itemView.findViewById(R.id.item_shop_image);
        }
    }
}
