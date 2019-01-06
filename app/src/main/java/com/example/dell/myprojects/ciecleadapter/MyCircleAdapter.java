package com.example.dell.myprojects.ciecleadapter;

import android.content.Context;
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
import com.example.dell.myprojects.bean.CircleBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCircleAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<CircleBean.ResultBean> list;
    private Context context;
    public MyCircleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<CircleBean.ResultBean> lists) {
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }
    public void addList(List<CircleBean.ResultBean> lists) {
        list.addAll(lists);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_frag_circle,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder viewHolder1= (ViewHolder) viewHolder;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(list.get(i).getCreateTime()));
        if (list.get(i).getWhetherGreat()==1){
            viewHolder1.circle_simple_give.setImageResource(R.mipmap.common_btn_prise_s_hdpi);
        }else {
            viewHolder1.circle_simple_give.setImageResource(R.mipmap.common_btn_prise_n_hdpi);
        }
        viewHolder1.circle_simple_time.setText(date);
        Uri uri = Uri.parse(list.get(i).getHeadPic());
        viewHolder1.circle_simple_head.setImageURI(uri);
        viewHolder1.circle_simple_title.setText(list.get(i).getNickName());
        viewHolder1.circle_simple_zhu.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder1.circle_simple_pic);
        viewHolder1.circle_text_give.setText(list.get(i).getGreatNum()+"");
        viewHolder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circleLinener!=null){
                    circleLinener.setcircle(list.get(i).getWhetherGreat(),i,list.get(i).getId());
                }
            }
        });
    }
    //点赞
    public void add(int position){
      list.get(position).setWhetherGreat(1);
      list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
      notifyDataSetChanged();
    }
    //取消
    public void cancel(int position){
        list.get(position).setWhetherGreat(2);
        list.get(position).setGreatNum(list.get(position).getGreatNum()-1);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView circle_simple_pic;
        private SimpleDraweeView circle_simple_head,circle_simple_give;
        private TextView circle_simple_title,circle_simple_time,circle_simple_zhu,circle_text_give;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle_simple_head=itemView.findViewById(R.id.circle_simple_head);
            circle_simple_give=itemView.findViewById(R.id.circle_simple_give);
            circle_simple_pic=itemView.findViewById(R.id.circle_simple_pic);
            circle_simple_title=itemView.findViewById(R.id.circle_simple_title);
            circle_simple_time=itemView.findViewById(R.id.circle_simple_time);
            circle_simple_zhu=itemView.findViewById(R.id.circle_simple_zhu);
            circle_text_give=itemView.findViewById(R.id.circle_text_give);
            circle_simple_give=itemView.findViewById(R.id.circle_simple_give);
        }
    }
    private CircleLinener circleLinener;

    public void setCircleLinener(CircleLinener circleLinener) {
        this.circleLinener = circleLinener;
    }

    public interface CircleLinener{
        void setcircle(int b,int position,int id);
    }
}
