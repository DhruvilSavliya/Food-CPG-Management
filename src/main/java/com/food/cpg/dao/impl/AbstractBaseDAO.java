package com.food.cpg.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.food.cpg.exceptions.DBException;

/**
 * Abstract DAO implementation
 *
 * @author Kartik Gevariya
 */
@Repository
public abstract class AbstractBaseDAO {

    private static final String ERROR_MESSAGE = "An error was encountered whilst connecting to the database. Please try again later..";
    protected DataSource dataSource;

    public AbstractBaseDAO(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Returns database connection
     *
     * @return Database connection object
     * @throws DBException When error occurs while connecting to database/setting transaction isolation
     */
    protected Connection getDBConnection() throws DBException {
        if (this.dataSource != null) {
            return DataSourceUtils.getConnection(this.dataSource);
        } else {
            throw new DBException("Invalid datasource!");
        }
    }

    /**
     * Returns prepared statement for the given sql
     *
     * @param sql Sql statement for which prepared statement needs to be prepared
     * @return Prepared statement object
     * @throws DBException When error occurs while connecting to database
     */
    protected PreparedStatement getPreparedStatement(String sql) throws DBException {
        try {
            return getDBConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new DBException(ERROR_MESSAGE + e.getMessage(), e);
        }
    }

    /**
     * Returns prepared statement for the given sql
     *
     * @param sql                 Sql statement for which prepared statement needs to be prepared
     * @param returnGeneratedKeys boolean flag for RETURN_GENERATED_KEYS
     * @return Prepared statement object
     * @throws DBException When error occurs while connecting to database
     */
    protected PreparedStatement getPreparedStatement(String sql, boolean returnGeneratedKeys) throws DBException {
        try {
            if (returnGeneratedKeys)
                return getDBConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            else
                return getPreparedStatement(sql);
        } catch (SQLException e) {
            throw new DBException(ERROR_MESSAGE + e.getMessage(), e);
        }
    }
}