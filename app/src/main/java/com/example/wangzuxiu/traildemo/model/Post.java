package com.example.wangzuxiu.traildemo.model;

/**
 * Created by wangzuxiu on 16/03/18.
 */

public class Post {
    public String userId;
    public String post;
    public String timestamp;
    public Post(){}
    public Post(String userId,String post,String timestamp){
        this.userId=userId;
        this.post=post;
        this.timestamp=timestamp;
    }
}
