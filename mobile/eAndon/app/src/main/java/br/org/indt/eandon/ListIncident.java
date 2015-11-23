package br.org.indt.eandon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import br.org.indt.eandon.Utils.SendAlert;
import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.IncidentService;

public class ListIncident extends Fragment {

    private ListView listView;
    private Incident[] listIncident;
    private Thread timer;
    private ArrayList<Incident> arrayListincidents;
    private Incident[] incidentTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_incident, container, false);

        listView = (ListView) v.findViewById(R.id.lvListIncident);

        getListIncidentOpen(v);
        addListenerOnListView(v);

        return v;
    }

    @Override
    public void onPause(){
        super.onPause();
        SendAlert.cancelToast();
    }

    public void getListIncidentOpen(final View v){
        IncidentService service = new IncidentService();
        service.getOpenIncident(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ReceiveError(v);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    ReceiveSuccess(v, response.body().string());
                }
            }
        });
    }

    public void addListenerOnListView(final View v){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.v("ListItemClick", String.valueOf(position));

                Date date = new Date();

                Incident incident = new Incident(listIncident[position]);
                incident.setComment("Ocorrência fechada.");
                incident.setStatus("closed");
                incident.setOperatorClose("Pedro");
                incident.setDateHourClose(date.toString());

                IncidentService service = new IncidentService();
                service.closeIncident(listIncident[position].getId(), incident, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.v("Send Error ", "erro");
                        SendError(v);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.v("Send Success ", "sucesso");
                            SendSuccess(v);
                        }
                    }
                });
            }
        });
    }

    private void onCreateListView(final View v,Incident[] incidents){

        arrayListincidents = new ArrayList<>();

        if(timer != null){
            if(!timer.isInterrupted()){
                timer.interrupt();
            }
        }

        for (int i = 0; i<incidents.length; i++){
            if(incidents[i].getRegressiveTime() < 0){
                if(!incidents[i].getPriority().equals("preto")) {
                    incidents[i].setPriority("vermelho");
                }
            }
            arrayListincidents.add(incidents[i]);
        }
        incidentTimer = incidents.clone();

        listView.setAdapter(new CustomListIncidentOpenAdapter(v.getContext(), arrayListincidents));
        if (incidents.length > 0 && timer == null) {
            TimeThread();
        }
    }

    private void ReceiveError(final View v){
        SendAlert.toast(v.getContext(), "Erro ao receber os dados!");
    }

    public void ReceiveSuccess(final View v,String data){
        Log.v("OkHttp", data);

        Gson jsonParser = new Gson();

        listIncident = jsonParser.fromJson(data, Incident[].class);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onCreateListView(v, listIncident);
            }
        });
    }

    public void TimeThread(){

        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);

                    for(int i = 0; i < incidentTimer.length; i++){
                        incidentTimer[i].setRegressiveTime(incidentTimer[i].getRegressiveTime()  - 1000);

                        if(incidentTimer[i].getRegressiveTime() > 0){//Regressive
                            incidentTimer[i].setPriority("amarelo");
                        }else{//Progressive
                            if(!incidentTimer[i].getPriority().equals("preto")) {
                                incidentTimer[i].setPriority("vermelho");
                            }
                        }


                        arrayListincidents.set(i, incidentTimer[i]);
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (arrayListincidents.size() > 0) {
                                try {
                                    ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }catch(InterruptedException e){
                    timer.interrupt();
                    e.printStackTrace();
                }
                new Thread(timer).start();
            }
        });
        timer.start();
    }

    private static void SendError(final View v){
        SendAlert.toast(v.getContext(), "Conexão com o servidor perdida, tente novamente");
    }

    private void SendSuccess(final View v){
        SendAlert.toast(v.getContext(), "Ocorrência fechada com sucesso!");
        getListIncidentOpen(v);
    }
}