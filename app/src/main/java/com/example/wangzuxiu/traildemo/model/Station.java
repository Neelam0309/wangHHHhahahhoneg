package com.example.wangzuxiu.traildemo.model;

/**
 * Created by wangzuxiu on 09/03/18.
 */

public class Station {


    private int sequence;
    private String stationName;
    private String gps;
    private String instructions;
    private String stationKey;

    public Station() {
    }

    public Station(String stationName, String gps, String instructions,String stationKey) {
        // this.sequence = sequence;
        this.stationName = stationName;
        this.gps = gps;
        this.instructions = instructions;
        this.stationKey = stationKey;
    }

    public int getSequence() {
        return sequence;
    }

    public String getStationName() {
        return stationName;
    }

    public String getGps() {
        return gps;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getStationKey() {
        return stationKey;
    }


}
