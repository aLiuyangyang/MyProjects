package com.example.dell.myprojects.network;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis<T> {
    @GET
    Observable<ResponseBody> get(@Url String path);

    @POST
    Observable<ResponseBody> post(@Url String path, @QueryMap Map<String,String> map);

    @POST
    Observable<ResponseBody> postFormBody(@Url String url, @PartMap Map<String, RequestBody> requestBodyMap);

    @DELETE
    Observable<ResponseBody> delete(@Url String path);
}
