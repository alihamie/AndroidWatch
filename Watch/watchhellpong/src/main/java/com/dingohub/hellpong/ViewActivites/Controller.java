package com.dingohub.hellpong.ViewActivites;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dingohub.hellpong.BaseClasses.BaseWatchActivity;
import com.dingohub.hellpong.R;

public class Controller extends BaseWatchActivity {

    private TextView mTextView;
    public final static String PLAYERMOVE = "WatchPlayerControllerMove";
    int[] keyNums = new int[9];
    Button bBottom;
    Button bRight;
    Button bLeft;
    Button bUp;
    Button bBottomLeft;
    Button bBottomRight;
    Button bTopLeft;
    Button bTopRight;
    Button bAccelController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                bAccelController = (Button) stub.findViewById(R.id.button_activity_accel);

                bBottom = (Button) stub.findViewById(R.id.button_bottom);
                bRight = (Button) stub.findViewById(R.id.button_right);
                bLeft = (Button) stub.findViewById(R.id.button_left);
                bUp  = (Button) stub.findViewById(R.id.button_up);

                bBottomLeft  = (Button) stub.findViewById(R.id.button_bottomleft);
                bBottomRight  = (Button) stub.findViewById(R.id.button_bottomright);
                bTopLeft  = (Button) stub.findViewById(R.id.button_topleft);
                bTopRight  = (Button) stub.findViewById(R.id.button_topright);


                bBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(8);
                    }
                });
                bRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(6);
                    }
                });
                bLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(4);
                    }
                });
                bUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(2);
                    }
                });
                bBottomLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { sendTouchLoc(7);    }
                });
                bBottomRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { sendTouchLoc(9);
                    }
                });
                bTopLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(1);
                    }
                });
                bTopRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendTouchLoc(3);
                    }
                });

                bAccelController.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), AccelController.class);
                        startActivity(intent);
                    }
                });

            }
        });



    }
}
