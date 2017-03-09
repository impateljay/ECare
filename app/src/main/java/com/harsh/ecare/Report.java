package com.harsh.ecare;

/**
 * Created by Jay on 10-03-2017.
 */

public class Report {

    private String fileName;
    private String downloadURL;
    private String uploadedBy;
    private String uploadedFor;

    public Report() {
    }

    public Report(String fileName, String downloadURL, String uploadedBy, String uploadedFor) {
        this.fileName = fileName;
        this.downloadURL = downloadURL;
        this.uploadedBy = uploadedBy;
        this.uploadedFor = uploadedFor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedFor() {
        return uploadedFor;
    }

    public void setUploadedFor(String uploadedFor) {
        this.uploadedFor = uploadedFor;
    }
}
