package com.food.cpg.manufacturer.registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.manufacturer.Manufacturer;
import com.food.cpg.manufacturer.registration.IRegistrationPersistence;
import com.food.cpg.manufacturer.registration.Registration;

public class RegistrationDatabasePersistence implements IRegistrationPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RegistrationDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }


    @Override
    public List<Registration> getAll() {
        List<Registration> registrations = new ArrayList<>();

        String sql = "select * from manufacturer m left join users u on m.manufacturer_email = u.email";

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        Manufacturer manufacturer = new Manufacturer();
                        manufacturer.setId(rs.getInt("manufacturer_id"));
                        manufacturer.setCompanyName(rs.getString("manufacturer_company_name"));
                        manufacturer.setEmail(rs.getString("manufacturer_email"));
                        manufacturer.setContact(rs.getLong("manufacturer_contact"));
                        manufacturer.setAddress(rs.getString("manufacturer_address"));

                        Registration registration = new Registration();
                        registration.setManufacturer(manufacturer);
                        registration.setStatus(rs.getString("status"));

                        registrations.add(registration);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return registrations;
    }

    @Override
    public void approve(String email) {
        String sql = "update users set status = 'Approved' where email = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(email);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void block(String email) {
        String sql = "update users set status = 'Blocked' where email = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(email);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}