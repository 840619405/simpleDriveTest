package com.tqc.hnkj.drivingtest.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.entity.ErrEntity;

public class ItemErrAdapter extends BaseAdapter {
    private List<ErrEntity> objects;
    private Context context;
    private LayoutInflater layoutInflater;
    public ItemErrAdapter(Context context,List<ErrEntity> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ErrEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_err, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ErrEntity)getItem(position), (ViewHolder) convertView.getTag(),position);
        return convertView;
    }
    private void initializeViews(ErrEntity object, ViewHolder holder, int position) {
        holder.post.setText((position+1)+"");
        holder.tvCount.setText(object.getNum());
        holder.tvLeixin.setText(object.getType());
    }
    protected class ViewHolder {
        private TextView post;
        private TextView tvLeixin;
        private TextView tvCount;
        public ViewHolder(View view) {
            post = (TextView) view.findViewById(R.id.post);
            tvLeixin = (TextView) view.findViewById(R.id.tv_leixin);
            tvCount = (TextView) view.findViewById(R.id.tv_count);
        }
    }
}
