package com.example.falldetection;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.falldetection.ui.notifications.NotificationsFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class CoreActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ImageView stateImage;
    private TextView data;
    private Button back;
//    //全局变量
//    public static LinkedList<RecordData> recordData = new LinkedList<RecordData>();
    //获取开启记录的开关值
    private boolean isRecord = NotificationsFragment.recordSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        stateImage = findViewById(R.id.state_imageview);
        data = findViewById(R.id.data_textview);
        back = findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前的Activity，返回上一页
                finish();
            }
        });

    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(isFallOrCollision(event)){
                stateImage.setImageResource(R.drawable.fall06);
                data.setText(
                        Html.fromHtml(
                                "<font color=\"#ff0000\">"
                                        + String.format("X轴：%s<br>Y轴：%s<br>Z轴：%s", event.values[0],event.values[1],event.values[2])
                                        +"</font>"
                        )
                );
                //获取时间戳并转化为指定格式的字符串
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                String time = df.format(new Date().getTime());
                //设置自定义对话框
                CustomDialog customDialog = new CustomDialog(CoreActivity.this, R.style.CustomDialog);
                customDialog.setTitle("提示").setMessage("已监测到您的手机处于坠落状态，请检查手机是否损坏！")
                .setCancel("监测有误", new CustomDialog.IonCancelListener()
                {
                    @Override
                    public void onCancel(CustomDialog dialog)
                    {
                        if(isRecord){
//                            recordData.addFirst(new RecordData(R.drawable.false02, time, R.drawable.todetail02));
                            String data = R.drawable.false02 + "+" + time + "e";
                            File file = new File("/data/data/com.example.falldetection/", "recordData.txt");
                            FileOutputStream outputStream = null;
                            try {
                                outputStream = new FileOutputStream(file, true);
                                outputStream.write(data.getBytes());
                            } catch (Exception e){
                                e.printStackTrace();
                            } finally {
                                if(outputStream != null){
                                    try {
                                        outputStream.close();
                                    } catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Toast.makeText(CoreActivity.this, "可在“记录”中查看本次监测！", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setConfirm("确认", new CustomDialog.IonConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog)
                    {
                        if(isRecord){
//                            recordData.addFirst(new RecordData(R.drawable.true02, time, R.drawable.todetail02));
                            String data = R.drawable.true02 + "+" + time + "e";
                            File file = new File("/data/data/com.example.falldetection/", "recordData.txt");
                            FileOutputStream outputStream = null;
                            try {
                                outputStream = new FileOutputStream(file, true);
                                outputStream.write(data.getBytes());
                            } catch (Exception e){
                                e.printStackTrace();
                            } finally {
                                if(outputStream != null){
                                    try {
                                        outputStream.close();
                                    } catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Toast.makeText(CoreActivity.this, "可在“记录”中查看本次监测！", Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();

            }else {
                stateImage.setImageResource(R.drawable.smooth02);
                data.setText(String.format("X轴：%s\nY轴：%s\nZ轴：%s",
                        event.values[0],event.values[1],event.values[2]));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != sensorManager) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }



    public boolean isFallOrCollision(SensorEvent event){
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
        //在这个例子中，计算为 t / (t + dT)，
        //其中t是低通滤波器的时间常数和
        // dT为事件传递速率。
        final float alpha = 0.8f;
        // Isolate the force of gravity with the low-pass filter.
        //用low-pass过滤器隔离重力。
        float gravity[] = new float[3];
        float linear_acceleration[] = new float[3];
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        // Remove the gravity contribution with the high-pass filter.
        //用高通滤波器移除重力。
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        //跌落检测
        double force;
        force = Math.sqrt(linear_acceleration[0]*linear_acceleration[0] + linear_acceleration[1]*linear_acceleration[1] + linear_acceleration[2]*linear_acceleration[2]);
        if(force < 1){
            return true;
        }
        return false;
    }

}
