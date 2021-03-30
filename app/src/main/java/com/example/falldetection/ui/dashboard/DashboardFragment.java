package com.example.falldetection.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.falldetection.CoreActivity;
import com.example.falldetection.DataStorage;
import com.example.falldetection.DetailActivity;
import com.example.falldetection.ListAdapter;
import com.example.falldetection.R;
import com.example.falldetection.RecordData;

import java.sql.Struct;
import java.util.LinkedList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private List<RecordData> myData = null;
    private Context myContext;
    private ListAdapter myAdapter = null;
    private ListView record;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    //
    public void onActivityCreated(@NonNull Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

//        record = getActivity().findViewById(R.id.record_lv);
//        ListAdapter data = new ListAdapter(this.getContext());
//        record.setAdapter(data);

        //
        //view部分未写
        //

        //获取当前布局的上下文
        myContext = getActivity();
        //获取控件ListView
        record = getActivity().findViewById(R.id.record_lv);
        //获取监测数据
        myData = CoreActivity.recordData;
        if(myData.size() == 0){
            //无记录时的逻辑
        }
//        String data = dataStorage.load();
//        myData.add(new RecordData(0, data, 0));
        //实例化适配器Adapter
        myAdapter = new ListAdapter((LinkedList<RecordData>) myData, myContext);
        //向容器中嵌入适配器的布局
        record.setAdapter(myAdapter);

        //设置点击事件
        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "点击position："+position, Toast.LENGTH_LONG).show();
                String isRight = "";
                if(myData.get(position).getState() == R.drawable.true02){
                    isRight = "+true";
                }else if(myData.get(position).getState() == R.drawable.false02){
                    isRight = "+false";
                }
                String data = myData.get(position).getTime() + isRight;
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                detailIntent.putExtra("data", data);
                startActivity(detailIntent);
            }
        });

    }

}