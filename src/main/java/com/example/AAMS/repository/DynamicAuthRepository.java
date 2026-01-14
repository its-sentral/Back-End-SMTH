package com.example.AAMS.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DynamicAuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${app.table.users}")
    private String userTable;

    @Value("${app.table.tokens}")
    private String tokenTable;

    @Value("${app.table.auth}")
    private String authTable;

    public void saveUser(String username, String hashedPassword) {
        String sql = String.format("INSERT INTO %s (username, password) VALUES (?, ?)", userTable);
        jdbcTemplate.update(sql, username, hashedPassword);
    }

    public String getPassword(String username) {
        String sql = String.format("SELECT password FROM %s WHERE username = ?", userTable);
        return jdbcTemplate.queryForObject(sql, String.class, username);
    }

    public void saveToken(String username, String token) {
        String sql = String.format("INSERT INTO %s (username, token_value) VALUES (?, ?)", tokenTable);
        jdbcTemplate.update(sql, username, token);
    }

    public Integer getAuthId(String username) {
        String sql = String.format("SELECT auth_id FROM %s WHERE username = ?", authTable);
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }
}