package com.food.cpg.manufacturer.registration;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationDatabasePersistenceTest {

    private static final String TEST_CONTACT_EMAIL = "rotesh@testregistration.com";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    Registration registration;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.getAll();

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(8)).getString(anyString());
        verify(resultSet, times(2)).getLong(anyString());
    }

    @Test
    public void approveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.approve(TEST_CONTACT_EMAIL);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
//        verify(registration, times(1)).getStatus();
    }

    @Test
    public void blockTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.approve(TEST_CONTACT_EMAIL);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
//        verify(registration, times(1)).getStatus();
    }

}
