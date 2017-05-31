package com.sdproject.szabi.pizzaclient.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sdproject.szabi.pizzaclient.R;
import com.sdproject.szabi.pizzaclient.datamodel.Order;
import com.sdproject.szabi.pizzaclient.domain.orders.OrdersService;
import com.sdproject.szabi.pizzaclient.utils.Constants;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Szabi on 5/30/2017.
 */

public class OrderUpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final String orderId = intent.getStringExtra("orderId");

        Thread t = new Thread() {
            @Override
            public void run() {
                int previousState = 0;
                try {
                    // while the order status is not marked as finished
                    while(previousState < 4) {
                        previousState = retrieveOrder(orderId, previousState);
                        sleep(1000);
                        Log.d("Service", "Service still running and listening!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private int retrieveOrder(String id, int previousState) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        OrdersService client = retrofit.create(OrdersService.class);
        Call<Order> callOrder = client.getOrderById(id);

        try {
            Order updatedOrder = callOrder.execute().body();
            if (updatedOrder != null) {
                if (updatedOrder.status != previousState) {
                    previousState = updatedOrder.status;
                    switch (previousState){
                        case 1:
                            sendNotification("Waiting to be processed.", "Order from: " + DateFormat.getDateTimeInstance().format(updatedOrder.datetime));
                            break;
                        case 2:
                            sendNotification("Preparing food for you.", "Order from: " + DateFormat.getDateTimeInstance().format(updatedOrder.datetime));
                            break;
                        case 3:
                            sendNotification("Delivering your food.", "Order from: " + DateFormat.getDateTimeInstance().format(updatedOrder.datetime));
                            break;
                        case 4:
                            sendNotification("Marked as finished.", "Order from: " + DateFormat.getDateTimeInstance().format(updatedOrder.datetime));
                            break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return previousState;
    }

    private void sendNotification(String title, String msg){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(title).setContentText(msg)
                .setSmallIcon(R.drawable.image_not_found).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);

        Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }
}
