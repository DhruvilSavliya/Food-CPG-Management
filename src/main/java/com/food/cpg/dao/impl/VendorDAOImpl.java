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
        List<Vendor> vendorList = new ArrayList<>();
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from vendors")) {
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while(rs.next()) {
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

        return vendorList;
    }

    @Override
    public Vendor getVendor(int vendorId) {
        Vendor vendor = null;
        try(Connection connection = getDBConnection()) {
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

        return vendor;
    }

    @Override
    public void saveVendor(Vendor vendor) {
        String insertVendorQuery = "insert into vendors (vendor_name, vendor_address, contact_person_name, contact_person_email, contact_person_phone, manufacturer_id) values (?, ?, ?, ?, ?, ?)";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertVendorQuery)) {
                statement.setString(1, vendor.getName());
                statement.setString(2, vendor.getAddress());
                statement.setString(3, vendor.getContactPersonName());
                statement.setString(4, vendor.getContactPersonEmail());
                statement.setLong(5, vendor.getContactPersonPhone());
                statement.setInt(6, 1);

                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateVendor(Vendor vendor) {
        String insertVendorQuery = "update vendors set vendor_name = ?, vendor_address = ?, contact_person_name = ?, contact_person_email = ?, contact_person_phone = ? where vendor_id = ?";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertVendorQuery)) {
                statement.setString(1, vendor.getName());
                statement.setString(2, vendor.getAddress());
                statement.setString(3, vendor.getContactPersonName());
                statement.setString(4, vendor.getContactPersonEmail());
                statement.setLong(5, vendor.getContactPersonPhone());
                statement.setInt(6, vendor.getId());

                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteVendor(int vendorId) {
        String insertVendorQuery = "delete from vendors where vendor_id = ?";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertVendorQuery)) {
                statement.setInt(1, vendorId);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }
}