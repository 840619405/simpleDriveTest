package com.tqc.hnkj.drivingtest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.entity.TestEntity;

public class ItemDialogAdapter extends BaseAdapter {
    private List<TestEntity.ResultBean> objects;
    private Context context;
    private LayoutInflater layoutInflater;
    List<Integer> unmArray;
    public ItemDialogAdapter(Context context, List<TestEntity.ResultBean> list, List<Integer> unmArray) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=list;
        this.unmArray=unmArray;
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
            convertView = layoutInflater.inflate(R.layout.item_dialog, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((TestEntity.ResultBean)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }

    private void initializeViews(TestEntity.ResultBean object, ViewHolder holder, int position) {
        holder.tvNum.setText((position+1)+"");
        holder.itemLayout.setBackgroundResource(R.drawable.ic_yuan_white);
        if (object.isState()){
            if (object.isResult()){
                holder.itemLayout.setBackgroundResource(R.drawable.ic_yuan_black);
            }else {
                holder.itemLayout.setBackgroundResource(R.drawable.ic_yuan_red);
            }
        }
        if (position==unmArray.get(0)){
            holder.itemLayout.setBackgroundResource(R.drawable.ic_yuan_gray);
        }
    }

    protected class ViewHolder {
        private LinearLayout itemLayout;
        private TextView tvNum;
        public ViewHolder(View view) {
            itemLayout = (LinearLayout) view.findViewById(R.id.item_layout);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
        }
    }
}
