package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.DBConnection;
import util.HashUtil;

public class UserDAO {
    public boolean register(String username, String password) {
        String hashed = HashUtil.hashPassword(password);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean login(String username, String password) {
        String hashed = HashUtil.hashPassword(password);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, hashed);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUserId(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT id FROM users WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<User> getTopUsers(int limit) {
    List<User> users = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT id, username FROM users LIMIT ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("username"));
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}
}
