package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.food.cpg.dao.IUserDAO;
import com.food.cpg.exceptions.DBException;
import com.food.cpg.exceptions.ServiceException;
import com.food.cpg.models.User;

@Repository
public class UserDAOImpl extends AbstractBaseDAO implements IUserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User getUser(int userId) {
        LOG.info("IN UserDAOImpl : getUser : userId - {}", userId);
        User user = null;
        try (Connection connection = getDBConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from user where userid = ?")) {
                statement.setInt(1, userId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs != null && rs.next()) {
                        user = new User();
                        user.setId(rs.getInt("userid"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(rs.getString("role"));
                        user.setStatus(rs.getBoolean("status"));
                    }
                }
            }
        } catch (DBException | SQLException e) {
            throw new ServiceException(e);
        }

        LOG.info("OUT UserDAOImpl : getUser");
        return user;
    }

}
