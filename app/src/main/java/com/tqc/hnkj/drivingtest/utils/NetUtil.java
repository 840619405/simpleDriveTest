package com.tqc.hnkj.drivingtest.utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class NetUtil {
    Handler handler;
    ExecutorService service;
    public NetUtil() {
        handler=new Handler();
        service= Executors.newCachedThreadPool();
    }
    private static strictfp class Instance{
        static NetUtil netUtil=new NetUtil();
    }
    public static NetUtil Instance(){
        return Instance.netUtil;
    }
    public void addRequest(StringRequest sr){
        service.execute(new RequestTask(sr));
    }
    public void addRequest(BitmapRequest br){
        service.execute(new RequestBimet(br));
    }
    public synchronized String senByPost(String url,String json)throws ConnectException{
        URL url1=null;
        HttpURLConnection conn=null;
        BufferedWriter bfw=null;
        BufferedReader bfr=null;
        String result="";
        Log.i("TAG", "senByPost: "+url);
        try {
            url1=new URL(url);
            conn= (HttpURLConnection) url1.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            bfr=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line=null;
            while ((line=bfr.readLine())!=null){
                result +=line;
            }
            return result;
        } catch (Exception e) {
            throw new ConnectException("网络错误");
        }finally {
            if (bfr!=null){
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bfw!=null){
                try {
                    bfw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class RequestTask implements Runnable{
        StringRequest sr;
        public RequestTask(StringRequest sr) {
            this.sr = sr;
        }
        @Override
        public void run() {
            String url=sr.getUri();
            String json=sr.getJson();
            try {
                String s = senByPost(url, json);
                onSueecss(s,true);
            } catch (ConnectException e) {
                onSueecss(e.toString(),false);
            }
        }
        public void onSueecss(final String result, final boolean b){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (b){
                        sr.getCallBack().onSueecss(result);
                    }else sr.getCallBack().onError(result);
                    sr.getCallBack().onFinish();
                }
            });
        }
    }
    public Bitmap senBypost(String url)throws ConnectException{
        URL url1=null;
        HttpURLConnection conn=null;
        BufferedWriter bfw=null;
        BufferedReader bfr=null;
        InputStream is=null;
        try {
            url1=new URL(url);
            conn= (HttpURLConnection) url1.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            is = conn.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            throw new ConnectException(url);
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bfw != null) {
                try {
                    bfw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class RequestBimet implements Runnable {
        BitmapRequest br;
        public RequestBimet(BitmapRequest br) {
            this.br=br;
        }
        public void run() {
            try {
                Bitmap bitmap = senBypost(br.getUrl());
                success(bitmap,null);
            } catch (ConnectException e) {
                success(null,br.getUrl());
            }
        }
        public void success(final Bitmap bitmap, final String reuslt){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (bitmap==null){
                        br.getCallBackl().onError(reuslt);
                    }else {
                        br.getCallBackl().onSuccess(bitmap);
                    }
                }
            });
        }
    }
}
