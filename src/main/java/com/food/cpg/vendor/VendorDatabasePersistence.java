package com.food.cpg.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

public class VendorDatabasePersistence implements IVendorPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public VendorDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<Vendor> getAll(int manufacturerId) {
        List<Vendor> vendorList = new ArrayList<>();

        String sql = "select * from vendors where manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Vendor vendor = new Vendor();
                        loadVendorDetailsFromResultSet(rs, vendor);

                        vendorList.add(vendor);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return vendorList;
    }

    @Override
    public void load(Vendor vendor) {
        String sql = "select * from vendors where vendor_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadVendorDetailsFromResultSet(rs, vendor);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Vendor vendor) {
        String sql = "insert into vendors (vendor_name, vendor_address, contact_person_name, contact_person_email, contact_person_phone, manufacturer_id) values (?, ?, ?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getName());
        placeholderValues.add(vendor.getAddress());
        placeholderValues.add(vendor.getContactPersonName());
        placeholderValues.add(vendor.getContactPersonEmail());
        placeholderValues.add(vendor.getContactPersonPhone());
        placeholderValues.add(vendor.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Vendor vendor) {
        String sql = "update vendors set vendor_name = ?, vendor_address = ?, contact_person_name = ?, contact_person_email = ?, contact_person_phone = ? where vendor_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getName());
        placeholderValues.add(vendor.getAddress());
        placeholderValues.add(vendor.getContactPersonName());
        placeholderValues.add(vendor.getContactPersonEmail());
        placeholderValues.add(vendor.getContactPersonPhone());
        placeholderValues.add(vendor.getId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int vendorId) {
        String sql = "delete from vendors where vendor_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendorId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private void loadVendorDetailsFromResultSet(ResultSet resultSet, Vendor vendor) throws SQLException {
        vendor.setId(resultSet.getInt("vendor_id"));
        vendor.setName(resultSet.getString("vendor_name"));
        vendor.setAddress(resultSet.getString("vendor_address"));
        vendor.setContactPersonName(resultSet.getString("contact_person_name"));
        vendor.setContactPersonEmail(resultSet.getString("contact_person_email"));
        vendor.setContactPersonPhone(resultSet.getLong("contact_person_phone"));
    }
}