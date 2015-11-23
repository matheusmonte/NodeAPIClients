package br.org.indt.eandon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.res.Resources;

import java.util.List;

import br.org.indt.eandon.Utils.Time;
import br.org.indt.eandon.model.Incident;

import static br.org.indt.eandon.R.drawable.open_item_backgroud;

public class CustomListIncidentAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private List<Incident> listListIncidents;

    public CustomListIncidentAdapter(Context context, List<Incident> incidentListView){
        this.listListIncidents = incidentListView;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return listListIncidents.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder itemHolder;
        if (convertView == null){
            itemHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_incident_item, parent, false);

            itemHolder.Status = (TextView)convertView.findViewById(R.id.status);
            itemHolder.Type = (TextView)convertView.findViewById(R.id.type);
            itemHolder.Priority = (TextView)convertView.findViewById(R.id.priority);
            itemHolder.Line = (TextView)convertView.findViewById(R.id.line);
            itemHolder.Station = (TextView)convertView.findViewById(R.id.station);
            itemHolder.RegressiveTime = (TextView)convertView.findViewById(R.id.regressiveTime);
            itemHolder.OperatorCreate = (TextView)convertView.findViewById(R.id.operatorCreate);
            itemHolder.DateCreate = (TextView)convertView.findViewById(R.id.dateCreate);
            itemHolder.Comment = (TextView)convertView.findViewById(R.id.comment);
            itemHolder.OperatorClose = (TextView)convertView.findViewById(R.id.operatorClose);
            itemHolder.DateClose = (TextView)convertView.findViewById(R.id.dateClose);



            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ViewHolder)convertView.getTag();
        }


        itemHolder.Status.setText(listListIncidents.get(position).getStatus() + " ");
        itemHolder.Type.setText(listListIncidents.get(position).getType() + " ");
        itemHolder.Priority.setText(listListIncidents.get(position).getPriority());
        itemHolder.Line.setText(listListIncidents.get(position).getAdress().getLine()  + " ");
        itemHolder.Station.setText(listListIncidents.get(position).getAdress().getStation()  + " ");
        itemHolder.RegressiveTime.setText(Time.ConverterTimeMs(listListIncidents.get(position).getRegressiveTime()) + " ");
        itemHolder.OperatorCreate.setText(listListIncidents.get(position).getOperatorCreate() + " ");
        itemHolder.DateCreate.setText(listListIncidents.get(position).getDateHourCreate() + " ");
        itemHolder.Comment.setText(listListIncidents.get(position).getComment());
        itemHolder.OperatorClose.setText(listListIncidents.get(position).getOperatorClose()  + " ");
        itemHolder.DateClose.setText(listListIncidents.get(position).getDateHourClose());




        return convertView;
    }

    static class ViewHolder{
        TextView Status;
        TextView Type;
        TextView Priority;
        TextView Line;
        TextView Station;
        TextView RegressiveTime;
        TextView OperatorCreate;
        TextView DateCreate;
        TextView Comment;
        TextView OperatorClose;
        TextView DateClose;

    }
}
