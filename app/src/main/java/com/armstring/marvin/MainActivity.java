package com.armstring.marvin;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Armstring";
    private TextView txtNum;
    private  int num = 0;
    private static int oldNum = 0;
    static boolean serviceOn = false;
    private Intent intent;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int num = intent.getIntExtra(MyService.MESSAGE_TAG, -1);
            MainActivity.oldNum = num;
            txtNum.setText(String.valueOf(num));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNum = (TextView)findViewById(R.id.txtNum);
        txtNum.setText("0");
        intent = new Intent(MainActivity.this, MyService.class);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver, new IntentFilter(MyService.INTENT_TAG));
    }

    public void btnStart(View view) {
        serviceOn = true;
        Log.d(TAG, "btnStart: " + oldNum);
        intent.putExtra(MyService.INPUT_TAG, oldNum);
        startService(intent);
    }


    public void btnStop(View view) {
        serviceOn = false;
        stopService(intent);
    }

    public void btnReset(View view) {
        if(serviceOn){
            Toast.makeText(this, "Switch Off Service First", Toast.LENGTH_SHORT).show();
        }else{
            oldNum = 0;
            txtNum.setText("0");
        }
    }
}

/*
look for something called ResultService or ServiceResult.
 */