package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.food.cpg.dao.IRawMaterialDAO;
import com.food.cpg.dao.IRawMaterialDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.RawMaterial;
import com.food.cpg.models.Vendor;

/**
 * RawMaterial DAO implementation
 *
 * @author Kartik Gevariya
 */
@Repository
public class RawMaterialDAOImpl extends AbstractBaseDAO implements IRawMaterialDAO {

    private static final Logger LOG = LoggerFactory.getLogger(RawMaterialDAOImpl.class);

    @Autowired
    public RawMaterialDAOImpl(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public List<RawMaterial> getRawMaterialsList(int manufacturerId) {
        LOG.info("IN RawMaterialDAOImpl : getRawMaterialsList : manufacturerId - {}", manufacturerId);
        List<RawMaterial> rawMaterialList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from raw_materials where manufacturer_id = ?")) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            RawMaterial rawMaterial = new RawMaterial();
                            rawMaterial.setId(rs.getInt("raw_material_id"));
                            rawMaterial.setName(rs.getString("raw_material_name"));

                            Vendor vendor = new Vendor();
                            vendor.setId(rs.getInt("vendor_id"));
                            rawMaterial.setVendor(vendor);
                            rawMaterial.setVendorId(rs.getInt("vendor_id"));

                            rawMaterial.setUnitCost(rs.getDouble("unit_cost"));
                            rawMaterial.setUnitMeasurement(rs.getDouble("unit_measurement"));
                            rawMaterial.setUnitMeasurementUOM(rs.getString("unit_measurement_uom"));
                            rawMaterial.setReorderPointQuantity(rs.getDouble("reorder_point_quantity"));
                            rawMaterial.setReorderPointQuantityUOM(rs.getString("reorder_point_quantity_uom"));

                            rawMaterialList.add(rawMaterial);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT RawMaterialDAOImpl : getRawMaterialsList : Raw material count - {}", rawMaterialList.size());
        return rawMaterialList;
    }

    @Override
    public RawMaterial getRawMaterial(int rawMaterialId) {
        LOG.info("IN RawMaterialDAOImpl : getRawMaterial : rawMaterialId - {}", rawMaterialId);
        RawMaterial rawMaterial = null;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from raw_materials where raw_material_id = ?")) {
                statement.setInt(1, rawMaterialId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null && rs.next()) {
                        rawMaterial = new RawMaterial();
                        rawMaterial.setId(rs.getInt("raw_material_id"));
                        rawMaterial.setName(rs.getString("raw_material_name"));

                        Vendor vendor = new Vendor();
                        vendor.setId(rs.getInt("vendor_id"));
                        rawMaterial.setVendor(vendor);
                        rawMaterial.setVendorId(rs.getInt("vendor_id"));

                        rawMaterial.setUnitCost(rs.getDouble("unit_cost"));
                        rawMaterial.setUnitMeasurement(rs.getDouble("unit_measurement"));
                        rawMaterial.setUnitMeasurementUOM(rs.getString("unit_measurement_uom"));
                        rawMaterial.setReorderPointQuantity(rs.getDouble("reorder_point_quantity"));
                        rawMaterial.setReorderPointQuantityUOM(rs.getString("reorder_point_quantity_uom"));
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT RawMaterialDAOImpl : getRawMaterial");
        return rawMaterial;
    }

    @Override
    public void saveRawMaterial(RawMaterial rawMaterial) {
        LOG.info("IN RawMaterialDAOImpl : saveRawMaterial");

        String insertQuery = "insert into raw_materials (raw_material_name, vendor_id, unit_cost, unit_measurement, unit_measurement_uom, reorder_point_quantity, reorder_point_quantity_uom, manufacturer_id) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, rawMaterial.getName());
                statement.setInt(2, rawMaterial.getVendorId());
                statement.setDouble(3, rawMaterial.getUnitCost());
                statement.setDouble(4, rawMaterial.getUnitMeasurement());
                statement.setString(5, rawMaterial.getUnitMeasurementUOM());
                statement.setDouble(6, rawMaterial.getReorderPointQuantity());
                statement.setString(7, rawMaterial.getReorderPointQuantityUOM());
                statement.setInt(8, 1);

                statement.executeUpdate();
                LOG.info("Raw material saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT RawMaterialDAOImpl : saveRawMaterial");
    }

    @Override
    public void updateRawMaterial(RawMaterial rawMaterial) {
        LOG.info("IN RawMaterialDAOImpl : updateRawMaterial");

        String updateQuery = "update raw_materials set raw_material_name = ?, vendor_id = ?, unit_cost = ?, unit_measurement = ?, unit_measurement_uom = ?, reorder_point_quantity = ?, reorder_point_quantity_uom = ? where raw_material_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, rawMaterial.getName());
                statement.setInt(2, rawMaterial.getVendorId());
                statement.setDouble(3, rawMaterial.getUnitCost());
                statement.setDouble(4, rawMaterial.getUnitMeasurement());
                statement.setString(5, rawMaterial.getUnitMeasurementUOM());
                statement.setDouble(6, rawMaterial.getReorderPointQuantity());
                statement.setString(7, rawMaterial.getReorderPointQuantityUOM());
                statement.setInt(8, rawMaterial.getId());

                statement.executeUpdate();
                LOG.info("Raw material updated successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT RawMaterialDAOImpl : updateRawMaterial");
    }

    @Override
    public void deleteRawMaterial(int rawMaterialId) {
        LOG.info("IN RawMaterialDAOImpl : deleteRawMaterial");

        String deleteQuery = "delete from raw_materials where raw_material_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, rawMaterialId);
                statement.executeUpdate();
                LOG.info("Raw material deleted successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT RawMaterialDAOImpl : deleteRawMaterial");
    }
}