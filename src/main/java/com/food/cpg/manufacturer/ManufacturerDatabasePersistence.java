package com.food.cpg.manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class ManufacturerDatabasePersistence implements IManufacturerPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ManufacturerDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public Manufacturer get(String manufacturerEmail) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setEmail(manufacturerEmail);
        load(manufacturer);
        return manufacturer;
    }

    @Override
    public void load(Manufacturer manufacturer) {
        String sql = "select * from manufacturer where manufacturer_email = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        manufacturer.setId(rs.getInt("manufacturer_id"));
                        manufacturer.setCompanyName(rs.getString("manufacturer_company_name"));
                        manufacturer.setEmail(rs.getString("manufacturer_email"));
                        manufacturer.setContact(rs.getLong("manufacturer_contact"));
                        manufacturer.setAddress(rs.getString("manufacturer_address"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void register(Manufacturer manufacturer) {
        String sql = "insert into manufacturer (manufacturer_company_name, manufacturer_email, manufacturer_contact, manufacturer_address) values (?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getCompanyName());
        placeholderValues.add(manufacturer.getEmail());
        placeholderValues.add(manufacturer.getContact());
        placeholderValues.add(manufacturer.getAddress());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createLoginAccount(Manufacturer manufacturer) {
        String sql = "insert into users (email, password) values(?,?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());
        placeholderValues.add(manufacturer.getPassword());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}