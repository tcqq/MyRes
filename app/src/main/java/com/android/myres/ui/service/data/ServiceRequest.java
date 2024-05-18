package com.android.myres.ui.service.data;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class ServiceRequest {
    private String requestNumber;
    private String requestDetails;
    private String requestStatus;

    public ServiceRequest(String requestNumber, String requestDetails, String requestStatus) {
        this.requestNumber = requestNumber;
        this.requestDetails = requestDetails;
        this.requestStatus = requestStatus;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public String getRequestStatus() {
        return requestStatus;
    }
}