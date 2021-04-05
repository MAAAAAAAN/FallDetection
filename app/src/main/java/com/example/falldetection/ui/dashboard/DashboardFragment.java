package com.example.falldetection.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.falldetection.DetailActivity;
import com.example.falldetection.ListAdapter;
import com.example.falldetection.R;
import com.example.falldetection.RecordData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class DashboardFragment extends Fragment {

//    private List<RecordData> myData = null;
    private LinkedList<RecordData> myData = new LinkedList<RecordData>();
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

        //view部分
        //

        //获取当前布局的上下文
        myContext = getActivity();
        //获取控件ListView
        record = getActivity().findViewById(R.id.record_lv);
        //获取监测数据
//        myData = CoreActivity.recordData;
        InputStream inputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        String fallData = "";
        try {
            File file = new File("/data/data/com.example.falldetection/", "recordData.txt");
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(reader);
            StringBuilder data = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null){
                data.append(temp);
            }
            fallData = data.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(fallData != ""){
            String[] array = fallData.split("e");
            for(int i=0; i<array.length; i++){
                int state = Integer.parseInt(array[i].substring(0, array[i].indexOf("+")));
                String time = array[i].substring(array[i].indexOf("+")+1);
                myData.addFirst(new RecordData(state, time, R.drawable.todetail02));
            }
        }
        //实例化适配器Adapter
        myAdapter = new ListAdapter((LinkedList<RecordData>) myData, myContext);
        //向容器中嵌入适配器的布局
        record.setAdapter(myAdapter);

        //设置点击事件
        record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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