package br.org.indt.eandon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import br.org.indt.eandon.Utils.SendAlert;
import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.IncidentService;

public class HistoryIncident extends Fragment {
    private ListView listView;
    private Incident[] ListIncident;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_incident, container, false);

        getListIncidentClosed(v);

        return v;
    }

    @Override
    public void onPause(){
        super.onPause();
        SendAlert.cancelToast();
    }

    public void getListIncidentClosed(final View v){
        IncidentService service = new IncidentService();
        service.getClosedIncident(new Callback() {
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

    private void ReceiveError(final View v){
        SendAlert.toast(v.getContext(), "Erro ao receber os dados!");
    }

    private void ReceiveSuccess(final View v, String data){
        Gson jsonParser = new Gson();

        ListIncident = jsonParser.fromJson(data, Incident[].class);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onCreateListView(v, ListIncident);
            }
        });
    }

    private  void onCreateListView(final View v, Incident[] incidents){
        ArrayList<Incident> ArrayListincidents = new ArrayList<>();

        for (int i = 0; i <incidents.length; i++){
            ArrayListincidents.add(incidents[i]);
        }

        ListView lv = (ListView)v.findViewById(R.id.lvHistoryIncident);
        lv.setAdapter(new CustomListHistoryAdapter(getActivity(), ArrayListincidents));
    }
}