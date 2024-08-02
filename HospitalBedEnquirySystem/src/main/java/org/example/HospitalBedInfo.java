package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalBedInfo implements BedInfo {
    private String bedId;
    private String bedType;
    private double bedChargesRate;
    private String hospitalId;
    public void inputDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Bed ID: ");
        bedId = scanner.nextLine();
        System.out.println("Enter Bed Type: " + "Emergency / Regular ");
        bedType = scanner.nextLine();
        System.out.println("Enter Bed Charges Rate: ");
        bedChargesRate = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

    }

    @Override
    public void saveToDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zorba_1015", "root", "Heythere!777");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Hospital_Bed_info (bed_id, bed_type, bed_charges_rate, hospital_id) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, bedId);
            preparedStatement.setString(2, bedType);
            preparedStatement.setDouble(3, bedChargesRate);
            preparedStatement.setString(4, hospitalId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
