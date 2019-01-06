package com.example.dell.myprojects.presenter;

import com.example.dell.myprojects.callback.MyCallback;
import com.example.dell.myprojects.model.Modellmpl;
import com.example.dell.myprojects.view.IView;

import java.util.Map;

public class PresenterImpl implements Presenter{
    private IView iView;
    private Modellmpl modellmpl;

    public PresenterImpl(IView iView) {
        this.iView = iView;
        modellmpl=new Modellmpl();
    }

    @Override
    public void setRequestData(String path, Map<String, String> map, Class clazz) {
        modellmpl.setRequestData(path, map, clazz, new MyCallback() {
            @Override
            public void setDataSuccess(Object data) {
                iView.setDataSuccess(data);
            }

            @Override
            public void setDataFail(String ex) {
                   iView.setDataFail(ex);
            }
        });
    }

    @Override
    public void setRequestget(String path, Class clazz) {
        modellmpl.setRequestget(path, clazz, new MyCallback() {
            @Override
            public void setDataSuccess(Object data) {
                iView.setDataSuccess(data);
            }

            @Override
            public void setDataFail(String ex) {
                 iView.setDataFail(ex);
            }
        });
    }

    @Override
    public void setRequestDelete(String path, Class clazz) {
        modellmpl.setRequestDelete(path, clazz, new MyCallback() {
            @Override
            public void setDataSuccess(Object data) {
                iView.setDataSuccess(data);
            }

            @Override
            public void setDataFail(String ex) {
               iView.setDataFail(ex);
            }
        });
    }

    public void seDestroy(){
        if (iView!=null){
            iView=null;
        }
        if (modellmpl!=null){
            modellmpl=null;
        }
    }
}
