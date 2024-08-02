package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportToExcel {
    public static void exportDataToExcel(Connection connection) throws SQLException {
        String sqlQuery = "\"SELECT h.hospital_name, p.patient_name, p.patient_type, b.bed_type, p.no_of_days, p.total_bed_charges " +
                                     "FROM Patient p " +
                                       "JOIN Hospital h ON p.hospital_id = h.hospital_id "+
                                       "JOIN Hospital_Bed_info b ON p.bed_id = b.bed_id";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Hospital Data");

            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Hospital Name");
            row.createCell(1).setCellValue("Patient Name");
            row.createCell(2).setCellValue("Patient Type");
            row.createCell(3).setCellValue("Bed Type");
            row.createCell(4).setCellValue("No of Days");
            row.createCell(5).setCellValue("Total Bed Charges");

            int rowNum = 1;

            while (resultSet.next()) {
               Row row1 = sheet.createRow(rowNum++);
                row1.createCell(0).setCellValue(resultSet.getString("hospital_name"));
                row1.createCell(1).setCellValue(resultSet.getString("patient_name"));
                row1.createCell(2).setCellValue(resultSet.getString("patient_type"));
                row1.createCell(3).setCellValue(resultSet.getString("bed_type"));
                row1.createCell(4).setCellValue(resultSet.getInt("no_of_days"));
                row1.createCell(5).setCellValue(resultSet.getDouble("total_bed_charges"));
            }

            try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\mahar\\IdeaProjects\\HospitalBedEnquirySystem\\src\\main\\resources\\Hospital Data.xlsx")){
                workbook.write(fileOutputStream);
            }
            workbook.close();

        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
