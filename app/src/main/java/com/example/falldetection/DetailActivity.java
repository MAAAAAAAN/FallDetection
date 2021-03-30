package com.example.falldetection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView time_tv;
    private TextView state_tv;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String data = getIntent().getStringExtra("data");
        String time = data.substring(0, data.indexOf("+"));
        String state = data.substring(data.indexOf("+")+1);

        time_tv = findViewById(R.id.detail_time_tv);
        time_tv.setText(Html.fromHtml("监测时间：" + "<b>" + time +"</b>"));

        state_tv = findViewById(R.id.detail_state_tv);
        if(state.equals("true")){
            state_tv.setText(Html.fromHtml("监测状态：<font color=\"#00ff00\">成功监测</font>"));
        }else if(state.equals("false")){
            state_tv.setText(Html.fromHtml("监测状态：<font color=\"#ff0000\">监测有误</font>"));
        }

        back = findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}