package com.example.falldetection.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.falldetection.R;

public class NotificationsFragment extends Fragment {

//    private NotificationsViewModel notificationsViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                new ViewModelProvider(this).get(NotificationsViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText("test");
//            }
//        });
//        return root;
//    }


    SharedPreferences preferences;
    private Switch onOff;
    public static boolean recordSetting = false;

    private ImageButton about;
    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        textView = getActivity().findViewById(R.id.text_notifications);
//        textView.setText("version 1.0");
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //开启掉落记录
        onOff = getActivity().findViewById(R.id.isRecord_s);
        preferences = getContext().getSharedPreferences("switch", Context.MODE_PRIVATE);
        if(preferences != null){
            boolean bool = preferences.getBoolean("recordSetting", recordSetting);
            onOff.setChecked(bool);
        }
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    recordSetting = true;
                    SharedPreferences sp = getContext().getSharedPreferences("switch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("recordSetting", true);
                    editor.commit();
                }else{
                    recordSetting = false;
                    SharedPreferences sp = getContext().getSharedPreferences("switch", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("recordSetting", false);
                    editor.commit();
                }
            }
        });

        //敏捷度


        //关于
        about = getActivity().findViewById(R.id.about_b);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t =new Toast(getActivity());
                LayoutInflater inflater =LayoutInflater.from(getActivity());
                View view =inflater.inflate(R.layout.layout_about, null);
                TextView tv = view.findViewById(R.id.layout_about_tv);
                tv.setText(Html.fromHtml("<b>关于</b><br/><br/><br/>版本号：version 1.0<br/>版本时间：2021-03-27"));
                t.setView(view);
                t.setDuration(Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        });

        //底部版本号
        textView = getActivity().findViewById(R.id.text_notifications);
        textView.setText("version 1.0.6");

    }
}