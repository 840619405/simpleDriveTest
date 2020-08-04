package com.tqc.hnkj.drivingtest.utils;
import android.graphics.Bitmap;
public class BitmapRequest {
    public BitmapRequest(String url, CallBack CallBackl) {
        this.url = url;
        this.callBackl=CallBackl;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    private String url;
    CallBack callBackl;
    public CallBack getCallBackl() {
        return callBackl;
    }
    public void setCallBackl(CallBack callBackl) {
        this.callBackl = callBackl;
    }
    public interface CallBack{
        void onSuccess(Bitmap bitmap);
        void onError(String reult);
    }
}
