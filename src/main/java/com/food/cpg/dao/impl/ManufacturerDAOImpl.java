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
import java.sql.SQLException;

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
}
