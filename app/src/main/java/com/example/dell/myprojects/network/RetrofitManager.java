package com.example.dell.myprojects.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.dell.myprojects.util.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager{
    //域名
    private String MY_URL="http://mobile.bwstudent.com/small/";
    private static RetrofitManager mRetrofitManager;
     BaseApis baseApis;

    //单例
    public static synchronized RetrofitManager getInstance(){
       if (mRetrofitManager==null){
           mRetrofitManager=new RetrofitManager();
       }
       return mRetrofitManager;
    }

    public RetrofitManager(){
        //日志
       /* HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(10,TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
               //拿到请求
                Request request=chain.request();
                 //取出数据
                SharedPreferences sharedPreferences=App.getApplication().getSharedPreferences("User",Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                String sessionId= sharedPreferences.getString("sessionId", "");
                Request.Builder builder1=request.newBuilder();
                builder1.method(request.method(),request.body());
                //添加
                if (!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                    builder1.addHeader("userId",userId);
                    builder1.addHeader("sessionId",sessionId);
                }
                        Request reques=builder1.build();
                return chain.proceed(reques);
            }
        });
        builder.retryOnConnectionFailure(true);
        OkHttpClient client=builder.build();

        Retrofit retrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MY_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }
       //get请求
       public RetrofitManager get(String path,HttpListener listener){
           baseApis.get(path)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(getObserver(listener));
           return mRetrofitManager;
       }
      //delete
    public RetrofitManager delete(String path,HttpListener listener){
        baseApis.delete(path).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;
    }

    private Observer getObserver(final HttpListener listener) {
        Observer observer=new Observer<ResponseBody>(){

            //观察者
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if(listener!=null){
                        listener.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener!=null){
                        listener.onFail(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    public RetrofitManager post(String path,Map<String,String> map,HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.post(path,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;
    }

    public RetrofitManager postFormBoby(String path, Map<String, RequestBody> map, HttpListener listener){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.postFormBody(path,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
        return mRetrofitManager;
    }



    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }



}