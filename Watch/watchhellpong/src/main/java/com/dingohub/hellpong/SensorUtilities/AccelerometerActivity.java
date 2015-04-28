package com.dingohub.hellpong.SensorUtilities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dingohub.hellpong.BaseClasses.BaseSensorActivity;
import com.dingohub.hellpong.R;

/**
 * Created by Ali on 3/25/15.
 */
public class AccelerometerActivity extends BaseSensorActivity {
    private final static String TAG = "AccelerometerFetcher";



    float lastX;
    float lastY;
    float lastZ;
    float NOISE = 9;
    final float HORZONTAL_SWING = 30;
    final float HORIZONTAL_DIAGNAL = 20;
    final float DOWN_VERTICAL_SWING = -25;
    final float UP_VERTICAL_SWING = 20;

    // 50 for handheld
    final float SIGMA_HORIZONTAL_SWING = 25;
    //-120 for handheld
    final float SIGMA_DOWN_VERT = -20;
    //35 for handheld
    final float SIGMA_UP_VERT = 20;

    final float INTERVAL = 1000000000;
    final float ALPHA = 0.8f;
    private float sigmaX = 0;
    private float sigmaY = 0;

    float gravityV[] = new float[5];
    long lastTime = 0;
    boolean init = false;
    boolean active = false;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_accel_controller);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            getAccelerometerData(event);

        if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
            getAccelerometerData(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometerData(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        if (!init) {
            lastX = x;
            lastY = y;
            lastZ = z;

            init = true;
        } else {

                gravityV[0] = ALPHA * gravityV[0] + (1 - ALPHA) * event.values[0];
                gravityV[1] = ALPHA * gravityV[1] + (1 - ALPHA) * event.values[1];
                gravityV[2] = ALPHA * gravityV[2] + (1 - ALPHA) * event.values[2];

                x = event.values[0] - gravityV[0];
                y = event.values[1] - gravityV[1];
                z = event.values[2] - gravityV[2];

                // Makes the movement lose axis direction
                float deltaX = lastX + x;
                float deltaY = lastY + y;
                float deltaZ = lastZ + z;

                // MAKE FOR NEGATIVE AXIS
                if (deltaX < NOISE && deltaX > -NOISE) deltaX = (float) 0.0;
                if (deltaY < NOISE && deltaY > -NOISE) deltaY = (float) 0.0;
                if (deltaZ < NOISE && deltaZ > -NOISE) deltaZ = (float) 0.0;

                lastX = x;
                lastY = y;
                lastZ = z;

               // hitBoxLogic(event, deltaX, deltaY, deltaZ);
               // Using DeltaY and DeltaZ due to orientation of watch
               // user would have X plan
                sigmaHitBoxLogic(event, deltaY, deltaZ);

        }
    }

    private void hitBoxLogic(SensorEvent event, float deltaX, float deltaY, float deltaZ){

        if (event.timestamp - lastTime > INTERVAL) {
            // LEFT || BOT LEFT || BOT RIGHT
            if (deltaX > HORZONTAL_SWING) {
                active = true;
                Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
            }
            // RIGHT || BOT RIGHT || BOT LEFT
            if (deltaX < -HORZONTAL_SWING) {
                active = true;
                Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();
            }
            if (deltaY < DOWN_VERTICAL_SWING) {
                active = true;
                Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
            }
            if (deltaY > UP_VERTICAL_SWING) {
                active = true;
                Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
            }

            if (active) {
                lastTime = event.timestamp;
                active = false;
            }

            if (deltaX > 1 || deltaX < -1) {
                sigmaX += deltaX;
                sigmaY += deltaY;
                Log.i(TAG, "Horizontal " + deltaX + " " + deltaY + " " + deltaZ + "  Sigma: " + sigmaX + " " + sigmaY);
            } else if (deltaY > 1 || deltaY < -1) {
                sigmaX += deltaX;
                sigmaY += deltaY;
                Log.i(TAG, "Vertical " + deltaX + " " + deltaY + " " + deltaZ + "   Sigma: " + sigmaX + " " + sigmaY);
            } else {
                sigmaX = 0;
                sigmaY = 0;
            }
        } else {
            sigmaX = 0;
            sigmaY = 0;
            Log.i(TAG, "------------------------------------------");
        }
    }

    private void sigmaHitBoxLogic(SensorEvent event, float deltaX, float deltaY){

        if (event.timestamp - lastTime > INTERVAL) {

            // LEFT || BOT LEFT || BOT RIGHT
            if (sigmaX < -SIGMA_HORIZONTAL_SWING) {
                active = true;
                /*
                 if(sigmaY > SIGMA_UP_VERT)
                    Toast.makeText(getApplicationContext(), "Top Left", Toast.LENGTH_SHORT).show();
                 else if(sigmaY < SIGMA_DOWN_VERT)
                    Toast.makeText(getApplicationContext(), "Bottom Left", Toast.LENGTH_SHORT).show();
                 else*/
                    Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();

                sendTouchLoc(4);
            }
            // RIGHT || BOT RIGHT || BOT LEFT
            if (sigmaX > SIGMA_HORIZONTAL_SWING) {
                active = true;
                /*
                if(sigmaY > SIGMA_UP_VERT)
                    Toast.makeText(getApplicationContext(), "Top Right", Toast.LENGTH_SHORT).show();
                else if(sigmaY < SIGMA_DOWN_VERT)
                    Toast.makeText(getApplicationContext(), "Bottom Right", Toast.LENGTH_SHORT).show();
                else*/
                    Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT).show();

                sendTouchLoc(6);
            }
            if (sigmaY < SIGMA_DOWN_VERT) {
                active = true;
                Toast.makeText(getApplicationContext(), "Down", Toast.LENGTH_SHORT).show();
                sendTouchLoc(8);
            }
            if (sigmaY > SIGMA_UP_VERT) {
                active = true;
                Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
                sendTouchLoc(2);
            }

            if (active) {
                lastTime = event.timestamp;
                active = false;
            }

            if (deltaX > 1 || deltaX < -1 || deltaY > 1 || deltaY < -1) {
                sigmaX += deltaX;
                sigmaY += deltaY;
                Log.i(TAG, "Horizontal " + deltaX + " " + deltaY + "   Sigma:X" + sigmaX + " " + sigmaY);
            } else {
                sigmaX = 0;
                sigmaY = 0;
            }
        } else {
            sigmaX = 0;
            sigmaY = 0;
            Log.i(TAG, "------------------------------------------");
        }
    }

    private void diagCheck(){
        if(sigmaY > SIGMA_UP_VERT){
            Toast.makeText(getApplicationContext(), "Top Left", Toast.LENGTH_SHORT).show();
        }
        if(sigmaY < SIGMA_DOWN_VERT){
            Toast.makeText(getApplicationContext(), "Top Left", Toast.LENGTH_SHORT).show();
        }
    }
}
