package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    public static Connection connection = null;
    public static Connection getConnection(){
        Properties properties = loadProperties();
        String url = properties.getProperty("dburl");
        if (connection == null){
            try {
                connection = DriverManager.getConnection(url,properties);
            }catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("src/db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        }catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }

    public static void closeConnectino(){
        try {
            connection.close();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement statement){
        try {
            statement.close();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet resultSet){
        try {
            resultSet.close();
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }
}
