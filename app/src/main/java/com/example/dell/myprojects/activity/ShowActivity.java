package com.example.dell.myprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.dell.myprojects.R;
import com.example.dell.myprojects.fragment.CircleFragment;
import com.example.dell.myprojects.fragment.HomeFragemnt;
import com.example.dell.myprojects.fragment.MineFragment;
import com.example.dell.myprojects.fragment.OrderFragment;
import com.example.dell.myprojects.fragment.ShoppingFragment;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends BaseActivity implements View.OnClickListener{

    private Button show_image_home;
    private Button show_image_circle;
    private Button show_image_shopping;
    private Button show_image_order;
    private Button show_image_mine;
    private FragmentTransaction transaction;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        show_image_home =  findViewById(R.id.show_image_home);
        show_image_home.setOnClickListener(this);
        show_image_circle =  findViewById(R.id.show_image_circle);
        show_image_circle.setOnClickListener(this);
        show_image_shopping =  findViewById(R.id.show_image_shopping);
        show_image_shopping.setOnClickListener(this);
        show_image_order =findViewById(R.id.show_image_order);
        show_image_order.setOnClickListener(this);
        show_image_mine = findViewById(R.id.show_image_mine);
        show_image_mine.setOnClickListener(this);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.show_view_pager,new HomeFragemnt()).commit();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show;
    }

    @Override
    public void onClick(View v) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
                    case R.id.show_image_home:
                        transaction.replace(R.id.show_view_pager,new HomeFragemnt()).commit();
                        break;
                    case R.id.show_image_circle:
                        transaction.replace(R.id.show_view_pager,new CircleFragment()).commit();
                        break;
                    case R.id.show_image_shopping:
                        transaction.replace(R.id.show_view_pager,new ShoppingFragment()).commit();
                        break;
                    case R.id.show_image_order:
                        transaction.replace(R.id.show_view_pager,new OrderFragment()).commit();
                        break;
                    case R.id.show_image_mine:
                        transaction.replace(R.id.show_view_pager,new MineFragment()).commit();
                        break;
                    default:break;
                }
    }

}
