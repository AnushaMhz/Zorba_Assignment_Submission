package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalSystem {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);

        HospitalBedInfo hospitalBedInfo = new HospitalBedInfo();
        hospitalBedInfo.inputDetails();
        hospitalBedInfo.saveToDatabase();

        //Get Hospital details
        System.out.println("Enter Hospital ID: ");
        String hospitalId = scanner.nextLine();

        System.out.println("Enter Hospital Name");
        String hospitalName = scanner.nextLine();
        Hospital hospital = new Hospital(hospitalId, hospitalName);

        //Get Patient Details

        System.out.println("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        System.out.println("Enter Patient Name: ");
        String patientName = scanner.nextLine();

        System.out.println("Enter Patient Type  " + " Critical/ Normal: " );
        String patientType = scanner.nextLine();

        System.out.println("Enter Number of Days: ");
        int noOfDays = scanner.nextInt();

        Patient patient = new Patient(patientId, patientName, patientType, noOfDays);
        saveToDatabase(hospital, patient);
        writeToExcel(hospital, patient);
        scanner.close();

    }

    private static void writeToExcel(Hospital hospital, Patient patient) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Hospital Data");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Hospital Name");
        header.createCell(1).setCellValue("Patient Name");
        header.createCell(2).setCellValue("Patient Type");
        header.createCell(3).setCellValue("Bed Type");
        header.createCell(4).setCellValue("No of Days");
        header.createCell(5).setCellValue("Total Bed Charges");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(hospital.getHospitalName());
        dataRow.createCell(1).setCellValue(patient.getPatientName());
        dataRow.createCell(2).setCellValue(patient.getPatientType());
        dataRow.createCell(3).setCellValue(patient.getPatientType().equals("Critical") ? "Emergency" : "Normal");
        dataRow.createCell(4).setCellValue(patient.getNoOfDays());
        dataRow.createCell(5).setCellValue(patient.getTotalBedCharges());

        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\mahar\\OneDrive\\Desktop\\Hospital Data.xlsx")) {
            workbook.write(fileOutputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabase(Hospital hospital, Patient patient) {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zorba_1015", "root", "Heythere!777")) {
            String insertHospital = "INSERT INTO Hospital (hospital_id, hospital_name, patient_id) VALUES (?, ?, ?)";

            try(PreparedStatement preparedStatement = connection.prepareStatement(insertHospital)) {
                preparedStatement.setString(1, hospital.getHospitalId());
                preparedStatement.setString(2, hospital.getHospitalName());
                preparedStatement.setString(3, patient.getPatientId());
                preparedStatement.executeUpdate();
            }
            String insertPatient = "INSERT INTO Patient (patient_id, patient_name, patient_type, no_of_days, total_bed_charges) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(insertPatient)) {
                preparedStatement.setString(1, patient.getPatientId());
                preparedStatement.setString(2, patient.getPatientName());
                preparedStatement.setString(3, patient.getPatientType());
                preparedStatement.setInt(4, patient.getNoOfDays());
                preparedStatement.setDouble(5, patient.getTotalBedCharges());
                preparedStatement.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
