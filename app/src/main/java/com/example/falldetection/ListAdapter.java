package com.example.falldetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.falldetection.ui.dashboard.DashboardFragment;

public class ListAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ListAdapter(Context context)
    {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return 10;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    static class ViewHolder
    {
        public ImageView state_iv, todetail_iv;
        public TextView tv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder holder = null;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.layout_list_item, null);
//            view = mLayoutInflater.inflate(R.layout.layout_list_item, this, false);
            holder = new ViewHolder();
            holder.state_iv = view.findViewById(R.id.record_state_iv);
            holder.tv = view.findViewById(R.id.record_tv);
            holder.todetail_iv = view.findViewById(R.id.record_todetail);
            view.setTag(holder);
        }else{

        }
        holder = (ViewHolder) view.getTag();
        holder.state_iv.setImageResource(R.drawable.true02);
        holder.tv.setText("2021-03-18  14:51:00");
        holder.todetail_iv.setImageResource(R.drawable.todetail02);

        return view;
    }
}

