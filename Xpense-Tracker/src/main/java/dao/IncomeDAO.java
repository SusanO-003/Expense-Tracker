package dao;

import model.Income;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAO {

    public boolean addIncome(Income income) {
        String sql = "INSERT INTO income (user_id, date, source, category, amount, remarks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, income.getUserId());
            stmt.setDate(2, Date.valueOf(income.getDate()));
            stmt.setString(3, income.getSource());
            stmt.setString(4, income.getCategory());
            stmt.setDouble(5, income.getAmount());
            stmt.setString(6, income.getRemarks());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIncome(Income income) {
        String sql = "UPDATE income SET date=?, source=?, category=?, amount=?, remarks=? WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(income.getDate()));
            stmt.setString(2, income.getSource());
            stmt.setString(3, income.getCategory());
            stmt.setDouble(4, income.getAmount());
            stmt.setString(5, income.getRemarks());
            stmt.setInt(6, income.getId());
            stmt.setInt(7, income.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIncome(int id, int userId) {
        String sql = "DELETE FROM income WHERE id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Income> getAllIncome(int userId) {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE user_id=? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Income income = new Income(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("source"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("remarks")
                );
                incomes.add(income);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incomes;
    }

    public List<Income> getIncomeByDateRange(int userId, LocalDate start, LocalDate end) {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE user_id=? AND date BETWEEN ? AND ? ORDER BY date DESC";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, Date.valueOf(start));
            stmt.setDate(3, Date.valueOf(end));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Income income = new Income(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("source"),
                        rs.getString("category"),
                        rs.getDouble("amount"),
                        rs.getString("remarks")
                );
                incomes.add(income);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incomes;
    }
}
