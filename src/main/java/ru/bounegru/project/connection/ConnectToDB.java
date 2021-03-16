package ru.bounegru.project.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class ConnectToDB implements AutoCloseable{
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private Connection connection;

    public ConnectToDB() {

        // Check if we have driver
        // and then connection to DB
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
            Class.forName(properties.getProperty("driver"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            System.out.println("Connection OK");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    @Override
    public void close() throws Exception {
        connection.close();
    }
}
