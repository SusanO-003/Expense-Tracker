package view;

import controller.IncomeManager;
import model.Income;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddIncomeDialog extends JDialog {
    private final int userId;

    public AddIncomeDialog(JFrame parent, int userId) {
        super(parent, "Add Income", true);
        this.userId = userId;

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField sourceField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextArea remarksArea = new JTextArea();

        formPanel.add(new JLabel("Source:"));
        formPanel.add(sourceField);
        formPanel.add(new JLabel("Amount:"));
        formPanel.add(amountField);
        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);
        formPanel.add(new JLabel("Remarks:"));
        formPanel.add(remarksArea);

        JButton submitButton = new JButton("Add Income");
        submitButton.addActionListener(e -> {
            String source = sourceField.getText().trim();
            String amountStr = amountField.getText().trim();
            String category = categoryField.getText().trim();
            String remarks = remarksArea.getText().trim();

            if (source.isEmpty() || amountStr.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                Income income = new Income();
                income.setUserId(userId);
                income.setDate(LocalDate.now());
                income.setSource(source);
                income.setCategory(category);
                income.setAmount(amount);
                income.setRemarks(remarks);

               boolean success = new IncomeManager(userId).addIncome(
    income.getDate().toString(),
    income.getSource(),
    income.getCategory(),
    income.getAmount(),
    income.getRemarks()
);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Income added successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add income.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
