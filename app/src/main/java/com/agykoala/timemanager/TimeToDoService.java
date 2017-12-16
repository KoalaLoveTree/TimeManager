package com.agykoala.timemanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.agykoala.timemanager.DB.EventDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class TimeToDoService extends Service {

    private NotificationManager nm;
    private final int NOTIF_ID = 322;

    @Override
    public void onCreate() {
        super.onCreate();

        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeToDoSomething();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void timeToDoSomething() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Realm readData = Realm.getDefaultInstance();
                EventDTO eventsDTO;
                while (true) {
                    Notification.Builder builder = new Notification.Builder(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    GregorianCalendar gregorianCalendar = new GregorianCalendar();
                    String date = String.valueOf(gregorianCalendar.get(Calendar.DATE))
                            + "-" + String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1)
                            + "-" + String.valueOf(gregorianCalendar.get(Calendar.YEAR));
                    GregorianCalendar.getInstance();
                    SimpleDateFormat formating = new SimpleDateFormat("HH:mm");
                    eventsDTO = readData.where(EventDTO.class).equalTo("date", date)
                            .and().equalTo("timeStart", formating.format(gregorianCalendar.getTime())).findFirst();

                    if(eventsDTO!=null) {
                        builder.setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.main_logo)
                                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.main_logo))
                                .setTicker("Time to do something")
                                .setWhen(System.currentTimeMillis())
                                .setAutoCancel(true)
                                .setContentTitle("Time Manager Remember")
                                .setContentText(eventsDTO.getName());

                        Notification notification = builder.build();
                        nm.notify(NOTIF_ID, notification);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(60);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}