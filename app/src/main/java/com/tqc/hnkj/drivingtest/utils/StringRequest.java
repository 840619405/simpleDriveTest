package com.tqc.hnkj.drivingtest.utils;
public class StringRequest {
    String uri;
    String json;
    CallBack callBack;
    public StringRequest(String uri, String json, CallBack callBack) {
        this.uri = uri;
        this.json = json;
        this.callBack = callBack;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
        void onSueecss(String result);
        void onError(String result);
        void onFinish();
    }
}
