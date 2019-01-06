package com.example.dell.myprojects.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutResId();
}
