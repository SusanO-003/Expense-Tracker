package chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportChartGenerator {

    public static JPanel generateIncomeCategoryChart(int userId, String start, String end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT category, SUM(amount) as total FROM income WHERE user_id = ? AND date BETWEEN ? AND ? GROUP BY category";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, start);
            ps.setString(3, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("total"), "Income", rs.getString("category"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Income by Category", "Category", "Amount", dataset
        );

        // Set income bar color to green
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0,128,0)); // Dark green

        return new ChartPanel(chart);
    }

    public static JPanel generateExpenseCategoryChart(int userId, String start, String end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT category, SUM(amount) as total FROM expense WHERE user_id = ? AND date BETWEEN ? AND ? GROUP BY category";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, start);
            ps.setString(3, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("total"), "Expense", rs.getString("category"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Expense by Category", "Category", "Amount", dataset
        );

        // Set expense bar color to red
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(220, 20, 60)); // Crimson red

        return new ChartPanel(chart);
    }

    public static JPanel generateIncomeTrendChart(int userId, String start, String end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT date, SUM(amount) as total FROM income WHERE user_id = ? AND date BETWEEN ? AND ? GROUP BY date ORDER BY date";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, start);
            ps.setString(3, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("total"), "Income", rs.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Income Trend", "Date", "Amount", dataset
        );
        return new ChartPanel(chart);
    }

    public static JPanel generateExpenseTrendChart(int userId, String start, String end) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT date, SUM(amount) as total FROM expense WHERE user_id = ? AND date BETWEEN ? AND ? GROUP BY date ORDER BY date";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, start);
            ps.setString(3, end);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getDouble("total"), "Expense", rs.getString("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Expense Trend", "Date", "Amount", dataset
        );
        return new ChartPanel(chart);
    }
}
