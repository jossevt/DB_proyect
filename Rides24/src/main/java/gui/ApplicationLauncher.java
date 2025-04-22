package gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Driver;
import domain.Client;
import dataAccess.DataAccess;

public class ApplicationLauncher {

    public static void main(String[] args) {
        try {
            // Configuración de la aplicación
            ConfigXML c = ConfigXML.getInstance();
            System.out.println(c.getLocale());
            Locale.setDefault(new Locale(c.getLocale()));
            System.out.println("Locale: " + Locale.getDefault());

            // Simulación de un usuario (conductor y cliente)
            Driver driver = new Driver("driver3@gmail.com", "Test Driver");
            Client client = new Client(null, null);

            // Mostrar la interfaz principal (MainGUI)
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);

            // Lógica de negocio remota o local
            BLFacade appFacadeInterface;
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            if (c.isBusinessLogicLocal()) {
                // Lógica de negocio local
                DataAccess da = new DataAccess();
                appFacadeInterface = new BLFacadeImplementation(da);
            } else {
                // Lógica de negocio remota
                String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/" + c.getBusinessLogicName() + "?wsdl";
                URL url = new URL(serviceName);

                QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
                Service service = Service.create(url, qname);

                appFacadeInterface = service.getPort(BLFacade.class);
            }

            // Establecer la lógica de negocio para la aplicación
            BLFacade.setBusinessLogic(appFacadeInterface);

        } catch (Exception e) {
            // Manejo de excepciones y mostrar mensaje de error
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

