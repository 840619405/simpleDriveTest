package com.tqc.hnkj.drivingtest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.entity.GvItemEntity;

public class ItemFragmentAdapter extends BaseAdapter {
    private List<GvItemEntity> objects;
    private Context context;
    private LayoutInflater layoutInflater;
    public ItemFragmentAdapter(Context context, List<GvItemEntity> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public GvItemEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.fragment_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((GvItemEntity)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GvItemEntity object, ViewHolder holder) {
        holder.ivFragmentItem.setImageResource(object.getIv());
        holder.tvFragmentItem.setText(object.getText());
    }

    protected class ViewHolder {
        private ImageView ivFragmentItem;
        private TextView tvFragmentItem;
        public ViewHolder(View view) {
            ivFragmentItem = (ImageView) view.findViewById(R.id.iv_fragment_item);
            tvFragmentItem = (TextView) view.findViewById(R.id.tv_fragment_item);
        }
    }
}
