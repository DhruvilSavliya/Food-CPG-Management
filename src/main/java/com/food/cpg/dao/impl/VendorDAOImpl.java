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

import com.food.cpg.dao.IVendorDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.Vendor;

/**
 * Vendor DAO implementation
 *
 * @author Kartik Gevariya
 */
@Repository
public class VendorDAOImpl extends AbstractBaseDAO implements IVendorDAO {

    private static final Logger LOG = LoggerFactory.getLogger(VendorDAOImpl.class);

    @Autowired
    public VendorDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vendor> getVendorsList(int manufacturerId) {
        LOG.info("IN VendorDAOImpl : getVendorsList : manufacturerId - {}", manufacturerId);
        List<Vendor> vendorList = new ArrayList<>();
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from vendors where manufacturer_id = ?")) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while (rs.next()) {
                            Vendor vendor = new Vendor();
                            vendor.setId(rs.getInt("vendor_id"));
                            vendor.setName(rs.getString("vendor_name"));
                            vendor.setAddress(rs.getString("vendor_address"));
                            vendor.setContactPersonName(rs.getString("contact_person_name"));
                            vendor.setContactPersonEmail(rs.getString("contact_person_email"));
                            vendor.setContactPersonPhone(rs.getLong("contact_person_phone"));
                            vendorList.add(vendor);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT VendorDAOImpl : getVendorsList : Vendor count - {}", vendorList.size());
        return vendorList;
    }

    @Override
    public Vendor getVendor(int vendorId) {
        LOG.info("IN VendorDAOImpl : getVendor : vendorId - {}", vendorId);
        Vendor vendor = null;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from vendors where vendor_id = ?")) {
                statement.setInt(1, vendorId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null && rs.next()) {
                        vendor = new Vendor();
                        vendor.setId(rs.getInt("vendor_id"));
                        vendor.setName(rs.getString("vendor_name"));
                        vendor.setAddress(rs.getString("vendor_address"));
                        vendor.setContactPersonName(rs.getString("contact_person_name"));
                        vendor.setContactPersonEmail(rs.getString("contact_person_email"));
                        vendor.setContactPersonPhone(rs.getLong("contact_person_phone"));
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT VendorDAOImpl : getVendor");
        return vendor;
    }

    @Override
    public void saveVendor(Vendor vendor) {
        LOG.info("IN VendorDAOImpl : saveVendor");

        String insertVendorQuery = "insert into vendors (vendor_name, vendor_address, contact_person_name, contact_person_email, contact_person_phone, manufacturer_id) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertVendorQuery)) {
                statement.setString(1, vendor.getName());
                statement.setString(2, vendor.getAddress());
                statement.setString(3, vendor.getContactPersonName());
                statement.setString(4, vendor.getContactPersonEmail());
                statement.setLong(5, vendor.getContactPersonPhone());
                statement.setInt(6, 1);

                statement.executeUpdate();
                LOG.info("Vendor saved successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT VendorDAOImpl : saveVendor");
    }

    @Override
    public void updateVendor(Vendor vendor) {
        LOG.info("IN VendorDAOImpl : updateVendor");

        String updateQuery = "update vendors set vendor_name = ?, vendor_address = ?, contact_person_name = ?, contact_person_email = ?, contact_person_phone = ? where vendor_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setString(1, vendor.getName());
                statement.setString(2, vendor.getAddress());
                statement.setString(3, vendor.getContactPersonName());
                statement.setString(4, vendor.getContactPersonEmail());
                statement.setLong(5, vendor.getContactPersonPhone());
                statement.setInt(6, vendor.getId());

                statement.executeUpdate();
                LOG.info("Vendor updated successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT VendorDAOImpl : updateVendor");
    }

    @Override
    public void deleteVendor(int vendorId) {
        LOG.info("IN VendorDAOImpl : deleteVendor");

        String deleteQuery = "delete from vendors where vendor_id = ?";
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, vendorId);
                statement.executeUpdate();
                LOG.info("Vendor deleted successfully.");
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
        LOG.info("OUT VendorDAOImpl : deleteVendor");
    }
}