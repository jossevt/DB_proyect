package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CreateTripGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtOrigin, txtDestination, txtDate, txtTime;
    private JButton btnCreateTrip;
    private String driverEmail;  // Email del conductor que está creando el viaje

    public CreateTripGUI() {
        setTitle("Crear Viaje (Conductor)");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2));  // Disposición de los componentes en una cuadrícula

        // Etiquetas y campos de entrada
        add(new JLabel("Origen:"));
        txtOrigin = new JTextField();
        add(txtOrigin);

        add(new JLabel("Destino:"));
        txtDestination = new JTextField();
        add(txtDestination);

        add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtDate = new JTextField();
        add(txtDate);

        add(new JLabel("Hora (HH:MM):"));
        txtTime = new JTextField();
        add(txtTime);

        // Botón para crear el viaje
        btnCreateTrip = new JButton("Crear Viaje");
        btnCreateTrip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTrip();
            }
        });
        add(btnCreateTrip);

        // Configurar la operación de cierre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana
    }

    private void createTrip() {
        String origin = txtOrigin.getText();
        String destination = txtDestination.getText();
        String date = txtDate.getText();
        String time = txtTime.getText();

        if (origin.isEmpty() || destination.isEmpty() || date.isEmpty() || time.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        // Insertar el viaje en la base de datos
        try (Connection conn = getDatabaseConnection()) {
            String query = "INSERT INTO trips (driver_email, origin, destination, date, time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, driverEmail);  // Aquí debe ir el email del conductor
            stmt.setString(2, origin);
            stmt.setString(3, destination);
            stmt.setString(4, date);
            stmt.setString(5, time);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Viaje creado exitosamente.");
                dispose();  // Cerrar la ventana
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear el viaje.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear el viaje.");
        }
    }

    // Método para obtener la conexión a la base de datos
    private Connection getDatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver JDBC no encontrado.");
            return null;
        }
    }

    // Método para establecer el email del conductor
    public void setDriverEmail(String email) {
        this.driverEmail = email;
    }
}
