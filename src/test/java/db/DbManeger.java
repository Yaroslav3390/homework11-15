package db;
import org.example.SetupFunctions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbManeger {

        public Connection connectionToDb() {

            SetupFunctions setupFunctions = new SetupFunctions();
            String host = setupFunctions.getDbhost();
            String port = setupFunctions.getDbport();
            String dbname = setupFunctions.getDbname();
            String dbusername = setupFunctions.getDbusername();
            String dbpassword = setupFunctions.getDbpassword();

            String connectionString = host + ":" + port + "/" + dbname;
            System.out.println(connectionString);
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Can't load class for db driver");
            }

            Connection connection;

            System.out.println("Trying to connect db");

            try {
                connection = DriverManager.getConnection(connectionString, dbusername, dbpassword);
                System.out.println("Connected to the PostgreSQL server successfully");
                return connection;
            } catch (SQLException e) {
                System.out.println("Can't establish connection...");
                System.out.println(e.getMessage());
                return null;
            }
        }

        public void close (Connection connection) {

            if (connection != null) {
                try {
                    System.out.println("Trying to close connection");
                    connection.close();
                    System.out.println("Closed successfully");
                } catch (SQLException e){
                    System.out.println("error with while closing connection:" + e);
                }
            } else {
                System.out.println("Connection does not exist");
            }
        }
    }

