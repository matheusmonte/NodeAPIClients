package br.org.indt.eandon;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.broadcast.NotificationReceiver;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private Incident[] listNotificationIncident;
    private Thread notify;
    private long countNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        createAlarm();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void createAlarm() {
        AlarmManager alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, NotificationReceiver.class), 0);
        long init = 1000;
        /* Foi utilizado o alarme e a função set que funcionou melhor q o setRepeating, por questões de boa prática esse
        pulling deve ser substituido por GCM - Google Cloud Management*/
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, init, pendingIntent);
    }
}