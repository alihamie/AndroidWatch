package com.dingohub.hellpong.AndroidGameFramework.Utilities;

import android.content.Intent;
import android.util.Log;

import com.dingohub.hellpong.AndroidGameFramework.Implementation.AndroidGame;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;

public class DataListenerService extends WearableListenerService {
    private final static String TAG = "DataListenerService";
    private static final String START_ACTIVITY_PATH = "/my/path";
    public static final String NOTIFICATION = "com.dingohub.andgameandwatch.AndroidGameFramework.Utilities";
    public final static String PLAYERMOVE = "WatchPlayerControllerMove";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        String path = messageEvent.getPath();

        if (START_ACTIVITY_PATH.equals(path)) {
            Intent intent = new Intent(this, AndroidGame.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        final List<DataEvent> events =
                FreezableUtils.freezeIterable(dataEvents);

        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                Log.i(TAG, "DataChanged Fired");

                // Gets DataItem event
                DataItem item = event.getDataItem();
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();

                // Calls the datamap value of player move and sends it to publish
                publishResults(dataMap.getInt(PLAYERMOVE));

            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    private void publishResults(int move) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(PLAYERMOVE, move);
        sendBroadcast(intent);
    }

}

