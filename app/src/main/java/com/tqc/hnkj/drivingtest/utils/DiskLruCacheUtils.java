package com.tqc.hnkj.drivingtest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.tqc.hnkj.drivingtest.entity.TestEntity;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DiskLruCacheUtils {
    DiskLruCache mDiskLruCache;
    DownloadUrlToStream mDownloadUrlToStream;
    Context context;
    public DiskLruCacheUtils(Context context) {
        this.context = context;
        initDiskLruCache();
    }
    //打开缓存
    private void initDiskLruCache(){
        File cacheDir = getDiskCacheDir(context,"bitmap");
        if (!cacheDir.exists()){
            cacheDir.mkdir();
        }
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir,getAppVersion(context),1,1024*1024*100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取版本号
    private int getAppVersion(Context context) {

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    //获取缓存路径
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    //缓存图片
    public  void cacheBitmap(TestEntity.ResultBean resultBean){
        String imageUrl=resultBean.getUrl();
        String key= GetMd5Utils.getMD5String(imageUrl);
        try {
            DiskLruCache.Editor editor=mDiskLruCache.edit(key);
            if (editor!=null){
                OutputStream outputStream = editor.newOutputStream(0);
                if (mDownloadUrlToStream==null){
                    mDownloadUrlToStream=new DownloadUrlToStream();
                }
                if (mDownloadUrlToStream.downloadUrlToStream(resultBean.getUrl(),outputStream)){
                    Log.i("TAG", "缓存成功");
                    editor.commit();
                }else {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取图片
    public InputStream getBitmap(String key) throws IOException {
        key=GetMd5Utils.getMD5String(key);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot==null){
            return null;
        }
        return snapshot.getInputStream(0);
    }
}
