package com.example.dell.myprojects.model;

import com.example.dell.myprojects.callback.MyCallback;

import java.util.Map;

public interface Model{
    void setRequestData(String path, Map<String,String> map,Class clazz,MyCallback myCallback);
    void setRequestget(String path,Class clazz,MyCallback myCallback);
    void setRequestDelete(String path,Class clazz,MyCallback myCallback);
}
