package com.yyhd.joke.api;


import com.google.gson.Gson;
import com.yyhd.joke.api.response.BaseResponse;
import com.yyhd.joke.utils.BizContant;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import common.bean.ErrorMsg;
import common.utils.BLog;
import common.utils.JsonUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cxw on 2016/9/1.
 */
public class SGApiUtils {
    private static SGApiUtils ourInstance = new SGApiUtils();
    private final SGHttpInterceptor interceptor;
    private Retrofit retrofit;
    public SGApiService apiService;

    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 60;

    public static SGApiUtils getInstance() {
        return ourInstance;
    }

    private SGApiUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        interceptor = new SGHttpInterceptor(BLog.rootLogOn);
        builder.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BizContant.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(SGApiService.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setApiService(SGApiService apiService) {
        this.apiService = apiService;
    }

    public SGApiService getApiService() {
        return apiService;
    }

    public <W> void request(Observable<BaseResponse<W>> observable, final NetCallback<W> netCallback) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<W>>() {
                    @Override
                    public void onError(Throwable e) {
                        ErrorMsg errorMsg = new ErrorMsg(e.getMessage());
                        if (e instanceof java.net.ConnectException || e instanceof UnknownHostException) {
                            errorMsg.setMsg("网络连接失败");
                        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
                            errorMsg.setMsg("网络连接超时");
                        }
                        netCallback.onFailed(errorMsg);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<W> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            netCallback.onSuccessed(baseResponse.getData());
                        } else {
                            netCallback.onFailed(new ErrorMsg(baseResponse.getMessageCode(),baseResponse.getMessage()));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static RequestBody buildRequestBodyByJson(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static RequestBody buildRequestBodyByForm(String key) {
        return RequestBody.create( MediaType.parse("multipart/form-data"), key);
    }



    public static RequestBody buildRequestBodyByObj(Object obj) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtils
                .parseBean2json(obj));
    }

    public interface NetCallback<W> {
        void onFailed(ErrorMsg errorMsg);

        void onSuccessed(W w);
    }

    public SGHttpInterceptor getInterceptor() {
        return interceptor;
    }
}
