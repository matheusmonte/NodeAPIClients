package br.org.indt.eandon.model;

import com.google.gson.annotations.SerializedName;


public class Incident {

    @SerializedName("_id")
    private String id;
    private String type;
    private String priority;
    private long regressiveTime;
    private Adress adress;
    private String status;
    private String operatorCreate;
    private String dateHourCreate;
    private String comment;
    private String operatorClose;
    private String dateHourClose;
    private String operatorStopLine;
    private String datehourStopLine;

    public Incident(){

    }

    public Incident(Incident incident){
        this.id = incident.getId();
        this.type = incident.getType();
        this.priority = incident.getPriority();
        this.regressiveTime = incident.getRegressiveTime();
        this.adress = incident.adress;
        this.status = incident.getStatus();
        this.operatorCreate = incident.getOperatorCreate();
        this.dateHourCreate = incident.getDateHourCreate();
        this.comment = incident.getComment();
        this.operatorClose = incident.getOperatorClose();
        this.dateHourClose = incident.getDateHourClose();
        this.operatorStopLine = incident.getOperatorStopLine();
        this.datehourStopLine = incident.getDateHourStopLine();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getRegressiveTime() {
        return regressiveTime;
    }

    public void setRegressiveTime(long regressiveTime) {
        this.regressiveTime = regressiveTime;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperatorCreate() {
        return operatorCreate;
    }

    public void setOperatorCreate(String operatorCreate) {
        this.operatorCreate = operatorCreate;
    }

    public String getDateHourCreate() {
        return dateHourCreate;
    }

    public void setDateHourCreate(String dateHourCreate) {
        this.dateHourCreate = dateHourCreate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperatorClose() {
        return operatorClose;
    }

    public String getOperatorStopLine() {
        return operatorStopLine;
    }

    public String getDateHourStopLine(){ return datehourStopLine;}

    public void setDateHourStopLine(String dateHourClose){
        this.datehourStopLine = dateHourClose;
    }
    public void setOperatorClose(String operatorClose) {
        this.operatorClose = operatorClose;
    }

    public String getDateHourClose() {
        return dateHourClose;
    }

    public void setDateHourClose(String dateHourClose) {
        this.dateHourClose = dateHourClose;
    }


}
