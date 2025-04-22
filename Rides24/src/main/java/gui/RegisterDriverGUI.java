package gui;

import javax.swing.*;

import domain.Driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDriverGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JTextField nameField;
    private JButton registerButton;

    public RegisterDriverGUI() {
        setTitle("Register as Driver");
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
        registerButton.addActionListener(e -> registerDriver());
        add(registerButton);
    }

    private void registerDriver() {
        String email = emailField.getText();
        String name = nameField.getText();

        if (email.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Driver driver = new Driver(email, name);
        JOptionPane.showMessageDialog(this, "Driver registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose(); // Close the window after registration
    }
}

