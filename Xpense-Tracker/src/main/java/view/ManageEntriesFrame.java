package view;

import controller.ExpenseManager;
import controller.IncomeManager;
import model.Expense;
import model.Income;
import util.ThemeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageEntriesFrame extends JFrame {
    private final int userId;
    private final Runnable onCloseCallback;

    public ManageEntriesFrame(int userId, Runnable onCloseCallback, JFrame parent) {
        this.userId = userId;
        this.onCloseCallback = onCloseCallback;
        setTitle("Manage Entries");
        setSize(800, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Income", createIncomePanel());
        tabs.addTab("Expense", createExpensePanel());

        add(tabs);
        ThemeManager.applyTheme(this, ThemeManager.isDarkMode());
        setVisible(true);
    }

    private JPanel createIncomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Date", "Source", "Category", "Amount", "Remarks"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        List<Income> incomes = new IncomeManager(userId).getAllIncomesWithId();
        for (Income income : incomes) {
            model.addRow(new Object[]{
                    income.getDate(), income.getSource(), income.getCategory(),
                    income.getAmount(), income.getRemarks()
            });
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createExpensePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Date", "Description", "Category", "Amount", "Remarks"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);

        List<Expense> expenses = new ExpenseManager(userId).getAllExpensesWithId();
        for (Expense expense : expenses) {
            model.addRow(new Object[]{
                    expense.getDate(), expense.getDescription(), expense.getCategory(),
                    expense.getAmount(), expense.getRemarks()
            });
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (onCloseCallback != null) onCloseCallback.run();
    }
}
