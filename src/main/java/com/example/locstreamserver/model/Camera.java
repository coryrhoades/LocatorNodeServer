package com.example.locstreamserver.model;

import java.util.Objects;
import java.time.LocalDateTime;

public class Camera {
    int cameraId;
    int accountId;
    String cameraIP;
    int username;
    int password;
    String cameraDescription;
    String cameraName;
    LocalDateTime dateAdded;


    public Camera(int cameraId, int accountId, String cameraIP, int username, int password) {
        this.cameraId = cameraId;
        this.accountId = accountId;
        this.cameraIP = cameraIP;
        this.username = username;
        this.password = password;
    }


    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCameraIP() {
        return cameraIP;
    }

    public void setCameraIP(String cameraIP) {
        this.cameraIP = cameraIP;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getCameraDescription() {
        return cameraDescription;
    }

    public void setCameraDescription(String cameraDescription) {
        this.cameraDescription = cameraDescription;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return cameraId == camera.cameraId && accountId == camera.accountId && username == camera.username && password == camera.password && Objects.equals(cameraIP, camera.cameraIP) && Objects.equals(cameraDescription, camera.cameraDescription) && Objects.equals(cameraName, camera.cameraName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cameraId, accountId, cameraIP, username, password, cameraDescription, cameraName);
    }
}
