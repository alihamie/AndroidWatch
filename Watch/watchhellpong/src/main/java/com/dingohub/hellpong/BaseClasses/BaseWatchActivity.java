package com.dingohub.hellpong.BaseClasses;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Ali on 3/23/15.
 */
public class BaseWatchActivity extends Activity {
    public GoogleApiClient googleApiClient;
    final String HELL_PATH = "/hellpong/moves";
    public final static String PLAYERMOVE = "WatchPlayerControllerMove";

    GoogleApiClient.ConnectionCallbacks callbacks =
            new GoogleApiClient.ConnectionCallbacks()
            {
                @Override
                public void onConnected(Bundle connectionHint) {
                    Toast.makeText(getApplicationContext(),
                            "Connected", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Toast.makeText(getApplicationContext(),
                            "DisConnected", Toast.LENGTH_SHORT).show();
                }
            };

    GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener()
            {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();


    }

    protected void sendTouchLoc(int move){
        PutDataMapRequest putDataMapRequest =  PutDataMapRequest.create(HELL_PATH);
        putDataMapRequest.getDataMap().putInt(PLAYERMOVE, move);
        PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(googleApiClient, putDataRequest);
    }
}