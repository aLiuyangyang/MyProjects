package com.example.dell.myprojects.model;

import com.example.dell.myprojects.callback.MyCallback;
import com.example.dell.myprojects.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class Modellmpl implements Model{
    @Override
    public void setRequestData(String path, Map<String, String> map, final Class clazz, final MyCallback myCallback) {
 //post
        RetrofitManager.getInstance().post(path,map,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                myCallback.setDataSuccess(o);
            }

            @Override
            public void onFail(String ex) {
                   myCallback.setDataFail(ex);
            }
        });

    }
//get请求
    @Override
    public void setRequestget(String path, final Class clazz, final MyCallback myCallback) {
        RetrofitManager.getInstance().get(path,new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data,clazz);
                myCallback.setDataSuccess(o);
            }

            @Override
            public void onFail(String ex) {
                myCallback.setDataFail(ex);
            }
        });
    }
//delete
    @Override
    public void setRequestDelete(String path, final Class clazz, final MyCallback myCallback) {
        RetrofitManager.getInstance().delete(path, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data,clazz);
                myCallback.setDataSuccess(o);
            }

            @Override
            public void onFail(String error) {
                 myCallback.setDataFail(error);
            }
        });
    }
}
