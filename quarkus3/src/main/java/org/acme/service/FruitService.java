package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.FruitDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FruitService {

    @Inject
    DataSource dataSource;

    public List<FruitDTO> getAllFruits() {
        String sql = "SELECT id, name, description FROM fruits ORDER BY id";
        List<FruitDTO> fruits = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                fruits.add(new FruitDTO(id, name, description, null));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error executing JDBC query for fruits", e);
        }

        return fruits;
    }


}
