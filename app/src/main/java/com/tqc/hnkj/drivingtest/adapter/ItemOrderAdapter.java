package com.tqc.hnkj.drivingtest.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.entity.TestEntity;
import com.tqc.hnkj.drivingtest.utils.BitmapRequest;
import com.tqc.hnkj.drivingtest.utils.DiskLruCacheUtils;
import com.tqc.hnkj.drivingtest.utils.NetUtil;
import com.tqc.hnkj.drivingtest.utils.NewsInterface;
import com.tqc.hnkj.drivingtest.view.ViewViewFlipper;

public class ItemOrderAdapter extends BaseAdapter {
    private List<TestEntity.ResultBean> objects;
    private Context context;
    private LayoutInflater layoutInflater;
    private ViewViewFlipper moreItemsListView;
    private ObjectAnimator left,right,empty;
    private int startX,startY;
    private int tL_COED;
    NewsInterface newsInterface;
    private String result;
    private boolean a,b,c,d;
    DiskLruCacheUtils diskLruCacheUtils;
    public ItemOrderAdapter(Context context, List<TestEntity.ResultBean> objects, ViewViewFlipper moreItemsListView, ObjectAnimator left, ObjectAnimator right, ObjectAnimator empty, int screenWidth, NewsInterface newsInterface, DiskLruCacheUtils diskLruCacheUtils) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
        this.moreItemsListView=moreItemsListView;
        this.left=left;
        this.right=right;
        this.empty=empty;
        this.newsInterface= newsInterface;
        this.diskLruCacheUtils=diskLruCacheUtils;
    }
    @Override
    public int getCount() {
        return objects.size();
    }
    @Override
    public TestEntity.ResultBean getItem(int position) {
        return objects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_order, parent,false);
            ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((TestEntity.ResultBean)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }
    private void initializeViews(TestEntity.ResultBean object, final ViewHolder holder, int position) {
        Log.i("TAG", "initializeViews: "+object.getAnswer());
        a=false;
        b=false;
        c=false;
        d=false;
        //加载滑动
        slideScrollView(holder);
        //加载初始化数据
        iniView(object,holder,position);
        //根据答案变成字符串，方便比较
        analysisAnswer(object);
        //判断试题是否做过
        if (!object.isState()){
            //没做过即启用选项按钮
            setOnOption(holder);
            //判断是单选题还是多选题
            if (Integer.parseInt(object.getAnswer())<=4) {
                //单选题
                singleAnswer(object, holder, position);
            }else {
                //多选题
                manyAnswer(object,holder,position);
            }
        }else {
            //隐藏提交答案按钮
            holder.btn_ok.setVisibility(View.GONE);
            //如果做过即不可在选择
            setOffOption(holder);
            //判断是否做对了
            if (object.isResult()){
                //做对了即把正确答案显示为蓝色
                setOnAnswer(object,holder,position);
            }else {
                //做错了即显示正确答案为蓝色，错误答案为红色,没有选择的答案为黄色
                setOffAnswer(object,holder,position);
            }
        }
    }
    //多选题操作
    private void manyAnswer(final TestEntity.ResultBean object, final ViewHolder holder, final int position) {
        holder.tvItemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!a){
                    object.setA(true);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemA.setTextColor(Color.parseColor("#D9D9D9"));
                    a=true;
                }else {
                    object.setA(false);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemA.setTextColor(Color.BLACK);
                    a=false;
                }
            }
        });
        holder.tvItemB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!b){
                    object.setB(true);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemB.setTextColor(Color.parseColor("#D9D9D9"));
                    b=true;
                }else {
                    object.setB(false);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemB.setTextColor(Color.BLACK);
                    b=false;
                }
            }
        });
        holder.tvItemC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!c){
                    object.setC(true);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemC.setTextColor(Color.parseColor("#D9D9D9"));
                    c=true;
                }else {
                    object.setC(false);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemC.setTextColor(Color.BLACK);
                    c=false;
                }
            }
        });
        holder.tvItemD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!d){
                    object.setD(true);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemD.setTextColor(Color.parseColor("#D9D9D9"));
                    d=true;
                }else {
                    object.setD(false);//选择了A
                    objects.set(position,object);//更改数据
                    holder.tvItemD.setTextColor(Color.BLACK);
                    d=false;
                }
            }
        });
        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setState(true);
                objects.set(position,object);
                isAnswerOnAndOff(object,holder,position);
                ItemOrderAdapter.this.notifyDataSetChanged();
            }
        });
    }
    //选择答案错误
    private void setOffAnswer(TestEntity.ResultBean object, ViewHolder holder, int position) {
        char[] chars = result.toCharArray();
        setViewColor(holder,chars);
        holder.tvItemTimo.append("(错误)");
        holder.tvItemTimo.setTextColor(Color.RED);
        String string = getString(object);
        //判断题目是单选还是多选
        if (Integer.parseInt(object.getAnswer())>4){
            String answer = getString(object);
            setViewColorYellow(holder,chars);
            compareAnswer(answer,chars,object,holder);

        }else {
            //单选就把单选的答案变成红色
            switch (string){
                case "A":
                    holder.tvItemA.setTextColor(Color.RED);
                    break;
                case "B":
                    holder.tvItemB.setTextColor(Color.RED);
                    break;
                case "C":
                    holder.tvItemC.setTextColor(Color.RED);
                    break;
                case "D":
                    holder.tvItemD.setTextColor(Color.RED);
                    break;
            }
        }
        holder.linearLayout.setVisibility(View.VISIBLE);
    }
    //多选题选项的颜色
    private void compareAnswer(String answer, char[] chars, TestEntity.ResultBean object, ViewHolder holder) {
        boolean yes=false;
        char[] chars1 = answer.toCharArray();
        for (int i = 0; i <chars1.length ; i++) {
            for (int j = 0; j <chars.length ; j++) {
                if ((chars1[i]+"").equals((chars[j]+""))){
                    yes=true;
                }
            }
            if (yes){
                switch (chars1[i]+""){
                    case "A":
                        holder.tvItemA.setTextColor(Color.BLUE);
                        break;
                    case "B":
                        holder.tvItemB.setTextColor(Color.BLUE);
                        break;
                    case "C":
                        holder.tvItemC.setTextColor(Color.BLUE);
                        break;
                    case "D":
                        holder.tvItemD.setTextColor(Color.BLUE);
                        break;

                }
                yes=false;
            }else {
                switch (chars1[i]+""){
                    case "A":
                        holder.tvItemA.setTextColor(Color.RED);
                        break;
                    case "B":
                        holder.tvItemB.setTextColor(Color.RED);
                        break;
                    case "C":
                        holder.tvItemC.setTextColor(Color.RED);
                        break;
                    case "D":
                        holder.tvItemD.setTextColor(Color.RED);
                        break;

                }
            }
        }
    }
    //将所有选择项变成黄色(多选题没有被选中的答案)
    private void setViewColorYellow(ViewHolder holder, char[] chars) {
        for (int i = 0; i <chars.length ; i++) {
            switch (chars[i]){
                case 'A':
                    holder.tvItemA.setTextColor(Color.YELLOW);
                    break;
                case 'B':
                    holder.tvItemB.setTextColor(Color.YELLOW);
                    break;
                case 'C':
                    holder.tvItemC.setTextColor(Color.YELLOW);
                    break;
                case 'D':
                    holder.tvItemD.setTextColor(Color.YELLOW);
                    break;
            }
        }
    }
    //选择答案正确
    private void setOnAnswer(TestEntity.ResultBean object, ViewHolder holder, int position) {
        char[] chars = result.toCharArray();
        holder.tvItemTimo.append("(正确)");
        holder.tvItemTimo.setTextColor(Color.BLUE);
        setViewColor(holder, chars);
    }
    //设置正确答案为蓝色
    private void setViewColor(ViewHolder holder, char[] chars) {
        for (int i = 0; i <chars.length ; i++) {
            switch (chars[i]){
                case 'A':
                    holder.tvItemA.setTextColor(Color.BLUE);
                    break;
                case 'B':
                    holder.tvItemB.setTextColor(Color.BLUE);
                    break;
                case 'C':
                    holder.tvItemC.setTextColor(Color.BLUE);
                    break;
                case 'D':
                    holder.tvItemD.setTextColor(Color.BLUE);
                    break;
            }
        }
    }
    /*
    单选题选项处理
     */
    private void singleAnswer(final TestEntity.ResultBean object, final ViewHolder holder, final int position) {
        setOnOption(holder);
        holder.tvItemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setState(true);//设置这题已做
                setOffOption(holder);//设置选项不可选了（单选）
                object.setA(true);//选择了A
                objects.set(position,object);//更改数据
                //isOnOff(object,holder,position);
                isAnswerOnAndOff(object,holder,position);//判断答案是否正确
                ItemOrderAdapter.this.notifyDataSetChanged();//更新界面
            }
        });
        holder.tvItemB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setState(true);
                setOffOption(holder);
                object.setB(true);
                objects.set(position,object);
                //isOnOff(object,holder,position);
                isAnswerOnAndOff(object,holder,position);

                ItemOrderAdapter.this.notifyDataSetChanged();
            }
        });
        holder.tvItemC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setState(true);
                setOffOption(holder);
                object.setC(true);
                objects.set(position,object);
                //isOnOff(object,holder,position);
                isAnswerOnAndOff(object,holder,position);

                ItemOrderAdapter.this.notifyDataSetChanged();
            }
        });
        holder.tvItemD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setState(true);
                setOffOption(holder);
                object.setD(true);
                objects.set(position,object);
                //isOnOff(object,holder,position);
                isAnswerOnAndOff(object,holder,position);
                ItemOrderAdapter.this.notifyDataSetChanged();
            }
        });
    }
    /*
    判断选择的答案是否正确
     */
    private void isAnswerOnAndOff(TestEntity.ResultBean object, ViewHolder holder, int position) {
        String answer = getString(object);
        if (answer.equals(result)){
            object.setResult(true);
            objects.set(position,object);
            newsInterface.onOKPlus();
            nextQuestion();
        }else {
            newsInterface.onNoPlus();
        }
    }
    //得到用户所选的答案
    @NonNull
    private String getString(TestEntity.ResultBean object) {
        String answer="";
        if (object.isA()){
            answer +="A";
        }
        if (object.isB()){
            answer +="B";
        }
        if (object.isC()){
            answer +="C";
        }
        if (object.isD()){
            answer +="D";
        }
        return answer;
    }
    /*
    设置选项不能选择
     */
    private void setOffOption(ViewHolder holder){
        holder.tvItemA.setEnabled(false);
        holder.tvItemB.setEnabled(false);
        holder.tvItemC.setEnabled(false);
        holder.tvItemD.setEnabled(false);
    }
    /*
    设置选项可以选择
     */
    private void setOnOption(ViewHolder holder){
        holder.tvItemA.setEnabled(true);
        holder.tvItemB.setEnabled(true);
        holder.tvItemC.setEnabled(true);
        holder.tvItemD.setEnabled(true);
    }
    /*
    根据json数据的答案判断出答案是A、B、C、D
     */
    private void analysisAnswer(TestEntity.ResultBean object){
        switch (object.getAnswer()){
            case "1":
                result="A";
                break;
            case "2":
                result="B";
                break;
            case "3":
                result="C";
                break;
            case "4":
                result="D";
                break;
            case "7":
                result="AB";
                break;
            case "8":
                result="AC";
                break;
            case "9":
                result="AD";
                break;
            case "10":
                result="BC";
                break;
            case "11":
                result="BD";
                break;
            case "12":
                result="CD";
                break;
            case "13":
                result="ABC";
                break;
            case "14":
                result="ABD";
                break;
            case "15":
                result="ACD";
                break;
            case "16":
                result="BCD";
                break;
            case "17":
                result="ABCD";
                break;
        }
    }
    /*
    初始化控件数据
     */
    private void iniView(TestEntity.ResultBean object, ViewHolder holder, int position) {
        //加载通用数据
        holder.linearLayout.setVisibility(View.GONE);
        holder.tvJieShi.setText("答案解析:\n"+object.getExplains());
        holder.tvJieShi.setTextColor(Color.RED);
        holder.tvItemTimo.setText(object.getQuestion());
        holder.tvItemA.setTextColor(Color.BLACK);
        holder.tvItemB.setTextColor(Color.BLACK);
        holder.tvItemC.setTextColor(Color.BLACK);
        holder.tvItemD.setTextColor(Color.BLACK);
        holder.tvItemTimo.setTextColor(Color.BLACK);
        /*
        判断是选择提还是判断题
         */
        if (object.getItem3().equals("")){
            holder.tvItemA.setText("A.正确");
            holder.tvItemB.setText("B.错误");
            holder.tvItemC.setVisibility(View.GONE);
            holder.tvItemD.setVisibility(View.GONE);
        }else {
            holder.tvItemA.setText("A."+object.getItem1());
            holder.tvItemB.setText("B."+object.getItem2());
            holder.tvItemC.setText("C."+object.getItem3());
            holder.tvItemD.setText("D."+object.getItem4());
            holder.tvItemC.setVisibility(View.VISIBLE);
            holder.tvItemD.setVisibility(View.VISIBLE);
        }
        /*
        判断是否有图片
         */
        if (!object.getUrl().equals("")) {
            holder.ivTu.setVisibility(View.VISIBLE);
            /*
            判断图片是否已经加载
            */
            try {
                InputStream bitmap = diskLruCacheUtils.getBitmap(object.getUrl());
                if (bitmap!=null) {
                    holder.ivTu.setImageBitmap(BitmapFactory.decodeStream(bitmap));
                }else {
                    senGetBitem(object,holder,position);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*if (!object.getUrl().equals("")&&object.getBitmap()==null){
                senGetBitem(object,holder,position);
            }else {
                holder.ivTu.setImageBitmap(object.getBitmap());
            }*/
        }else {
            holder.ivTu.setVisibility(View.GONE);
        }
        /*
        判断多选还是单选
         */
        if ((Integer.parseInt(object.getAnswer()))>4){
            holder.btn_ok.setVisibility(View.VISIBLE);
            holder.tvItemTimo.append("(多选题)");
        }else   holder.btn_ok.setVisibility(View.GONE);
    }
    /*
    设置滑动手势，左右滑动上、下题
     */
    private void slideScrollView(ViewHolder holder) {
        holder.scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY= (int) event.getY();
                        startX = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();
                        int moveY=Math.abs(startY-endY);
                        int moveX=Math.abs(startX-endX);
                        if ((startX - endX) > 200&&moveX>moveY) {
                            tL_COED = 0;
                        } else if (( endX-startX ) > 200&&moveX>moveY) {
                            tL_COED = -1;
                        } else {
                            tL_COED = 2;
                            return false;
                        }
                        switch (tL_COED) {
                            case -1:
                                previousQuestion();
                                break;
                            case 0:
                                nextQuestion();
                                break;
                        }
                        break;
                }
                return false;
            }
        });
    }
    /*
    上一题
     */
    private void previousQuestion() {
        moreItemsListView.setInAnimation(empty);
        moreItemsListView.setOutAnimation(right);
        moreItemsListView.showPrevious();
        newsInterface.onPreviousPage(moreItemsListView.getDisplayedChild()+1,objects.size());
        newsInterface.onArray(objects);
    }
    /*
    下一题
     */
    private void nextQuestion() {
        moreItemsListView.setInAnimation(empty);
        moreItemsListView.setOutAnimation(left);
        moreItemsListView.showNext();
        newsInterface.onNextPage(moreItemsListView.getDisplayedChild()+1,objects.size());
        newsInterface.onArray(objects);
    }
    /*
    根据url请求网络图片
     */
    private void senGetBitem(final TestEntity.ResultBean object, final ViewHolder holder, final int position) {
        BitmapRequest br=new BitmapRequest(object.getUrl(), new BitmapRequest.CallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                holder.ivTu.setImageBitmap(bitmap);
            }

            @Override
            public void onError(String reult) {
                //senGetBitem(object,holder,position);
                Toast.makeText(context, "当前网络状态不好，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
        NetUtil.Instance().addRequest(br);
        new Thread(new Runnable() {
            @Override
            public void run() {
                diskLruCacheUtils.cacheBitmap(object);
            }
        }).start();
    }
    protected class ViewHolder {
        private TextView tvItemTimo;
        private ImageView ivTu;
        private TextView tvItemA;
        private TextView tvItemB;
        private TextView tvItemC;
        private TextView tvItemD;
        private TextView tvDaAn;
        private TextView tvJieShi;
        private LinearLayout linearLayout;
        ViewViewFlipper viewViewFlipper;
        private ScrollView scrollView;
        private View view;
        private Button btn_ok;
        public ViewHolder(View view) {
            this.view=view;
            scrollView=view.findViewById(R.id.item_scrollView);
            tvItemTimo = (TextView) view.findViewById(R.id.tv_item_timo);
            ivTu = (ImageView) view.findViewById(R.id.iv_tu);
            tvItemA = (TextView) view.findViewById(R.id.tv_item_A);
            tvItemB = (TextView) view.findViewById(R.id.tv_item_B);
            tvItemC = (TextView) view.findViewById(R.id.tv_item_C);
            tvItemD = (TextView) view.findViewById(R.id.tv_item_D);
            tvDaAn=view.findViewById(R.id.tv_daan);
            tvJieShi=view.findViewById(R.id.tv_jieshi);
            linearLayout=view.findViewById(R.id.layout_item);
            btn_ok=view.findViewById(R.id.item_btn_OK);
        }
    }
}
