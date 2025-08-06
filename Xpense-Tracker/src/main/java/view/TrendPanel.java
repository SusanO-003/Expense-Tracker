package view;

import chart.ReportChartGenerator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TrendPanel extends JPanel {
    private final int userId;
    private final JPanel chartPanel;

    public TrendPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout());
        JTextField startDateField = new JTextField(LocalDate.now().minusMonths(1).toString(), 10);
        JTextField endDateField = new JTextField(LocalDate.now().toString(), 10);
        JButton applyBtn = new JButton("Apply Filter");

        filterPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        filterPanel.add(startDateField);
        filterPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        filterPanel.add(endDateField);
        filterPanel.add(applyBtn);

        add(filterPanel, BorderLayout.NORTH);

        chartPanel = new JPanel(new GridLayout(2, 1));
        add(chartPanel, BorderLayout.CENTER);

        applyBtn.addActionListener(e -> {
            chartPanel.removeAll();

            // 🚨 These must be the trend methods, not category chart methods!
            chartPanel.add(ReportChartGenerator.generateIncomeTrendChart(
                userId, startDateField.getText(), endDateField.getText()));
            chartPanel.add(ReportChartGenerator.generateExpenseTrendChart(
                userId, startDateField.getText(), endDateField.getText()));

            chartPanel.revalidate();
            chartPanel.repaint();
        });

        applyBtn.doClick(); // Load initial charts
    }
}
