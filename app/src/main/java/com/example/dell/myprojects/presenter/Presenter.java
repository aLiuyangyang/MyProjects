package com.example.dell.myprojects.presenter;

import com.example.dell.myprojects.callback.MyCallback;

import java.util.Map;

public interface Presenter {
    void setRequestData(String path, Map<String,String> map,Class clazz);
    void setRequestget(String path,Class clazz);
    void setRequestDelete(String path,Class clazz);
}
