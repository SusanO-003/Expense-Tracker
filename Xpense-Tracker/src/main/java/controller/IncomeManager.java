package controller;

import model.Income;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeManager {
    private int userId;

    public IncomeManager(int userId) {
        this.userId = userId;
    }

 public boolean addIncome(String date, String source, String category, double amount, String remarks) {
    String sql = "INSERT INTO income (user_id, date, source, category, amount, remarks) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId);
        stmt.setString(2, date); // Format: yyyy-MM-dd
        stmt.setString(3, source);
        stmt.setString(4, category);
        stmt.setDouble(5, amount);
        stmt.setString(6, remarks);

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("💥 SQL Error: " + e.getMessage());
        e.printStackTrace(); // ✅ add this line to see the stack trace
        return false;
    }
}


    public boolean updateIncome(int id, String date, String source, String category, double amount, String remarks) {
        String sql = "UPDATE income SET date=?, source=?, category=?, amount=?, remarks=? WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            stmt.setString(2, source);
            stmt.setString(3, category);
            stmt.setDouble(4, amount);
            stmt.setString(5, remarks);
            stmt.setInt(6, id);
            stmt.setInt(7, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIncome(int id) {
        String sql = "DELETE FROM income WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Income> getAllIncomesWithId() {
        List<Income> list = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
           while (rs.next()) {
    Income i = new Income();
    i.setId(rs.getInt("id"));
    i.setDate(rs.getDate("date").toLocalDate()); // ✅ Fix
    i.setSource(rs.getString("source"));
    i.setCategory(rs.getString("category"));
    i.setAmount(rs.getDouble("amount"));
    i.setRemarks(rs.getString("remarks"));
    list.add(i);
}

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalIncome() {
        String sql = "SELECT SUM(amount) FROM income WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
