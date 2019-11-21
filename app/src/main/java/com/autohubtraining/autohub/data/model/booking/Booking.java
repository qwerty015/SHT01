package com.autohubtraining.autohub.data.model.booking;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    private String bookingId;
    private int status;

    @ServerTimestamp
    private Date timestamp;

    private String name;
    private String price;
    private float score;
    private String feedback;
    private String distance;

    private String photographerId;
    private String photographerName;
    private String photographerAvatarUrl;
    private String cameraInfo;

    private String clientId;
    private String clientName;
    private String clientAvatarUrl;

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getScore() {
        return score;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerName(String photographerName) {
        this.photographerName = photographerName;
    }

    public String getPhotographerName() {
        return photographerName;
    }

    public void setPhotographerAvatarUrl(String photographerAvatarUrl) {
        this.photographerAvatarUrl = photographerAvatarUrl;
    }

    public String getPhotographerAvatarUrl() {
        return photographerAvatarUrl;
    }

    public void setCameraInfo(String cameraInfo) {
        this.cameraInfo = cameraInfo;
    }

    public String getCameraInfo() {
        return cameraInfo;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientAvatarUrl(String clientAvatarUrl) {
        this.clientAvatarUrl = clientAvatarUrl;
    }

    public String getClientAvatarUrl() {
        return clientAvatarUrl;
    }
}
