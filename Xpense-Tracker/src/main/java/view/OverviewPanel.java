package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OverviewPanel extends JPanel {
    private final int userId;
    private final JFrame parentFrame;
    private boolean isDarkTheme = false;

    public OverviewPanel(JFrame parentFrame, int userId, double totalIncome, double totalExpense) {
        this.userId = userId;
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Main display panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel incomeLabel = new JLabel("Total Income: Nrs " + String.format("%.2f", totalIncome));
        JLabel expenseLabel = new JLabel("Total Expense: Nrs " + String.format("%.2f", totalExpense));
        JLabel balanceLabel = new JLabel("Balance: Nrs " + String.format("%.2f", totalIncome - totalExpense));

        incomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        expenseLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));

        centerPanel.add(incomeLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(expenseLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(balanceLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Right-side control panel
        JPanel rightPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setPreferredSize(new Dimension(200, 180));

        JButton toggleThemeBtn = new JButton("Toggle-theme");
        JButton switchUserBtn = new JButton("switch user");
        JButton logoutBtn = new JButton("logout");

        toggleThemeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        switchUserBtn.setFont(new Font("Arial", Font.BOLD, 14));
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

        toggleThemeBtn.addActionListener(e -> toggleTheme());
        switchUserBtn.addActionListener(e -> switchUser());
        logoutBtn.addActionListener(e -> logout());

        rightPanel.add(toggleThemeBtn);
        rightPanel.add(switchUserBtn);
        rightPanel.add(logoutBtn);
        add(rightPanel, BorderLayout.EAST);
    }

    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        Color bg = isDarkTheme ? Color.DARK_GRAY : Color.WHITE;
        Color fg = isDarkTheme ? Color.WHITE : Color.BLACK;
        UIManager.put("Panel.background", bg);
        UIManager.put("Label.foreground", fg);
        UIManager.put("Button.background", isDarkTheme ? Color.GRAY : Color.LIGHT_GRAY);
        UIManager.put("Button.foreground", fg);
        SwingUtilities.updateComponentTreeUI(parentFrame);
    }

    private void switchUser() {
        String[] users = {"user1", "user2", "user3"};
        String selected = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Switch to user:",
                "Switch User",
                JOptionPane.PLAIN_MESSAGE,
                null,
                users,
                users[0]
        );

        if (selected != null) {
            parentFrame.dispose();
            new LoginFrame(); // Reopen login frame
        }
    }

    private void logout() {
        parentFrame.dispose();
        new LoginFrame();
    }
}
