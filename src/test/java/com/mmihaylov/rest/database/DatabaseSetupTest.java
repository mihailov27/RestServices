package com.mmihaylov.rest.database;

import com.mmihaylov.rest.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseSetupTest extends BaseTest {

    static final String CREATE_TABLE = "create table users (name varchar(45),email varchar(45),phone varchar(45))";
    static final String DROP_TABLE = "drop table users";

    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:1234/NEWS_DB", "SA", "");
        Assert.assertTrue(!connection.isClosed());
        connection.createStatement().executeUpdate(CREATE_TABLE);
        connection.createStatement().executeUpdate(DROP_TABLE);
        connection.commit();
        connection.close();
    }
}
