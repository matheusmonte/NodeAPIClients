package br.org.indt.eandon.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;

import br.org.indt.eandon.model.Incident;


public class IncidentService {

    public void createIncident(Incident incident, Callback callback)  {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson jsonParser = gsonBuilder.create();

        try{
            String json = jsonParser.toJson(incident);
            WebClient webClient = new WebClient();
            webClient.post(Urls.CREATE_INCIDENT, json, callback);
        } catch (Exception e){
            Log.e("Incident Service post:", e.toString());
        }
    }

    public void closeIncident(String id, Incident incident, Callback callback)  {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson jsonParser = gsonBuilder.create();

        try{
            String json = jsonParser.toJson(incident);
            WebClient webClient = new WebClient();
            webClient.put(Urls.UPDATE_INCIDENT, json, callback);
        } catch (Exception e){
            Log.e("Incident Update post:", e.toString());
        }
    }

    public void getOpenIncident(Callback callback)  {
        try{
            WebClient webClient = new WebClient();
            webClient.get(Urls.LIST_OPEN_INCIDENT, callback);
        }catch (Exception e){
            Log.e("Incident Service get:", e.toString());
        }
    }

    public void getClosedIncident(Callback callback){
        try {
            WebClient webClient = new WebClient();
            webClient.get(Urls.lIST_CLOSE_INCIDENT, callback);
        } catch (Exception e){
            Log.e("Incident Service get:", e.toString());
        }
    }

    public void stopLineIncident(Incident incident, Callback callback)  {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson jsonParser = gsonBuilder.create();

        try{
            String json = jsonParser.toJson(incident);
            WebClient webClient = new WebClient();
            webClient.put(Urls.UPDATE_INCIDENT, json, callback);
        } catch (Exception e){
            Log.e("Incident Update post:", e.toString());
        }
    }





}

