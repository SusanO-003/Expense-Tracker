package view;

import dao.UserDAO;
import util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RecommendationPanel extends JPanel {
    public RecommendationPanel(int userId) {
        setLayout(new BorderLayout());
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        Map<String, Double> categorySpending = new HashMap<>();
        double totalExpense = 0;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT category, SUM(amount) as total FROM expense WHERE user_id = ? GROUP BY category";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                double amount = rs.getDouble("total");
                categorySpending.put(category, amount);
                totalExpense += amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder recommendations = new StringBuilder("📊 Expense Recommendations Based on Your Spending:\n\n");

        if (totalExpense == 0) {
            recommendations.append("No expenses found. Start logging your expenses to get insights.");
        } else {
            for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
                double percentage = (entry.getValue() / totalExpense) * 100;
                recommendations.append(String.format("• %s: %.2f%% of total spending", entry.getKey(), percentage));

                if (percentage > 30) {
                    recommendations.append("  🔺 Consider reducing spending in this category.\n");
                } else if (percentage < 10) {
                    recommendations.append("  ✅ This category seems well-managed.\n");
                } else {
                    recommendations.append("\n");
                }
            }
        }

        resultArea.setText(recommendations.toString());
    }
}
