package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MainGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    // Simulación de una base de datos con un HashMap
    private Map<String, String> usersDatabase = new HashMap<>();

    public MainGUI() {
        setTitle("Login & Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnRegisterDriver = new JButton("Register as Driver");
        btnRegisterDriver.addActionListener(e -> openRegisterDriverGUI());
        add(btnRegisterDriver);

        JButton btnRegisterClient = new JButton("Register as Client");
        btnRegisterClient.addActionListener(e -> openRegisterClientGUI());
        add(btnRegisterClient);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> openLoginGUI());
        add(btnLogin);
    }

    private void openRegisterDriverGUI() {
        new RegisterDriverGUI().setVisible(true);
    }

    private void openRegisterClientGUI() {
        new RegisterClientGUI().setVisible(true);
    }

    private void openLoginGUI() {
        new LoginGUI().setVisible(true);
    }

    // Formulario para registro como Driver
    class RegisterDriverGUI extends JFrame {
        private JTextField nameField, emailField;
        private JButton submitButton;

        public RegisterDriverGUI() {
            setTitle("Register Driver");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 2, 10, 10));

            // Campos de texto para nombre y correo
            add(new JLabel("Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Email:"));
            emailField = new JTextField();
            add(emailField);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> registerDriver());
            add(submitButton);
        }

        private void registerDriver() {
            String name = nameField.getText();
            String email = emailField.getText();

            if (!name.isEmpty() && !email.isEmpty()) {
                // Guardar el Driver en la "base de datos"
                usersDatabase.put(email, name);
                JOptionPane.showMessageDialog(this, "Driver registered successfully!");
                dispose();  // Cerrar la ventana de registro
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    // Formulario para registro como Client
    class RegisterClientGUI extends JFrame {
        private JTextField nameField, emailField;
        private JButton submitButton;

        public RegisterClientGUI() {
            setTitle("Register Client");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 2, 10, 10));

            // Campos de texto para nombre y correo
            add(new JLabel("Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Email:"));
            emailField = new JTextField();
            add(emailField);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(e -> registerClient());
            add(submitButton);
        }

        private void registerClient() {
            String name = nameField.getText();
            String email = emailField.getText();

            if (!name.isEmpty() && !email.isEmpty()) {
                // Guardar el Client en la "base de datos"
                usersDatabase.put(email, name);
                JOptionPane.showMessageDialog(this, "Client registered successfully!");
                dispose();  // Cerrar la ventana de registro
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    // Formulario de Login
    class LoginGUI extends JFrame {
        private JTextField emailField, nameField;
        private JButton loginButton;

        public LoginGUI() {
            setTitle("Login");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(3, 2, 10, 10));

            // Campos de texto para correo y nombre
            add(new JLabel("Email:"));
            emailField = new JTextField();
            add(emailField);

            add(new JLabel("Name:"));
            nameField = new JTextField();
            add(nameField);

            loginButton = new JButton("Login");
            loginButton.addActionListener(e -> login());
            add(loginButton);
        }

        private void login() {
            String email = emailField.getText();
            String name = nameField.getText();

            if (!email.isEmpty() && !name.isEmpty()) {
                // Verificar si el usuario está en la base de datos
                if (usersDatabase.containsKey(email) && usersDatabase.get(email).equals(name)) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    dispose();  // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect email or name.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}

// @jve:decl-index=0:visual-constraint="0,0"

