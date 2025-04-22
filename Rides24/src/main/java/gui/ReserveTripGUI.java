package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReserveTripGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList<String> tripList;  // Lista para mostrar los viajes disponibles
    private JTextField textFieldDate;  // Campo de texto para la fecha de la reserva
    private JTextField textFieldTime;  // Campo de texto para la hora de la reserva
    private JButton btnReserve;  // Botón para realizar la reserva
    private String userEmail;  // Email del cliente que realiza la reserva
    
    public ReserveTripGUI() {
        setTitle("Reservar Viaje");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Título de bienvenida
        JLabel lblWelcome = new JLabel("Selecciona un Viaje para Reservar", JLabel.CENTER);
        add(lblWelcome, BorderLayout.NORTH);

        // Lista para mostrar los viajes disponibles
        tripList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(tripList);
        add(scrollPane, BorderLayout.CENTER);

        // Campos de texto para la fecha y hora de la reserva
        JPanel panelInputs = new JPanel();
        panelInputs.setLayout(new GridLayout(2, 2));
        
        panelInputs.add(new JLabel("Fecha:"));
        textFieldDate = new JTextField(15);
        panelInputs.add(textFieldDate);

        panelInputs.add(new JLabel("Hora:"));
        textFieldTime = new JTextField(15);
        panelInputs.add(textFieldTime);

        add(panelInputs, BorderLayout.SOUTH);

        // Botón para realizar la reserva
        btnReserve = new JButton("Reservar");
        btnReserve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveTrip();
            }
        });
        add(btnReserve, BorderLayout.SOUTH);

        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana

        // Cargar los viajes disponibles cuando se inicie la ventana
        loadTrips();
    }

    // Método para cargar los viajes disponibles desde la base de datos
    private void loadTrips() {
        List<String> trips = new ArrayList<>();
        try (Connection conn = getDatabaseConnection()) {
            String query = "SELECT trip_id, origin, destination, date, time FROM trips WHERE status='AVAILABLE'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String trip = "ID: " + rs.getInt("trip_id") + " | " + rs.getString("origin") + " -> " + rs.getString("destination") +
                        " | Fecha: " + rs.getString("date") + " | Hora: " + rs.getString("time");
                trips.add(trip);
            }

            // Cargar los viajes disponibles en el JList
            tripList.setListData(trips.toArray(new String[0]));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los viajes disponibles.");
        }
    }

    // Método para realizar la reserva de un viaje
    private void reserveTrip() {
        String selectedTrip = tripList.getSelectedValue();
        String date = textFieldDate.getText();
        String time = textFieldTime.getText();
        
        if (selectedTrip != null && !date.isEmpty() && !time.isEmpty()) {
            // Obtener los detalles del viaje seleccionado
            String[] tripDetails = selectedTrip.split(" \\| ");
            String tripId = tripDetails[0].split(": ")[1];

            try (Connection conn = getDatabaseConnection()) {
                // Verificar si el viaje aún está disponible
                String checkQuery = "SELECT status FROM trips WHERE trip_id=? AND status='AVAILABLE'";
                PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                checkStmt.setInt(1, Integer.parseInt(tripId));
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Realizar la reserva
                    String reserveQuery = "INSERT INTO reservations (user_email, trip_id, reservation_date, reservation_time, status) VALUES (?, ?, ?, ?, 'PENDING')";
                    PreparedStatement stmt = conn.prepareStatement(reserveQuery);
                    stmt.setString(1, userEmail);
                    stmt.setInt(2, Integer.parseInt(tripId));
                    stmt.setString(3, date);
                    stmt.setString(4, time);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Reserva realizada con éxito.");
                        dispose();  // Cerrar la ventana de reserva
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo realizar la reserva.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El viaje ya no está disponible.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la reserva.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona un viaje y completa todos los campos.");
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

    // Método para establecer el email del cliente que realiza la reserva
    public void setUserEmail(String email) {
        this.userEmail = email;
    }
}

