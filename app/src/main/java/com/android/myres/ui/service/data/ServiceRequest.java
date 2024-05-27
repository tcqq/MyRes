package com.android.myres.ui.service.data;

public class ServiceRequest {

    private String userId;
    private String userName;
    private long timestamp;

    public ServiceRequest() {
        // Default constructor required for calls to DataSnapshot.getValue(ServiceRequest.class)
    }

    public ServiceRequest(String userId, String userName, long timestamp) {
        this.userId = userId;
        this.userName = userName;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
