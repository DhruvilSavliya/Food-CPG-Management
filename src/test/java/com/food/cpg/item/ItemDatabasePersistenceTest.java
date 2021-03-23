package com.food.cpg.item;

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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemDatabasePersistenceTest {

    private static final String TEST_ITEM_NAME = "Test item";
    private static final Double TEST_ITEM_COOKING_COST = 3.0;
    private static final Double TEST_ITEM_TOTAL_COST = 10.0;
    private static final Integer TEST_ITEM_MANUFACTURER_ID = 1;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    Item item;

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

        ItemDatabasePersistence itemDatabasePersistence = new ItemDatabasePersistence(commonDatabaseOperation);

        itemDatabasePersistence.getAll(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(4)).getDouble(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(item.getId()).thenReturn(1);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(item.getId());

        ItemDatabasePersistence itemDatabasePersistence = new ItemDatabasePersistence(commonDatabaseOperation);

        itemDatabasePersistence.load(item);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(1)).getString(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
    }


    @Test
    public void saveTest() throws SQLException {
        when(item.getName()).thenReturn(TEST_ITEM_NAME);
        when(item.getCookingCost()).thenReturn(TEST_ITEM_COOKING_COST);
        when(item.getTotalCost()).thenReturn(TEST_ITEM_TOTAL_COST);
        when(item.getManufacturerId()).thenReturn(TEST_ITEM_MANUFACTURER_ID);

        ItemDatabasePersistence itemDatabasePersistence = new ItemDatabasePersistence(commonDatabaseOperation);

        itemDatabasePersistence.save(item);

        verify(commonDatabaseOperation, times(1)).executeUpdateGetId(anyString(), anyList());
        verify(item, times(1)).getName();
        verify(item, times(1)).getCookingCost();
        verify(item, times(1)).getTotalCost();
        verify(item, times(1)).getManufacturerId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        ItemDatabasePersistence itemDatabasePersistence = new ItemDatabasePersistence(commonDatabaseOperation);

        itemDatabasePersistence.delete(1);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
