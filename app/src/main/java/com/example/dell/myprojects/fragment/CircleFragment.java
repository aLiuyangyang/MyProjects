package com.example.dell.myprojects.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.activity.ShowActivity;
import com.example.dell.myprojects.bean.AddCircleBean;
import com.example.dell.myprojects.ciecleadapter.MyCircleAdapter;
import com.example.dell.myprojects.bean.CircleBean;
import com.example.dell.myprojects.presenter.PresenterImpl;
import com.example.dell.myprojects.util.Apis;
import com.example.dell.myprojects.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleFragment extends BaseFragment implements View.OnClickListener,IView {
    private XRecyclerView frag_circle_xrecy;
    private PresenterImpl presenter;
    private MyCircleAdapter adapter;
    private int page=1;
    private int COUNT=5;
    @Override
    protected void initData() {
        circle();
    }
    @Override
    protected void initView(View view) {
        frag_circle_xrecy = (XRecyclerView) view.findViewById(R.id.frag_circle_xrecy);
        presenter=new PresenterImpl(this);
        frag_circle_xrecy.setLoadingMoreEnabled(true);
        frag_circle_xrecy.setPullRefreshEnabled(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        frag_circle_xrecy.setLayoutManager(linearLayoutManager);
        adapter=new MyCircleAdapter(getContext());
        frag_circle_xrecy.setAdapter(adapter);
        frag_circle_xrecy.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                circle();
            }
            @Override
            public void onLoadMore() {
                circle();
            }
        });
       adapter.setCircleLinener(new MyCircleAdapter.CircleLinener() {
           @Override
           public void setcircle(int b, int position, int id) {
               if(b==1){
                   presenter.setRequestDelete(String.format(Apis.HIDECRICLE_URI,id),AddCircleBean.class);
                   adapter.cancel(position);
               }else if(b == 2){
                   Map<String,String> map=new HashMap<>();
                   map.put("circleId",id+"");
                   presenter.setRequestData(Apis.ADDCRICLE_URI,map,AddCircleBean.class);
                   adapter.add(position);
               }
           }
       });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_circle;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void setDataSuccess(Object data) {
        if (data instanceof CircleBean) {
            CircleBean circleBean= (CircleBean) data;
            List<CircleBean.ResultBean> result = circleBean.getResult();
                if (page==1){
                    adapter.setList(result);
                }else{
                    adapter.addList(result);
                }
                page++;
                frag_circle_xrecy.loadMoreComplete();
                frag_circle_xrecy.refreshComplete();
        }else if (data instanceof AddCircleBean){
            AddCircleBean addCircleBean= (AddCircleBean) data;
            if (addCircleBean.getMessage().equals("请先登陆")){
                Toast.makeText(getContext(),addCircleBean.getMessage(), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),addCircleBean.getMessage(), Toast.LENGTH_SHORT).show();
                circle();
            }
        }
    }
    private void circle() {
        presenter.setRequestget(String.format(Apis.CIRCLE_URL,page,COUNT),CircleBean.class);
    }
   /* private void addCircle(int id) {
        Map<String,String> map=new HashMap<>();
        map.put("circleId",id+"");
        presenter.setRequestData(Apis.ADDCRICLE_URI,map,AddCircleBean.class);
    }
    private void hideCircle(int id) {
       presenter.setRequestDelete(String.format(Apis.HIDECRICLE_URI,id),AddCircleBean.class);
    }*/
    //请求失败
    @Override
    public void setDataFail(String ex) {
        Log.i("TAG",ex);
    }
}
