package br.org.indt.eandon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.org.indt.eandon.model.Incident;


public class CustomListHistoryAdapter extends BaseAdapter {
    private List<Incident> incidentList;
    Context context;

    public CustomListHistoryAdapter(Context context, List<Incident> incidentListView){
        this.context = context;
        this.incidentList = incidentListView;
    }

    @Override
    public int getCount() {
        return incidentList.size();
    }

    @Override
    public Object getItem(int position) {
        return incidentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Incident incident = incidentList.get(position);
        View layout;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.history_incident_item, null);
        } else {
            layout = convertView;
        }

        TextView id = (TextView) layout.findViewById(R.id.id);
        id.setText(incident.getId());

        TextView type = (TextView) layout.findViewById(R.id.type);
        type.setText(incident.getType());

        TextView line = (TextView) layout.findViewById(R.id.line);
        line.setText(incident.getAdress().getLine());

        TextView station = (TextView) layout.findViewById(R.id.station);
        station.setText(incident.getAdress().getStation());

        TextView dateCreate = (TextView) layout.findViewById(R.id.open);
        dateCreate.setText(incident.getDateHourCreate());

        TextView dateClose = (TextView) layout.findViewById(R.id.closed);
        dateClose.setText(incident.getDateHourClose());

        return layout;
    }
}