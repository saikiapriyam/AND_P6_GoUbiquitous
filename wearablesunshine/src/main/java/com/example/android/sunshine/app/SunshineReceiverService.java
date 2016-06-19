package com.example.android.sunshine.app;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class SunshineReceiverService extends WearableListenerService {

    private static final String TAG = SunshineReceiverService.class.getSimpleName();

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.d(TAG, "onDataChanged");
        super.onDataChanged(dataEventBuffer);
        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/sunshine")) {
                    String low = dataMap.getString("low_temp");
                    String timestamp = dataMap.getString("timestamp");
                    String high = dataMap.getString("high_temp");
                    Log.d(TAG, "low =" + low + " high = " + high);
                    Intent intent = new Intent(AppConstants.INTENT_STRING);
                    intent.putExtra(AppConstants.low, low);
                    intent.putExtra(AppConstants.high, high);
                    sendBroadcast(intent);
                }
            }
        }
    }
}
