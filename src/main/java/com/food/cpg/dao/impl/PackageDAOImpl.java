package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.dao.IPackageDAO;
import com.food.cpg.models.Packages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.RawMaterial;

/**
 * Package DAO implementation
 *
 * @author Dhruvilkumar Savliya
 */
@Repository
public class PackageDAOImpl extends AbstractBaseDAO implements IPackageDAO
{

    private static final Logger LOG = LoggerFactory.getLogger(PackageDAOImpl.class);

    @Autowired
    public PackageDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public List<Packages> getPackageList(int manufacturerId) {
        LOG.info("IN PackageDAOImpl : getPackageList : manufacturerId - {}", manufacturerId);
        List<Packages> packageList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from packages where manufacturer_id = ?")) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            Packages packages = new Packages();
                            packages.setPackageId(rs.getInt("package_id"));
                            packages.setPackageName(rs.getString("package_name"));

                            RawMaterial rawMaterial = new RawMaterial();
                            rawMaterial.setId(rs.getInt("item_id"));
                            packages.setRawMaterial(rawMaterial);
                            packages.setItemId(rs.getInt("item_id"));
//                            packages.setUnitCost(rs.getInt("item_id"));

                            packages.setPackageName(rs.getString("package_name"));
                            packages.setQuantity(rs.getInt("quantity"));
                            packages.setManufacturingCost(rs.getInt("manufacturing_cost"));
                            packages.setWholesaleCost(rs.getInt("wholesale_cost"));
                            packages.setRetailCost(rs.getInt("retail_cost"));

                            packageList.add(packages);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PackageDAOImpl : getPackageList : Package count - {}", packageList.size());
        return packageList;
    }

    @Override
    public Packages getPackages(int packageId) {
        LOG.info("IN PackageDAOImpl : getPackages : packageId - {}", packageId);
        Packages packages = null;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from packages where package_id = ?")) {
                statement.setInt(1, packageId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null && rs.next()) {
                        packages = new Packages();
                        packages.setPackageId(rs.getInt("package_id"));
                        packages.setPackageName(rs.getString("package_name"));

//                        RawMaterial rawMaterial = new RawMaterial();
//                        rawMaterial.setId(rs.getInt("item_id"));
//                        packages.setRawMaterial(rawMaterial);
//                        packages.setItemId(rs.getInt("item_id"));
////                        packages.setUnitCost(rs.getInt("item_id"));



                        packages.setPackageName(rs.getString("package_name"));
                        packages.setQuantity(rs.getInt("quantity"));
                        packages.setManufacturingCost(rs.getInt("manufacturing_cost"));
                        packages.setWholesaleCost(rs.getInt("wholesale_cost"));
                        packages.setRetailCost(rs.getInt("retail_cost"));
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT PackageDAOImpl : getPackages");
        return packages;
    }

    @Override
    public void savePackages(Packages packages) {
        LOG.info("IN PackageDAOImpl : savePackages");

        String insertQuery = "insert into packages (item_id, package_name, quantity, manufacturing_cost, wholesale_cost, retail_cost, manufacturer_id) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, packages.getRawMaterialId());
                statement.setString(2, packages.getPackageName());
                statement.setInt(3, packages.getQuantity());
                statement.setInt(4, packages.getManufacturingCost());
                statement.setInt(5, packages.getWholesaleCost());
                statement.setInt(6, packages.getRetailCost());
                statement.setInt(7, 1);
                statement.executeUpdate();
                LOG.info("Package saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PackageDAOImpl : savePackages");
    }

    @Override
    public void updatePackages(Packages packages) {
        LOG.info("IN PackageDAOImpl : updatePackages");

        String updateQuery = "update packages set item_id = ?, package_name = ?, quantity = ?, manufacturing_cost = ?, wholesale_cost = ?, retail_cost = ? where package_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setInt(1, packages.getRawMaterialId());
                statement.setString(2, packages.getPackageName());
                statement.setInt(3, packages.getQuantity());
                statement.setInt(4, packages.getManufacturingCost());
                statement.setInt(5, packages.getWholesaleCost());
                statement.setInt(6, packages.getRetailCost());
                statement.setInt(7, packages.getPackageId());

                statement.executeUpdate();
                LOG.info("Package updated successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PackageDAOImpl : updatePackages");
    }

    @Override
    public void deletePackages(int packageId) {
        LOG.info("IN PackageDAOImpl : deletePackages");

        String deleteQuery = "delete from packages where package_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, packageId);
                statement.executeUpdate();
                LOG.info("Package deleted successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT PackageDAOImpl : deletePackages");
    }
}