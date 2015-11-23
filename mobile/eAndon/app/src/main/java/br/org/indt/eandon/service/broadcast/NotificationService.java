package br.org.indt.eandon.service.broadcast;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.org.indt.eandon.MainActivity;
import br.org.indt.eandon.Utils.SendAlert;
import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.IncidentService;


public class NotificationService extends IntentService {
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        getListIncidentOpen(intent);
    }

    public void getListIncidentOpen(final Intent intent){
        IncidentService service = new IncidentService();
        service.getOpenIncident(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                createAlarm();
                WakefulBroadcastReceiver.completeWakefulIntent(intent);
                Log.v("Error: ", "ao receber dados para o notification");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson jsonParser = new Gson();
                    Incident[] incidents = jsonParser.fromJson(response.body().string(), Incident[].class);

                    for (int i = 0; i < incidents.length; i++) {
                        if ((incidents[i].getRegressiveTime() >= -1000) && (incidents[i].getRegressiveTime() < 5500)) {//Regressive
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, new Intent(getBaseContext(), MainActivity.class), 0);
                                    SendAlert.notification(getBaseContext(), contentIntent, "enviando notificação e-andon!!!");
                                }
                            });
                        }

                        createAlarm();
                        WakefulBroadcastReceiver.completeWakefulIntent(intent);
                    }
                }
            }
        });
    }

    private void createAlarm() {
        AlarmManager alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, NotificationReceiver.class), 0);
        long init = 5000;
        /* Foi utilizado o alarme e a função set que funcionou melhor q o setRepeating, por questões de boa prática esse
        pulling deve ser substituido por GCM - Google Cloud Management*/
        alarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, init, pendingIntent);
    }
}
