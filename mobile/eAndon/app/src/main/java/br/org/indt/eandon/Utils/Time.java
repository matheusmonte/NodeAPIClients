package br.org.indt.eandon.Utils;


public class Time {
    public static String ConverterTimeMs(long value){

        long _value =  (value < 0) ? -1 * value : value;

        long hour = (_value / (1000 * 60 * 60)) % 24;
        long min =  (_value / (1000 * 60)) % 60;
        long sec =   (_value / 1000) % 60 ;

        return String.format("%02d:%02d:%2d", hour, min, sec);
    }
}
