package org.example;

public class Patient {
    public String patientId;
    public String patientName;
    public String patientType;
    private int noOfDays;
    private  double totalBedCharges;

    public Patient(String patientId, String patientName, String patientType, int noOfDays) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientType = patientType;
        this.noOfDays = noOfDays;
      calculateTotalBedCharge();
    }

    private void calculateTotalBedCharge() {
        double bedChargesRate = 0;
        if (patientType.equals("Critical")) {
            bedChargesRate = 50.00;

        }else if(patientType.equals("Normal")) {
            bedChargesRate = 30.00;
        }
        this.totalBedCharges = bedChargesRate * noOfDays;
    }

    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getPatientType() {
        return patientType;
    }
    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
    public double getTotalBedCharges() {
        return totalBedCharges;
    }

}
