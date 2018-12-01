package com.eldar.fit.seminarski.data;

public class ApiUserImage {

    public String encodedImageBase64;

    public String fileName;

    public int userId;


    public ApiUserImage(String encodedImageBase64, String fileName, int userId) {
        this.encodedImageBase64 = encodedImageBase64;
        this.fileName = fileName;
        this.userId = userId;
    }

    public String getEncodedImageBase64() {
        return encodedImageBase64;
    }

    public void setEncodedImageBase64(String encodedImageBase64) {
        this.encodedImageBase64 = encodedImageBase64;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
