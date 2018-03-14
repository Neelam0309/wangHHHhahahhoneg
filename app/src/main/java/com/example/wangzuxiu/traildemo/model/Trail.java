package com.example.wangzuxiu.traildemo.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 09/03/18.
 */

public class Trail {

    //public String trailId;
    //public String userId;
    public String trailName;
    public String trailDate;
    public String timestamp;
    public String trailId;
    public String key;

    public Trail(){

    }

    public Trail(String trailName, String trailDate, String timestamp,String trailId,String key){
        this.timestamp=timestamp;
        this.trailDate=trailDate;
        this.trailName=trailName;
        this.trailId=trailId;
        this.key=key;
        //this.userId=userId;
        //this.trailId=trailId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("userId", userId);
        result.put("trailName", trailName);
        result.put("trailDate", trailDate);
        result.put("timestamp", timestamp);
        result.put("trailId",trailId);
        result.put("key",key);

        return result;
    }

}
