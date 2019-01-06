package com.example.dell.myprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.myprojects.R;
import com.example.dell.myprojects.bean.DetailsHotBean;
import com.example.dell.myprojects.fragment.HomeFragemnt;
import com.example.dell.myprojects.presenter.PresenterImpl;
import com.example.dell.myprojects.util.Apis;
import com.example.dell.myprojects.view.IView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;

public class DetalisActivity extends AppCompatActivity implements IView,View.OnClickListener{
    private PresenterImpl presenter;
    private WebView detalis_web;
    private Button detalis_but;
    private XBanner detalis_image;
    private TextView detalis_price, detalis_sale, detalis_name, detalis_weighnt;
    private ArrayList<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis);
        presenter = new PresenterImpl(this);
        initView();

        //页面展示
        Intent intent1 = getIntent();
        int hodid = intent1.getIntExtra("hodid", 0);
        presenter.setRequestget(String.format(Apis.DETALIS_URL, hodid), DetailsHotBean.class);
        presenter.setRequestget(String.format(Apis.DETALIS_URL, hodid), DetailsHotBean.class);
        presenter.setRequestget(String.format(Apis.DETALIS_URL, hodid), DetailsHotBean.class);
    }

    @Override
    public void setDataSuccess(Object data) {
        if (data instanceof DetailsHotBean) {
            DetailsHotBean detailsHotBean = (DetailsHotBean) data;
            DetailsHotBean.ResultBean result = detailsHotBean.getResult();
            detalis_name.setText(result.getCommodityName());
            detalis_price.setText("￥" + result.getPrice() + "");
            detalis_sale.setText("已售" + result.getSaleNum() + "件");
            detalis_web.loadDataWithBaseURL(null, detailsHotBean.getResult().getDetails(), "text/html", "utf-8", null);
            String[] split = detailsHotBean.getResult().getPicture().split("\\,");
            for (int i = 0; i < split.length; i++) {
                datas.add(split[i]);
            }
            if (!datas.isEmpty()) {
                detalis_image.setData(datas, null);
                detalis_image.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(DetalisActivity.this).load(datas.get(position)).into((ImageView) view);
                    }
                });
                //横向移动
                detalis_image.setPageTransformer(Transformer.Default);
                detalis_weighnt.setText("重量" + detailsHotBean.getResult().getWeight() + "kg");
            }
        }

    }

    @Override
    public void setDataFail(String ex) {
        Log.i("TAG", ex);
    }

    private void initView() {
        detalis_image = findViewById(R.id.detalis_image);
        detalis_price = (TextView) findViewById(R.id.detalis_price);
        detalis_sale = (TextView) findViewById(R.id.detalis_sale);
        detalis_name = (TextView) findViewById(R.id.detalis_name);
        detalis_web = findViewById(R.id.detalis_web);
        detalis_weighnt = findViewById(R.id.detalis_weighnt);
        detalis_but = findViewById(R.id.detalis_but);
        detalis_but.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter = null;
        }
        if (detalis_web != null) {
            detalis_web.setVisibility(View.GONE);
            detalis_web.removeAllViews();
            detalis_web.destroy();
        }
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.detalis_but:

              Intent intent11 = new Intent(this, HomeFragemnt.class);
              startActivity(intent11);
              break;
      }
    }
}
