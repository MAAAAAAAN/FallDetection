package com.example.falldetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ListAdapter extends BaseAdapter
{

    private LinkedList<RecordData> recordData;
    private Context mContext;
//    private LayoutInflater mLayoutInflater;


//    public ListAdapter(Context context)
//    {
//        this.mContext = context;
//        mLayoutInflater = LayoutInflater.from(context);
//    }
    public ListAdapter(LinkedList<RecordData> recordData, Context context)
    {
        this.recordData = recordData;
        this.mContext = context;
    }

    @Override
    public int getCount()
    {
//        return 20;
        return recordData.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
//        return 0;
        return i;
    }

//    static class ViewHolder
//    {
//        public ImageView state_iv, todetail_iv;
//        public TextView tv;
//    }
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup)
//    {
//        ViewHolder holder = null;
//        if (view == null) {
//            view = mLayoutInflater.inflate(R.layout.layout_list_item, null);
//            holder = new ViewHolder();
//            holder.state_iv = view.findViewById(R.id.record_state_iv);
//            holder.tv = view.findViewById(R.id.record_tv);
//            holder.todetail_iv = view.findViewById(R.id.record_todetail);
//            view.setTag(holder);
//        }else{
//            holder = (ViewHolder) view.getTag();
//        }
//        holder.state_iv.setImageResource(R.drawable.true02);
//        holder.tv.setText("2021-03-18  14:51:00");
//        holder.todetail_iv.setImageResource(R.drawable.todetail02);
//
//        return view;
//    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        //填充xml视图
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, viewGroup, false);
        //获取控件
        ImageView state_iv = view.findViewById(R.id.record_state_iv);
        TextView tv = view.findViewById(R.id.record_tv);
        ImageView todetail_iv = view.findViewById(R.id.record_todetail);
        //从recordData中获取数据，往控件中写入数据
        state_iv.setImageResource(recordData.get(position).getState());
        tv.setText(recordData.get(position).getTime());
        todetail_iv.setImageResource(recordData.get(position).getToDetail());

        return view;
    }


    public void add(RecordData data){
        if(data == null){
            recordData = new LinkedList<>();
        }
        recordData.add(data);
        notifyDataSetChanged();
    }


}

