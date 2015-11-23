package br.org.indt.eandon;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import br.org.indt.eandon.Utils.SendAlert;
import br.org.indt.eandon.Utils.Time;
import br.org.indt.eandon.model.Incident;
import br.org.indt.eandon.service.IncidentService;

/**
 * Created by Clark on 10/22/15.
 */
public class CustomListIncidentOpenAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private List<Incident> listListIncidents;

    public CustomListIncidentOpenAdapter(Context context, List<Incident> incidentListView){
        this.listListIncidents = incidentListView;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listListIncidents.size();
    }

    @Override
    public Object getItem(int position) {
        return listListIncidents.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder itemHolder;
        if(convertView == null){
            itemHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_incident_open_item, parent, false);

            itemHolder.idIncident = (TextView)convertView.findViewById(R.id.idIncident);
            itemHolder.Type = (TextView)convertView.findViewById(R.id.typeIncident);
            itemHolder.Line = (TextView)convertView.findViewById(R.id.lineIncident);
            itemHolder.Station = (TextView)convertView.findViewById(R.id.stationIncident);
            itemHolder.RegressiveTime = (TextView)convertView.findViewById(R.id.regressiveTimeIncident);
            itemHolder.stopLine = (Button)convertView.findViewById(R.id.stopLine);

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolder)convertView.getTag();
        }

        itemHolder.idIncident.setText((position + 1) + "");
        itemHolder.Type.setText(listListIncidents.get(position).getType() + "");
        itemHolder.Line.setText(listListIncidents.get(position).getAdress().getLine() + "");
        itemHolder.Station.setText(listListIncidents.get(position).getAdress().getStation() + "");
        itemHolder.RegressiveTime.setText(Time.ConverterTimeMs(listListIncidents.get(position).getRegressiveTime()) + "");

        itemHolder.stopLine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Incident incident = listListIncidents.get(position);
                incident.setPriority("preto");

                IncidentService service = new IncidentService();
                service.stopLineIncident(incident, new Callback() {
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



        int color = R.drawable.btn_cancel_bg;

        switch(listListIncidents.get(position).getPriority()){
            case "amarelo":
                color = R.drawable.open_item_backgroud;
                break;
            case "vermelho":
                color = R.drawable.open_item_backgroud_red;
                break;
            case "preto":
                color = R.drawable.open_item_backgroud_black;
                break;
        }

        itemHolder.idIncident.setBackgroundResource(color);
        itemHolder.Type.setBackgroundResource(color);
        itemHolder.Line.setBackgroundResource(color);
        itemHolder.Station.setBackgroundResource(color);
        itemHolder.RegressiveTime.setBackgroundResource(color);

        return convertView;
    }

    static class ViewHolder{
        TextView idIncident;
        TextView Type;
        TextView Line;
        TextView Station;
        TextView RegressiveTime;
        Button stopLine;
    }

    private static void SendError(final View v){
        SendAlert.toast(v.getContext(), "Erro ao fechar ocorrência!");
    }

    private void SendSuccess(final View v){

    }
}
