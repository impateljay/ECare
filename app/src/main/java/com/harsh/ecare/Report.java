package com.harsh.ecare;

/**
 * Created by Jay on 10-03-2017.
 */

public class Report {

    private String fileName;
    private String downloadURL;
    private String uploadedBy;

    public Report() {
    }

    public Report(String fileName, String downloadURL, String uploadedBy) {
        this.fileName = fileName;
        this.downloadURL = downloadURL;
        this.uploadedBy = uploadedBy;
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
}
