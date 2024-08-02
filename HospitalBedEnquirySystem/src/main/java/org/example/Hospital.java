package org.example;

public class Hospital {
    public String hospitalId;
    public String hospitalName;

    public Hospital(String hospitalId, String hospitalName) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }
    public String getHospitalId() {
        return hospitalId;

    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

}

