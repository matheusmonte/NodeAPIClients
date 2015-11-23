package br.org.indt.eandon.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import br.org.indt.eandon.R;


public class SendAlert {
    public static void notification(final Context v, PendingIntent contentIntent, String msg) {
        NotificationManager notificationManager = (NotificationManager)
                v.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                v)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("E-AndOn")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        builder.setContentIntent(contentIntent);
        notificationManager.notify(1000, builder.build());//change 1000 for incident id
    }

    private static Toast toast;
    public static void toast(final Context c, final String msg){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                cancelToast();
                toast = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public static void cancelToast(){
        if (toast != null) {
            toast.cancel();
        }
    }
}
