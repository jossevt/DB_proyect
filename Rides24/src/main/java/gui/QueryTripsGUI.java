package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryTripsGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JList<String> tripList;  // Lista para mostrar los viajes disponibles
    private JButton btnRefresh;  // Botón para refrescar la lista de viajes
    private String userEmail;  // Email del usuario que consulta los viajes (cliente o conductor)

    public QueryTripsGUI() {
        setTitle("Consultar Viajes");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Título de bienvenida
        JLabel lblWelcome = new JLabel("Viajes Disponibles", JLabel.CENTER);
        add(lblWelcome, BorderLayout.NORTH);

        // Lista para mostrar los viajes disponibles
        tripList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(tripList);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para refrescar la lista de viajes
        btnRefresh = new JButton("Refrescar");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTrips();
            }
        });
        add(btnRefresh, BorderLayout.SOUTH);

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

    // Método para establecer el email del usuario (conductor o cliente)
    public void setUserEmail(String email) {
        this.userEmail = email;
    }
}
