package com.dingohub.hellpong.AndroidGameFramework.BaseClasses;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dingohub.hellpong.AndroidGameFramework.Utilities.DataListenerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by ereio on 3/23/15.
 */
public class BaseConnectActivity extends Activity {
    public static GoogleApiClient googleApiClient;
    private final static String TAG = "BaseConnectActivity";
    final String HELL_PATH = "/hellpong/moves";
    static public BroadcastReceiver watchReceiver;
    static public Boolean toggleWatchInput = true;

    GoogleApiClient.ConnectionCallbacks callbacks =
            new GoogleApiClient.ConnectionCallbacks()
            {
                @Override
                public void onConnected(Bundle connectionHint)
                {
                    Log.i(TAG, "Google API Client Connected");

                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.i(TAG, "Google API Client Suspended");
                }


            };

    GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener()
            {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Toast.makeText(getApplicationContext(),
                            "Failed to connect to Google API client", Toast.LENGTH_SHORT).show();
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
    @Override
    protected  void onResume(){
        super.onResume();
        registerReceiver(watchReceiver, new IntentFilter(DataListenerService.NOTIFICATION));
    }

    @Override
    protected  void onPause(){
        super.onPause();
        unregisterReceiver(watchReceiver);
    }
}