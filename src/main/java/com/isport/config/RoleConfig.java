package com.isport.config;

import com.isport.enums.UserRole;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.isport.constant.ISportConstants.INSERT_ROLE;
import static com.isport.constant.ISportConstants.SELECT_ALL_ROLES;

@Getter
@Configuration
public class RoleConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public int createRoleIfNotExist() {
        try (Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword())) {
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_ROLES);
            if (!resultSet.next()) {
                insertRole(connection, UserRole.ADMIN.getRole());
                insertRole(connection, UserRole.USER.getRole());
            }
            return 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private void insertRole(Connection connection, String role) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_ROLE)) {
            ps.setString(1, role);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
