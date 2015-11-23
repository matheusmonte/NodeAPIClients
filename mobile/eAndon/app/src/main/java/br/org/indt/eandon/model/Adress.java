package br.org.indt.eandon.model;


public class Adress {
    private String line;
    private String station;

    public Adress () {

    }

    public  Adress (String line, String station){
        this.line = line;
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
