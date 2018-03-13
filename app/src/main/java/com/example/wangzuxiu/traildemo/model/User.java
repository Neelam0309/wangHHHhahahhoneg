package com.example.wangzuxiu.traildemo.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 08/03/18.
 */

public class User {
    private String email;
    private String userName;
    public User(){

    }
    public User(String username, String email){
        this.userName=username;
        this.email=email;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return  email;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("email",email);
        result.put("username",userName);
        return result;
    }

}
