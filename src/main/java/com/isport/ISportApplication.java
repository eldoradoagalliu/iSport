package com.isport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class ISportApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(ISportApplication.class, args);

        //Add the dependencies of Java DB connector on the project structure!!!
        //DB properties
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        String url = "jdbc:mysql://localhost:3306/your-schema";
        String username = "";
        String password = "";
        Connection connection = DriverManager.getConnection(url, username, password);

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM `roles`");
        if (!resultSet.next()) {
            connection.createStatement().executeUpdate("INSERT INTO `isports_schema`.`roles` (`name`) VALUES ('ADMIN');");
            connection.createStatement().executeUpdate("INSERT INTO `isports_schema`.`roles` (`name`) VALUES ('USER');");
        }

        connection.close();
    }
}
