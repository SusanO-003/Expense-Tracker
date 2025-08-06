package view;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel()); // empty cell
        add(new JLabel()); // empty cell
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            UserDAO dao = new UserDAO();
            boolean success = dao.login(username, password);

            if (success) {
                int userId = dao.getUserId(username);
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.dispose();
                new Dashboard(username, userId);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerBtn.addActionListener((ActionEvent e) -> {
            this.dispose();
            new RegisterFrame();
        });

        setVisible(true);
    }
}
