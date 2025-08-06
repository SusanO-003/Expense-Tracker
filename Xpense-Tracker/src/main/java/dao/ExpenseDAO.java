package dao;

import model.Expense;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) {
        String sql = "INSERT INTO expense (user_id, date, description, category, amount, remarks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, expense.getUserId());
            stmt.setDate(2, Date.valueOf(expense.getDate()));
            stmt.setString(3, expense.getDescription());
            stmt.setString(4, expense.getCategory());
            stmt.setDouble(5, expense.getAmount());
            stmt.setString(6, expense.getRemarks());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expense SET date=?, description=?, category=?, amount=?, remarks=? WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(expense.getDate()));
            stmt.setString(2, expense.getDescription());
            stmt.setString(3, expense.getCategory());
            stmt.setDouble(4, expense.getAmount());
            stmt.setString(5, expense.getRemarks());
            stmt.setInt(6, expense.getId());
            stmt.setInt(7, expense.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpense(int id, int userId) {
        String sql = "DELETE FROM expense WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id=? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("remarks")
                );
                expenses.add(expense);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public List<Expense> getExpensesByDateRange(int userId, LocalDate start, LocalDate end) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expense WHERE user_id=? AND date BETWEEN ? AND ? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(start));
            stmt.setDate(3, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("remarks")
                );
                expenses.add(expense);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenses;
    }
}

