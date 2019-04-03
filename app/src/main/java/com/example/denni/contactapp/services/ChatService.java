package com.example.denni.contactapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.denni.contactapp.ChatActivity;
import com.example.denni.contactapp.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;

public class ChatService extends Service {

    private static final int ID = 0x554;
    private SharedPreferences preferences;
    private Set<String> keySet;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(ID, getNotification("","",false,0));

        keySet = preferences.getStringSet("CHAT_KEYS",null);

        if (keySet!=null){
            startToObserveDB();
        }

        return START_STICKY;
    }

    private Notification getNotification(String id,String msg, boolean setOnClick,int chatName) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(),"ID");
        builder
                .setSmallIcon(getApplicationInfo().icon)
                .setContentTitle(id)
                .setContentText(msg);

        if (setOnClick){
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("CHAT_NAME", chatName);
            intent.putExtra("CHAT_TYPE", 2);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext()
                    ,0
                    ,intent
                    ,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }

        NotificationManager manager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O && manager!=null){
            manager.createNotificationChannel(
                    new NotificationChannel(
                            "ID","NAME", NotificationManager.IMPORTANCE_DEFAULT));
        }
        return builder.build();
    }

    private void startToObserveDB() {
        for (String str : keySet){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(str);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.exists()){
                        Message msg = dataSnapshot.getValue(Message.class);
                        NotificationManager manager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (manager != null && msg.getKey()!=null){
                            int chatName = preferences.getString("KEY","").hashCode()
                                            + msg.getKey().hashCode();
                            manager.notify(1,getNotification(msg.getId(),msg.getMsg(),true, chatName));
                        }
                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
