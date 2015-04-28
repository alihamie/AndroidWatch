package com.dingohub.hellpong.BaseClasses;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Ali on 3/25/15.
 */
public class BaseSensorActivity extends BaseWatchActivity implements SensorEventListener {

    SensorManager sensorManager;

    Sensor magnetometer;
    Sensor accelerometer;
    //Sensor gyroscope;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

       // gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
       // magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if ( accelerometer == null || sensorManager == null)
            Toast.makeText(getApplicationContext(), "One or more sensors failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "All Sensors Init", Toast.LENGTH_SHORT).show();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
       // sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
       // sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
       // sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
       // sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
