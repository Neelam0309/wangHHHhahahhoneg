package com.example.wangzuxiu.traildemo.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 15/03/18.
 */

public class Discussion {
    public String userId;
    public String topic;
    public String timestamp;
    public String discussionId;

    public Discussion(){

    }
    public Discussion(String userId,String topic,String timestamp,String discussionId){
        this.userId=userId;
        this.timestamp=timestamp;
        this.topic=topic;
        this.discussionId=discussionId;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("userId", userId);
        result.put("userId", userId);
        result.put("topic",topic);
        result.put("timestamp", timestamp);
        result.put("discussionId",discussionId);
        return result;
    }
}
