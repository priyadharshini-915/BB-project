package com.bloodbank.model;

/**
 * BloodStock - represents the available blood units for a blood group.
 */
public class BloodStock {

    private int id;
    private String bloodGroup;
    private int units;
    private String updatedDate;

    public BloodStock() {
    }

    public BloodStock(int id, String bloodGroup, int units, String updatedDate) {
        this.id = id;
        this.bloodGroup = bloodGroup;
        this.units = units;
        this.updatedDate = updatedDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }

    public String getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(String updatedDate) { this.updatedDate = updatedDate; }
}
