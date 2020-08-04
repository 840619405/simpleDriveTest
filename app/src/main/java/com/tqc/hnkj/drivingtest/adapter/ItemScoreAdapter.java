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
import com.tqc.hnkj.drivingtest.entity.ScoerEntity;

public class ItemScoreAdapter extends BaseAdapter {

    private List<ScoerEntity> objects;

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemScoreAdapter(Context context,List<ScoerEntity> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ScoerEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_score, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((ScoerEntity)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ScoerEntity object, ViewHolder holder) {
        holder.itemDeFen.setText(object.getScore());
        holder.itemPingJai.setText(object.getQualifier());
        holder.itemTime.setText(object.getTime());
        holder.itemYongShi.setText(object.getDiration());
    }

    protected class ViewHolder {
        private TextView itemDeFen;
        private TextView itemYongShi;
        private TextView itemPingJai;
        private TextView itemTime;
        public ViewHolder(View view) {
            itemDeFen = (TextView) view.findViewById(R.id.item_deFen);
            itemYongShi = (TextView) view.findViewById(R.id.item_yongShi);
            itemPingJai = (TextView) view.findViewById(R.id.item_pingJai);
            itemTime = (TextView) view.findViewById(R.id.item_time);
        }
    }
}
