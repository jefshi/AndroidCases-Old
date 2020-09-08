package com.csp.cases.activity.network.visit.retrolfit.hce;


import java.io.File;
import java.util.List;

public class UploadFileListReq {

    private String bizType = "bizType";
    private String appId = "zqyl-app";
    private String userId = "userId";
    private String fileName = "fileName";
    private List<File> files;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
