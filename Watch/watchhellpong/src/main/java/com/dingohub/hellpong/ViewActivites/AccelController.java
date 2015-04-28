package com.dingohub.hellpong.ViewActivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dingohub.hellpong.R;
import com.dingohub.hellpong.SensorUtilities.AccelerometerActivity;

/**
 * Created by ereio on 3/25/15.
 */
public class AccelController extends AccelerometerActivity {
    Button bButtonController;

    TextView axisX;
    TextView axisY;
    TextView axisZ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_controller);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub_accel);


        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                bButtonController = (Button) findViewById(R.id.button_activity_controller);
                //axisX.setText(Float.toString(deltaX));
                //axisY.setText(Float.toString(deltaZ));
                bButtonController.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Controller.class);
                        startActivity(intent);
                    }
                });
            }
        });

        Toast.makeText(getApplicationContext(), "Twist arm with watch on to turn bar!", Toast.LENGTH_LONG).show();
    }
}

