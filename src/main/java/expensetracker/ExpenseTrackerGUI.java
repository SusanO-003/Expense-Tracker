package expensetracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class ExpenseTrackerGUI extends JFrame {
    private ExpenseTracker tracker;
    private JTextField categoryField, amountField, filterField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public ExpenseTrackerGUI() {
        tracker = new ExpenseTracker();
        createUI();
        loadTableData(tracker.getExpenses());
    }

    private void createUI() {
        setTitle("Advanced Expense Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel (inputs)
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel();
        categoryField = new JTextField(10);
        amountField = new JTextField(6);
        JButton addButton = new JButton("Add Expense");

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(addButton);

        JPanel filterPanel = new JPanel();
        filterField = new JTextField(10);
        JButton filterButton = new JButton("Filter");
        JButton showAllButton = new JButton("Show All");
        JButton deleteButton = new JButton("Delete Selected");

        filterPanel.add(new JLabel("Filter Category:"));
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(showAllButton);
        filterPanel.add(deleteButton);

        topPanel.add(inputPanel);
        topPanel.add(filterPanel);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Category", "Amount", "Date"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bottom (total)
        totalLabel = new JLabel("Total: Rs. 0.0");
        updateTotal();

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);

        // Button listeners
        addButton.addActionListener(e -> addExpense());
        filterButton.addActionListener(e -> {
            String cat = filterField.getText().trim();
            if (!cat.isEmpty()) {
                loadTableData(tracker.filterByCategory(cat));
            }
        });
        showAllButton.addActionListener(e -> loadTableData(tracker.getExpenses()));
        deleteButton.addActionListener(e -> deleteSelectedExpense());
    }

    private void addExpense() {
        String category = categoryField.getText().trim();
        String amountText = amountField.getText().trim();
        if (category.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            LocalDate date = LocalDate.now();
            Expense expense = new Expense(category, amount, date);
            tracker.addExpense(expense);
            categoryField.setText("");
            amountField.setText("");
            loadTableData(tracker.getExpenses());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void deleteSelectedExpense() {
        int row = table.getSelectedRow();
        if (row != -1) {
            tracker.deleteExpense(row);
            loadTableData(tracker.getExpenses());
        } else {
            JOptionPane.showMessageDialog(this, "Select an expense to delete.");
        }
    }

    private void loadTableData(List<Expense> expenses) {
        tableModel.setRowCount(0);
        for (Expense e : expenses) {
            tableModel.addRow(new Object[]{e.getCategory(), e.getAmount(), e.getDate()});
        }
        updateTotal();
    }

    private void updateTotal() {
        totalLabel.setText("Total: Rs. " + tracker.getTotalAmount());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTrackerGUI().setVisible(true));
    }
}

