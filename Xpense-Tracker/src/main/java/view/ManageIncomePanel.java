package view;

import dao.UserDAO;
import util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageIncomePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private String username;

    public ManageIncomePanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Date", "Source", "Category", "Amount", "Remarks"}, 0);
        table = new JTable(model);
        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        buttons.add(editBtn);
        buttons.add(deleteBtn);
        add(buttons, BorderLayout.SOUTH);

        deleteBtn.addActionListener(e -> deleteSelectedRow());
        editBtn.addActionListener(e -> editSelectedRow());
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM incomes WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, new UserDAO().getUserId(username));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getDate("date"),
                    rs.getString("source"),
                    rs.getString("category"),
                    rs.getDouble("amount"),
                    rs.getString("remarks")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete selected income?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection()) {
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM incomes WHERE id = ?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    loadData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
        }
    }

    private void editSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String date = model.getValueAt(selectedRow, 1).toString();
            String source = model.getValueAt(selectedRow, 2).toString();
            String category = model.getValueAt(selectedRow, 3).toString();
            String amount = model.getValueAt(selectedRow, 4).toString();
            String remarks = model.getValueAt(selectedRow, 5).toString();

            JTextField dateField = new JTextField(date);
            JTextField sourceField = new JTextField(source);
            JTextField categoryField = new JTextField(category);
            JTextField amountField = new JTextField(amount);
            JTextField remarksField = new JTextField(remarks);

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Date:")); panel.add(dateField);
            panel.add(new JLabel("Source:")); panel.add(sourceField);
            panel.add(new JLabel("Category:")); panel.add(categoryField);
            panel.add(new JLabel("Amount:")); panel.add(amountField);
            panel.add(new JLabel("Remarks:")); panel.add(remarksField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Income", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try (Connection conn = DBConnection.getConnection()) {
                    String query = "UPDATE incomes SET date=?, source=?, category=?, amount=?, remarks=? WHERE id=?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, dateField.getText());
                    ps.setString(2, sourceField.getText());
                    ps.setString(3, categoryField.getText());
                    ps.setDouble(4, Double.parseDouble(amountField.getText()));
                    ps.setString(5, remarksField.getText());
                    ps.setInt(6, id);
                    ps.executeUpdate();
                    loadData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to update income.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        }
    }
}
