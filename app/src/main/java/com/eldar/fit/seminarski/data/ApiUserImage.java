package com.eldar.fit.seminarski.data;

public class ApiUserImage {

    public String encodedImageBase64;

    public String fileName;

    public AuthLogin credentials;


    public ApiUserImage(String encodedImageBase64, String fileName, AuthLogin credentials) {
        this.encodedImageBase64 = encodedImageBase64;
        this.fileName = fileName;
        this.credentials = credentials;
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



}
