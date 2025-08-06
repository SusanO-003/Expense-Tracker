package view;

import controller.ExpenseManager;
import controller.IncomeManager;
import controller.UserManager;
import model.User;
import util.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class Dashboard extends JFrame {
    private final int userId;
    private final String username;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private JLabel incomeLabel, expenseLabel, balanceLabel;
    private JPanel overviewPanel;
    private boolean darkTheme = false;

    public Dashboard(String username, int userId) {
        this.username = username;
        this.userId = userId;
        setTitle("Expense Tracker - Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createOverviewPanel();

        ReportPanel reportPanel = new ReportPanel(userId);
        TrendPanel trendPanel = new TrendPanel(userId);
        RecommendationPanel recommendationPanel = new RecommendationPanel(userId);
        ManageExpensePanel manageExpensePanel = new ManageExpensePanel(String.valueOf(userId)); // Optional

        mainPanel.add(overviewPanel, "Overview");
        mainPanel.add(reportPanel, "Report");
        mainPanel.add(trendPanel, "Trends");
        mainPanel.add(recommendationPanel, "Recommendation");
        mainPanel.add(manageExpensePanel, "ManageExpenses"); // Optional

        JPanel sidePanel = new JPanel(new GridLayout(0, 1));
        JButton overviewBtn = new JButton("Overview");
        JButton reportBtn = new JButton("Report");
        JButton trendBtn = new JButton("Trends");
        JButton recommendationBtn = new JButton("Recommendation");
        JButton manageBtn = new JButton("Manage Entries");
        JButton addIncomeBtn = new JButton("Add Income");
        JButton addExpenseBtn = new JButton("Add Expense");

        sidePanel.add(overviewBtn);
        sidePanel.add(reportBtn);
        sidePanel.add(trendBtn);
        sidePanel.add(recommendationBtn);
        sidePanel.add(manageBtn);
        sidePanel.add(addIncomeBtn);
        sidePanel.add(addExpenseBtn);

        overviewBtn.addActionListener(e -> cardLayout.show(mainPanel, "Overview"));
        reportBtn.addActionListener(e -> cardLayout.show(mainPanel, "Report"));
        trendBtn.addActionListener(e -> cardLayout.show(mainPanel, "Trends"));
        recommendationBtn.addActionListener(e -> cardLayout.show(mainPanel, "Recommendation"));

        manageBtn.addActionListener(e -> new ManageEntriesFrame(userId, this::refreshSummary,this));

        addIncomeBtn.addActionListener(e -> {
            new AddIncomeDialog(this, userId);
            refreshSummary();
        });

        addExpenseBtn.addActionListener(e -> {
            new AddExpenseDialog(this, userId);
            refreshSummary();
        });

        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        refreshSummary();
        setVisible(true);
    }

    private void createOverviewPanel() {
        overviewPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        incomeLabel = new JLabel();
        expenseLabel = new JLabel();
        balanceLabel = new JLabel();

        incomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        expenseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        centerPanel.add(incomeLabel);
        centerPanel.add(expenseLabel);
        centerPanel.add(balanceLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JButton toggleThemeBtn = new JButton("Toggle Theme");
        JButton switchUserBtn = new JButton("Switch User");
        JButton logoutBtn = new JButton("Logout");

        toggleThemeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchUserBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        toggleThemeBtn.addActionListener(e -> {
           ThemeManager.applyTheme(this,!ThemeManager.isDarkMode());
        });

        switchUserBtn.addActionListener(this::switchUser);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        rightPanel.add(toggleThemeBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(switchUserBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(logoutBtn);

        overviewPanel.add(centerPanel, BorderLayout.CENTER);
        overviewPanel.add(rightPanel, BorderLayout.EAST);
    }

    private void refreshSummary() {
        IncomeManager incomeManager = new IncomeManager(userId);
        ExpenseManager expenseManager = new ExpenseManager(userId);

        double totalIncome = incomeManager.getTotalIncome();
        double totalExpense = expenseManager.getTotalExpense();
        double net = totalIncome - totalExpense;

        incomeLabel.setText("Total Income: Nrs " + String.format("%.2f", totalIncome));
        expenseLabel.setText("Total Expense: Nrs " + String.format("%.2f", totalExpense));
        balanceLabel.setText("Balance: Nrs " + String.format("%.2f", net));
    }

    private void switchUser(ActionEvent e) {
        UserManager userManager = new UserManager();
        List<User> users = userManager.getTopUsers(3);
        JPopupMenu popup = new JPopupMenu();
        for (User user : users) {
            JMenuItem item = new JMenuItem(user.getUsername());
            item.addActionListener(ae -> {
                dispose();
                new Dashboard(user.getUsername(), user.getId());
            });
            popup.add(item);
        }
        popup.show((Component) e.getSource(), 0, ((Component) e.getSource()).getHeight());
    }
}
