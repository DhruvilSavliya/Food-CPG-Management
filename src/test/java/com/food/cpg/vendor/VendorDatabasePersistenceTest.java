package com.food.cpg.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VendorDatabasePersistenceTest {

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    Vendor vendor;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.getAll(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(8)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getLong(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(vendor.getId()).thenReturn(1);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getId());

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.load(vendor);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(1)).getLong(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(vendor.getName()).thenReturn("Test Vendor 1");
        when(vendor.getAddress()).thenReturn("Test Address 1");
        when(vendor.getContactPersonName()).thenReturn("Test Person 1");
        when(vendor.getContactPersonEmail()).thenReturn("person1@vendor1.com");
        when(vendor.getContactPersonPhone()).thenReturn(9876543210L);
        when(vendor.getManufacturerId()).thenReturn(1);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.save(vendor);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(vendor, times(1)).getName();
        verify(vendor, times(1)).getAddress();
        verify(vendor, times(1)).getContactPersonName();
        verify(vendor, times(1)).getContactPersonEmail();
        verify(vendor, times(1)).getContactPersonPhone();
        verify(vendor, times(1)).getManufacturerId();
    }

    @Test
    public void updateTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(vendor.getName()).thenReturn("Test Vendor 1");
        when(vendor.getAddress()).thenReturn("Test Address 1");
        when(vendor.getContactPersonName()).thenReturn("Test Person 1");
        when(vendor.getContactPersonEmail()).thenReturn("person1@vendor1.com");
        when(vendor.getContactPersonPhone()).thenReturn(9876543210L);
        when(vendor.getId()).thenReturn(1);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.update(vendor);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(vendor, times(1)).getName();
        verify(vendor, times(1)).getAddress();
        verify(vendor, times(1)).getContactPersonName();
        verify(vendor, times(1)).getContactPersonEmail();
        verify(vendor, times(1)).getContactPersonPhone();
        verify(vendor, times(1)).getId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.delete(1);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }

}
