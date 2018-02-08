package com.armstring.marvin;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Darkwood on 2/8/2018.
 */

public class MyService extends IntentService{
    public static final String TAG = "Armstring";
    private boolean keepGoing = true;
    public static final String INTENT_TAG = "intent_tag";
    public static final String MESSAGE_TAG = "message_tag";
    public static final String INPUT_TAG = "message_tag";

    public MyService() {
        super("MyService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int num = intent.getIntExtra(INPUT_TAG, -1);
        while(keepGoing){
            try {
                Thread.sleep(5000);
                if(keepGoing){
                    num += 5;
                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                    Intent newIntent = new Intent(INTENT_TAG);
                    newIntent.putExtra(MESSAGE_TAG, num);
                    manager.sendBroadcast(newIntent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        keepGoing = false;
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
