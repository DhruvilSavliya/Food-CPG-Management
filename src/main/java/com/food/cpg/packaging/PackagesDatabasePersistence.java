package com.food.cpg.packaging;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagesDatabasePersistence implements IPackagesPersistence {
    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PackagesDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<Packages> getAll(int manufacturerId) {
        List<Packages> packagesList = new ArrayList<>();

        String sql = "select * from packages where manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Packages packages = new Packages();
                        loadPackagesDetailsFromResultSet(rs, packages);

                        packagesList.add(packages);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return packagesList;
    }

    @Override
    public void load(Packages packages) {
        String sql = "select * from packages where package_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getPackageId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadPackagesDetailsFromResultSet(rs, packages);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Packages packages) {
        String sql = "insert into packages (item_id, package_name, quantity, manufacturing_cost, wholesale_cost, retail_cost, manufacturer_id) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getItemId());
        placeholderValues.add(packages.getPackageName());
        placeholderValues.add(packages.getQuantity());
        placeholderValues.add(packages.getManufacturingCost());
        placeholderValues.add(packages.getWholesaleCost());
        placeholderValues.add(packages.getRetailCost());
        placeholderValues.add(packages.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Packages packages) {
        String sql = "update packages set item_id = ?, package_name = ?, quantity = ?, manufacturing_cost = ?, wholesale_cost = ?, retail_cost = ? where package_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getItemId());
        placeholderValues.add(packages.getPackageName());
        placeholderValues.add(packages.getQuantity());
        placeholderValues.add(packages.getManufacturingCost());
        placeholderValues.add(packages.getWholesaleCost());
        placeholderValues.add(packages.getRetailCost());
        placeholderValues.add(packages.getPackageId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int packageId) {
        String sql = "delete from packages where package_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packageId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    private void loadPackagesDetailsFromResultSet(ResultSet resultSet, Packages packages) throws SQLException {
        packages.setPackageId(resultSet.getInt("package_id"));
        packages.setItemId(resultSet.getInt("item_id"));
        packages.setPackageName(resultSet.getString("package_name"));
        packages.setQuantity(resultSet.getDouble("quantity"));
        packages.setManufacturingCost(resultSet.getDouble("manufacturing_cost"));
        packages.setWholesaleCost(resultSet.getDouble("wholesale_cost"));
        packages.setRetailCost(resultSet.getDouble("retail_cost"));
    }
}
