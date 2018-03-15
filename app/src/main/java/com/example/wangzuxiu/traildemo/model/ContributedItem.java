package com.example.wangzuxiu.traildemo.model;



/**
 * Created by hongweixiang on 13/03/2018.
 */

public class ContributedItem {
    private String userId;
    private String fileURL;
    private String description;

    private String userName;
    private String timeCreation;
    private String fileType;

    public ContributedItem() {
    }

    public ContributedItem(String userId, String fileURL, String description) {
        this.userId = userId;
        this.fileURL = fileURL;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeCreation() {
        return timeCreation;
    }

    public void setTimeCreation(String timeCreation) {
        this.timeCreation = timeCreation;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
