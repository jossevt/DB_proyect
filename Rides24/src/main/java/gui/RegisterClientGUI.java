package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import domain.Client;

public class RegisterClientGUI extends JFrame {
    private JTextField emailField;
    private JTextField nameField;
    private JButton registerButton;

    public RegisterClientGUI() {
        setTitle("Register as Client");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerClient());
        add(registerButton);
    }

    private void registerClient() {
        String email = emailField.getText();
        String name = nameField.getText();

        if (email.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client = new Client(email, name);
        JOptionPane.showMessageDialog(this, "Client registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Close the window after registration
    }
}
