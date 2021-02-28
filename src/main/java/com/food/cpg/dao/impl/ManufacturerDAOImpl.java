package com.food.cpg.dao.impl;

import com.food.cpg.dao.IManufacturerDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.Manufacturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManufacturerDAOImpl extends AbstractBaseDAO implements IManufacturerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerDAOImpl.class);

    @Autowired
    public ManufacturerDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        String insertManufacturerQuery = "insert into manufacturer (manufacturer_company_name, manufacturer_email, manufacturer_password, manufacturer_contact, manufacturer_address) values (?, ?, ?, ?, ?)";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertManufacturerQuery)) {
                statement.setString(1, manufacturer.getCompanyName());
                System.out.println(manufacturer.getCompanyName());
                System.out.println(manufacturer.getEmail());
                System.out.println(manufacturer.getPassword());
                System.out.println(manufacturer.getContact());
                System.out.println(manufacturer.getAddress());
                statement.setString(2, manufacturer.getEmail());
                statement.setString(3, manufacturer.getPassword());
                statement.setLong(4, manufacturer.getContact());
                statement.setString(5, manufacturer.getAddress());

                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Manufacturer> getManufacturerList(int manufacturerId) {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from manufacturer")) {
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null) {
                        while(rs.next()) {
                            Manufacturer manufacturer = new Manufacturer();
                            manufacturer.setId(rs.getInt("manufacturer_id"));
                            manufacturer.setCompanyName(rs.getString("manufacturer_company_name"));
                            manufacturer.setEmail(rs.getString("manufacturer_email"));
                            manufacturer.setPassword(rs.getString("manufacturer_password"));
                            manufacturer.setContact(rs.getLong("manufacturer_contact"));
                            manufacturer.setAddress(rs.getString("manufacturer_address"));
                            manufacturer.setStatus(rs.getString("status"));
                            manufacturerList.add(manufacturer);
                        }
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        return manufacturerList;
    }

    @Override
    public Manufacturer getManufacturer(int manufacturerId) {
        Manufacturer manufacturer = null;
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from manufacturer where manufacturer_id = ?")) {
                statement.setInt(1, manufacturerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null && rs.next()) {
                        manufacturer = new Manufacturer();
                        manufacturer.setId(rs.getInt("manufacturer_id"));
                        manufacturer.setCompanyName(rs.getString("manufacturer_company_name"));
                        manufacturer.setEmail(rs.getString("manufacturer_email"));
                        manufacturer.setPassword(rs.getString("manufacturer_password"));
                        manufacturer.setContact(rs.getLong("manufacturer_contact"));
                        manufacturer.setAddress(rs.getString("manufacturer_address"));
                        manufacturer.setStatus(rs.getString("status"));
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        return manufacturer;
    }

    @Override
    public void approveManufacturer(int manufacturerId) {
        String approveManufacturerQuery = "update manufacturer set status='Approved' where manufacturer_id = ?";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(approveManufacturerQuery)) {
                statement.setInt(1, manufacturerId);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockManufacturer(int manufacturerId) {
        String blockManufacturerQuery = "update manufacturer set status='Blocked' where manufacturer_id = ?";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(blockManufacturerQuery)) {
                statement.setInt(1, manufacturerId);
                statement.executeUpdate();
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }

}
