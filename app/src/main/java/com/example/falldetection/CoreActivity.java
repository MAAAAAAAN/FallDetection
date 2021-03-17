package com.example.falldetection;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class CoreActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private TextView data;

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

        data = findViewById(R.id.data_textview);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // In this example, alpha is calculated as t / (t + dT),
            // where t is the low-pass filter's time-constant and
            // dT is the event delivery rate.
            //在这个例子中，计算为t / (t + dT)，
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
            data.setText(String.format("event.value0:%s\nevent.value1:%s\nevent.value2:%s\n\nX:%s\nY:%s\nZ:%s",
                    event.values[0],event.values[1],event.values[2],
                    linear_acceleration[0],linear_acceleration[1],linear_acceleration[2]));
            if(force < 1){
                data.setText("您的手机处于跌落状态！");
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

}