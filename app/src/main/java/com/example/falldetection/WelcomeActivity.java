package com.example.falldetection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //让欢迎界面全屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //调用启动主Activity的方法
        startMainActivity();
    }

    //自定义启动主Activity方法
    private void startMainActivity(){
        //1得到Timer的实例化对象
        Timer timer = new Timer();
        //2得到TimerTask的实例化对象
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                //启动MainActivity
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        };
        //3启动定时器
        //延时2秒执行计时任务对象TimerTask
        timer.schedule(delayTask, 3000);
    }

}