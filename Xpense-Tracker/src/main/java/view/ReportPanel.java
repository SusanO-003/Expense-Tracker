package view;

import chart.ReportChartGenerator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ReportPanel extends JPanel {
    private final int userId;
    private final JPanel chartPanel;

    public ReportPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // Top filter panel
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

        // Chart area
        chartPanel = new JPanel(new GridLayout(2, 1));
        add(chartPanel, BorderLayout.CENTER);

        applyBtn.addActionListener(e -> {
            chartPanel.removeAll();
            chartPanel.add(ReportChartGenerator.generateIncomeCategoryChart(
                userId, startDateField.getText(), endDateField.getText()));
            chartPanel.add(ReportChartGenerator.generateExpenseCategoryChart(
                userId, startDateField.getText(), endDateField.getText()));
            chartPanel.revalidate();
            chartPanel.repaint();
        });

        // Initial chart load
        applyBtn.doClick();
    }
}
