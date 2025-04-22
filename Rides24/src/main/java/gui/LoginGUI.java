package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Driver;
import domain.Client;

public class LoginGUI extends JFrame {
    private JTextField emailField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> loginUser());
        add(loginButton);
    }

    private void loginUser() {
        String email = emailField.getText();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulación de autenticación (mejorable con BD en el futuro)
        if (email.contains("driver")) {
            Driver driver = new Driver(email, "Test Driver");
            JOptionPane.showMessageDialog(this, "Welcome, " + driver.getName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (email.contains("client")) {
            Client client = new Client(email, "Test Client");
            JOptionPane.showMessageDialog(this, "Welcome, " + client.getName() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

