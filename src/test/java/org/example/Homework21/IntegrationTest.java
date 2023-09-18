package org.example.Homework21;

import db.DbManeger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IntegrationTest {

    static Connection connection;

    @Test
    public void connectionTest (){
        DbManeger dbManeger = new DbManeger();
        connection = dbManeger.connectionToDb();
        executeSearchAndCompare(4775);
        dbManeger.close(connection);

    }

    public void executeSearchAndCompare(int orderId) {


        String sql = String.format("select * from orders where id = %d ;", orderId);

        System.out.println();

        try {
            System.out.println("Executing sql ... ");
            System.out.println("SQL is : " + sql);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery( sql );

            String statusFromDb = null;
            if (resultSet != null) {

                while ( resultSet.next() ) {
                    System.out.println(resultSet.getString(1) + resultSet.getString(2) + resultSet.getString(3));
                    statusFromDb = resultSet.getString(3);
                }

                Assertions.assertEquals( "OPEN", statusFromDb);

            } else {
                Assertions.fail("Result set is null");
            }

        } catch (SQLException e) {

            System.out.println("Error while executing sql ");
            System.out.println(e.getErrorCode());
            System.out.println(e.getSQLState());
            e.printStackTrace();

            Assertions.fail("SQLException");

        }

    }
}

