package view;

import controller.ExpenseManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddExpenseDialog extends JDialog {
    public AddExpenseDialog(JFrame parent, int userId) {
        super(parent, "Add Expense", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField dateField = new JTextField(LocalDate.now().toString());
        JTextField descriptionField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField amountField = new JTextField();

        JButton saveBtn = new JButton("Save");

        add(new JLabel("Date (YYYY-MM-DD):"));
        add(dateField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("Category:"));
        add(categoryField);
        add(new JLabel("Amount:"));
        add(amountField);
        add(new JLabel());
        add(saveBtn);

        saveBtn.addActionListener(e -> {
            String date = dateField.getText();
            String description = descriptionField.getText();
            String category = categoryField.getText();
            double amount;

            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
                return;
            }

            ExpenseManager manager = new ExpenseManager(userId);
            boolean success = manager.addExpense(date, description, category, amount, "");

            if (success) {
                JOptionPane.showMessageDialog(this, "Expense added successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add expense.");
            }
        });

        setVisible(true);
    }
}
