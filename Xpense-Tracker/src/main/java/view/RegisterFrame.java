package view;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterFrame() {
        setTitle("Expense Tracker - Register");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Register", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        form.add(new JLabel("Username:"));
        form.add(usernameField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton registerBtn = new JButton("Register");
        buttons.add(registerBtn);
        add(buttons, BorderLayout.SOUTH);

        registerBtn.addActionListener((ActionEvent e) -> {
            UserDAO dao = new UserDAO();
            boolean success = dao.register(usernameField.getText(), new String(passwordField.getPassword()));
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                this.dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}

