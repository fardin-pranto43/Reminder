package com.blundell.tut.service;

import java.util.Calendar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * This is our service client, it is the 'middle-man' between the
 * service and any activity that wants to connect to the service
 *
 * @author paul.blundell
 */
public class ScheduleClient {

    // The hook into our service
    private com.blundell.tut.service.ScheduleService mBoundService;
    // The context to start the service in
    private Context mContext;
    // A flag if we are connected to the service or not
    private boolean mIsBound;

    public ScheduleClient(Context context) {
        mContext = context;
    }


    public void doBindService() {
        // Establish a connection with our service
        mContext.bindService(new Intent(mContext, com.blundell.tut.service.ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with our service has been established,
            // giving us the service object we can use to interact with our service.
            mBoundService = ((com.blundell.tut.service.ScheduleService.ServiceBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };


    public void setAlarmForNotification(Calendar c){
        mBoundService.setAlarm(c);
    }

    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}