package com.example.wangzuxiu.traildemo.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 09/03/18.
 */

public class Station {


    private int sequence;
    private String stationName;
    private String GPS;
    private String instructions;
    private String stationKey;

    public Station() {
    }

    public Station(String stationName, String GPS, String instructions,String stationKey) {
        // this.sequence = sequence;
        this.stationName = stationName;
        this.GPS = GPS;
        this.instructions = instructions;
        this.stationKey = stationKey;
    }

    public int getSequence() {
        return sequence;
    }

    public String getStationName() {
        return stationName;
    }

    public String getGPS() {
        return GPS;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getStationKey() {
        return stationKey;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("userId", userId);
        result.put("stationName", stationName);
        result.put("location",GPS);
        result.put("instructions", instructions);
        result.put("stationKey",stationKey);
        return result;
    }
}
