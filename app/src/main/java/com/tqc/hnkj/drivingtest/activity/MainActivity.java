package com.tqc.hnkj.drivingtest.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import com.jakewharton.disklrucache.DiskLruCache;
import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.adapter.ViewPagerAdapter;
import com.tqc.hnkj.drivingtest.db.DBUtils;
import com.tqc.hnkj.drivingtest.entity.TestEntity;
import com.tqc.hnkj.drivingtest.fragament.SubjectOneFragment;
import com.tqc.hnkj.drivingtest.utils.DiskLruCacheUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager main_viewPager;
    private android.support.design.widget.TabLayout main_tab;
    public int PAGER_COED;
    //@SuppressLint("CommitPrefEdits")
    int[] kemu;
    private DiskLruCacheUtils diskLruCacheUtils;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences num = getSharedPreferences("cun", 0);
        int numInt = num.getInt("num", 0);
        initView();
        initViewPager();
        importDatabase();
        if (numInt<1){
            SharedPreferences.Editor edit = num.edit();
            edit.putInt("num",1);
            edit.commit();
            huanCunBitem();
        }
    }
    private void initView() {
        main_viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        main_tab = (android.support.design.widget.TabLayout) findViewById(R.id.main_tab);
    }
    private void initViewPager(){
        PAGER_COED=1;
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new SubjectOneFragment());
        fragments.add(new SubjectOneFragment());
        ViewPagerAdapter va=new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        main_viewPager.setAdapter(va);
        main_tab.addTab(main_tab.newTab());
        main_tab.addTab(main_tab.newTab());
        main_tab.setupWithViewPager(main_viewPager);
        main_tab.getTabAt(0).setText("科目一");
        main_tab.getTabAt(1).setText("科目四");
        main_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        PAGER_COED=1;
                        break;
                    case 1:
                        PAGER_COED=4;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //把已有的数据库文件移动到database
    public void importDatabase() {
        checkPermission();
        // 存放数据库的目录
        String dirPath = "/data/data/com.tqc.hnkj.drivingtest/databases";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 数据库文件
        File file = new File(dir, "topic.db");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // 加载需要导入的数据库
            InputStream is = this.getApplicationContext().getResources()
                    .openRawResource(R.raw.topic);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffere = new byte[is.available()];
            is.read(buffere);
            fos.write(buffere);
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    获取运行时读写文件权限
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT>=23){
            int i = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            if (i!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }
    //程序第一次运行时缓存图片
    private void huanCunBitem(){
        diskLruCacheUtils = new DiskLruCacheUtils(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                kemu=new int[2];
                kemu[0]=1;
                kemu[1]=4;
                for (int i = 0; i <kemu.length ; i++) {
                    List<TestEntity.ResultBean> resultBeans = DBUtils.queryTopic(MainActivity.this, "topic_questions", "subject=?", new String[]{kemu[i] + ""}, null);
                    for (int j = 0; j <resultBeans.size() ; j++) {
                        if (!resultBeans.get(j).getUrl().equals("")){
                            diskLruCacheUtils.cacheBitmap(resultBeans.get(j));
                        }
                    }
                }
            }
        }).start();

    }
}
