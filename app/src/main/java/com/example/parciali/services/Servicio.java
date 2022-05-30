package com.example.parciali.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.example.parciali.MainActivity;

public class Servicio extends Service {
    public Servicio() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("inside", "oncreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    public boolean linea(Context c){
        ConnectivityManager cm=(ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();

        if(ni!=null && ni.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    Handler handler=new Handler();

    private Runnable periodicUpdate=new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate,1*1000- SystemClock.elapsedRealtime()%1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.BroadcastStringForAction);
            broadcastIntent.putExtra("estatus online", ""+linea(Servicio.this));
            sendBroadcast(broadcastIntent);
        }
    };
}