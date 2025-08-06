package controller;

import model.Expense;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private int userId;

    public ExpenseManager(int userId) {
        this.userId = userId;
    }

    public boolean addExpense(String date, String description, String category, double amount, String remarks) {
        String sql = "INSERT INTO expense (user_id, date, description, category, amount, remarks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, date);
            stmt.setString(3, description);
            stmt.setString(4, category);
            stmt.setDouble(5, amount);
            stmt.setString(6, remarks);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExpense(int id, String date, String description, String category, double amount, String remarks) {
        String sql = "UPDATE expense SET date=?, description=?, category=?, amount=?, remarks=? WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            stmt.setString(2, description);
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

    public boolean deleteExpense(int id) {
        String sql = "DELETE FROM expense WHERE id=? AND user_id=?";
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

    public List<Expense> getAllExpensesWithId() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
    Expense e = new Expense();
    e.setId(rs.getInt("id"));
    e.setDate(rs.getDate("date").toLocalDate()); // ✅ Fix
    e.setDescription(rs.getString("description"));
    e.setCategory(rs.getString("category"));
    e.setAmount(rs.getDouble("amount"));
    e.setRemarks(rs.getString("remarks"));
    list.add(e);
}

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalExpense() {
        String sql = "SELECT SUM(amount) FROM expense WHERE user_id=?";
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
