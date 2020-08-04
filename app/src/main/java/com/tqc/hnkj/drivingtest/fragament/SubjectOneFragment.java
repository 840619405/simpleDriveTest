package com.tqc.hnkj.drivingtest.fragament;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tqc.hnkj.drivingtest.R;
import com.tqc.hnkj.drivingtest.activity.ErrActivity;
import com.tqc.hnkj.drivingtest.activity.OrderActivity;
import com.tqc.hnkj.drivingtest.activity.MainActivity;
import com.tqc.hnkj.drivingtest.activity.ScoreActivity;
import com.tqc.hnkj.drivingtest.activity.SimulationActivity;
import com.tqc.hnkj.drivingtest.adapter.ItemFragmentAdapter;
import com.tqc.hnkj.drivingtest.entity.GvItemEntity;

import java.util.ArrayList;
import java.util.List;

public class SubjectOneFragment extends Fragment  {
    private GridView gvFragment;
    int coed;
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subject_one, null);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gvFragment = (GridView) view.findViewById(R.id.gv_fragment);
        initGridView();
        mainActivity = (MainActivity)getActivity();
    }
    private void initGridView(){
        List<GvItemEntity> list=new ArrayList<>();
        list.add(new GvItemEntity(R.mipmap.icon_order,"顺序答题"));
        list.add(new GvItemEntity(R.mipmap.icon_random,"模拟测试"));
        list.add(new GvItemEntity(R.mipmap.icon_error,"我的错题"));
        list.add(new GvItemEntity(R.mipmap.icon_mark,"我的成绩"));
        ItemFragmentAdapter fa=new ItemFragmentAdapter(getContext(),list);
        gvFragment.setAdapter(fa);
        gvFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                 int subject;
        String model;
        String testType;
                 */
                coed = mainActivity.PAGER_COED;
                switch (position){
                    case 0:
                        Intent it=new Intent();
                        it.putExtra("subject",coed);
                        it.putExtra("model","c1");
                        it.putExtra("testType","order");
                        it.setClass(getContext(), OrderActivity.class);
                        getActivity().startActivity(it);
                        break;
                    case 1:
                        Intent its=new Intent();
                        its.putExtra("subject",coed);
                        its.putExtra("model","c1");
                        its.putExtra("testType","rand");
                        its.setClass(getContext(), SimulationActivity.class);
                        getActivity().startActivity(its);
                        break;
                    case 2:
                        Intent itss=new Intent();
                        itss.putExtra("subject",coed);
                        itss.setClass(getContext(), ErrActivity.class);
                        getActivity().startActivity(itss);
                        break;
                    case 3:
                        Intent itsss=new Intent();
                        itsss.putExtra("subject",coed);
                        itsss.setClass(getContext(), ScoreActivity.class);
                        getActivity().startActivity(itsss);
                        break;
                }
            }
        });
    }
}
