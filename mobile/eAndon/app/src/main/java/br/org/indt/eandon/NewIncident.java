package br.org.indt.eandon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Date;

import br.org.indt.eandon.Utils.SendAlert;
import br.org.indt.eandon.model.Adress;
import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.IncidentService;

public class NewIncident extends Fragment {

    private Spinner spinnerType;
    private RadioGroup radioPriorityGroup;
    private RadioButton radioPriorityButton;
    private Spinner spinnerLine;
    private Spinner spinnerStation;
    private Button btnSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_incident, container, false);

        String[] type = {"Qualidade", "Segurança", "Mecânica","Supermercado"};
        Integer[] line = {1, 2, 3, 4, 5};
        Integer[] station = {1, 2, 3, 4, 5};

        ArrayAdapter<String> stringTypeArrayAdapter = new ArrayAdapter<String>(this.getActivity(),R.layout.spinner_item, type);
        spinnerType = (Spinner) v.findViewById(R.id.spnType);
        spinnerType.setAdapter(stringTypeArrayAdapter);

        ArrayAdapter<Integer> integerLineArrayAdapter = new ArrayAdapter<Integer>(this.getActivity(),R.layout.spinner_item, line);
        spinnerLine = (Spinner) v.findViewById(R.id.line);
        spinnerLine.setAdapter(integerLineArrayAdapter);

        ArrayAdapter<Integer> integerStationArrayAdapter = new ArrayAdapter<Integer>(this.getActivity(),R.layout.spinner_item, station);
        spinnerStation = (Spinner) v.findViewById(R.id.station);
        spinnerStation.setAdapter(integerStationArrayAdapter);

        addListenerOnButton(v);

        return v;
    }

    @Override
    public void onPause(){
        super.onPause();
        SendAlert.cancelToast();
    }

    public void addListenerOnButton(View v) {

        spinnerType = (Spinner) v.findViewById(R.id.spnType);
        spinnerLine = (Spinner) v.findViewById(R.id.line);
        spinnerStation = (Spinner) v.findViewById(R.id.station);
        radioPriorityGroup = (RadioGroup) v.findViewById(R.id.radioPriority);

        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                View view = v.getRootView();
                Incident incident = new Incident();
                Date date = new Date();

                int selectedId = radioPriorityGroup.getCheckedRadioButtonId();
                radioPriorityButton = (RadioButton) view.findViewById(selectedId);
                String radioSelect = radioPriorityButton.getText().toString();

                incident.setType(spinnerType.getSelectedItem().toString());
                incident.setPriority(radioSelect.toLowerCase());
                incident.setAdress(new Adress(spinnerLine.getSelectedItem().toString(), spinnerStation.getSelectedItem().toString()));
                incident.setStatus("open");
                incident.setOperatorCreate("João");
                incident.setDateHourCreate(date.toString());

                IncidentService service = new IncidentService();
                service.createIncident(incident,new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        SendError(v);
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            SendSuccess(v);
                        }
                    }
                });
            }
        });
    }

    private static void SendError(final View v){
        SendAlert.toast(v.getContext(), "Erro ao enviar a ocorrência!Conexao com servidor perdida");
    }

    private void SendSuccess(final View v){
        SendAlert.toast(v.getContext(), "Ocorrência enviado com sucesso!");
    }
}