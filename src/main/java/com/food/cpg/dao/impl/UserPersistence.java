package com.food.cpg.dao.impl;

import com.food.cpg.dao.IUserPersistence;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserPersistence extends AbstractBaseDAO implements IUserPersistence {

    private static final Logger LOG = LoggerFactory.getLogger(UserPersistence.class);

    @Autowired
    public UserPersistence(DataSource ds) {
        super(ds);
    }


    @Override
    public void saveUser(User user) {
        String insertUserQuery = "insert into users (email, password, role) values(?,?,?)";
        try(Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertUserQuery)) {
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole());

                statement.executeUpdate();
            }

        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }
    }
}
