package com.bloodbank.model;

/**
 * BloodRequest - represents a request for blood made by a user.
 */
public class BloodRequest {

    private int id;
    private String patientName;
    private String bloodGroup;
    private int requiredUnits;
    private String hospitalName;
    private String contactNumber;
    private String requestDate;
    private String address;
    private String reason;
    private String status;

    public BloodRequest() {
    }

    public BloodRequest(int id, String patientName, String bloodGroup, int requiredUnits,
                        String hospitalName, String contactNumber, String requestDate,
                        String address, String reason, String status) {
        this.id = id;
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.requiredUnits = requiredUnits;
        this.hospitalName = hospitalName;
        this.contactNumber = contactNumber;
        this.requestDate = requestDate;
        this.address = address;
        this.reason = reason;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getRequiredUnits() { return requiredUnits; }
    public void setRequiredUnits(int requiredUnits) { this.requiredUnits = requiredUnits; }

    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getRequestDate() { return requestDate; }
    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
