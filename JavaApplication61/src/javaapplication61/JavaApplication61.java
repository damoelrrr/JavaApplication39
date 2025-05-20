package javaapplication61;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JavaApplication61 {

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Sistema Bancario");
        ventana.setSize(1200, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton botonEntrar = new JButton("Entrar");
        panelSuperior.add(botonEntrar, BorderLayout.WEST);

        JLabel titulo = new JLabel("Bienvenido al Sistema Bancario", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panelSuperior.add(titulo, BorderLayout.CENTER);

        ventana.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 5, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] opciones = {
            "Crear cuenta",
            "Consultar saldo",
            "Depositar",
            "Retirar",
            "Solicitar préstamo",
            "Guardar dinero",
            "Ver reporte financiero",
            "Salir"
        };

        for (String texto : opciones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.PLAIN, 16));
            if (texto.equals("Salir")) {
                boton.addActionListener(e -> System.exit(0));
            } else {
                boton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(ventana, "Función aún no implementada: " + texto);
                });
            }
            panelBotones.add(boton);
        }

        ventana.add(panelBotones, BorderLayout.CENTER);

        // Acción del botón "Entrar"
        botonEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame loginVentana = new JFrame("Iniciar sesión");
                loginVentana.setSize(400, 250);
                loginVentana.setLocationRelativeTo(null);
                loginVentana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginVentana.setLayout(new GridLayout(4, 2, 10, 10));
                loginVentana.setResizable(false);

                JLabel userLabel = new JLabel("Usuario:");
                JTextField userField = new JTextField();

                JLabel passLabel = new JLabel("Contraseña:");
                JPasswordField passField = new JPasswordField();

                JButton btnLogin = new JButton("Iniciar sesión");
                JButton btnCrearCuenta = new JButton("No tengo cuenta");

                btnLogin.addActionListener(ev -> {
                    String usuario = userField.getText();
                    String contrasena = new String(passField.getPassword());
                    // Aquí podrías validar usuario y contraseña
                    JOptionPane.showMessageDialog(loginVentana, "Intentando iniciar sesión con: " + usuario);
                });

                btnCrearCuenta.addActionListener(ev -> {
                    JOptionPane.showMessageDialog(loginVentana, "Función de creación de cuenta no implementada.");
                });

                loginVentana.add(userLabel);
                loginVentana.add(userField);
                loginVentana.add(passLabel);
                loginVentana.add(passField);
                loginVentana.add(new JLabel()); // Espacio vacío
                loginVentana.add(new JLabel()); // Espacio vacío
                loginVentana.add(btnLogin);
                loginVentana.add(btnCrearCuenta);

                loginVentana.setVisible(true);
            }
        });

        ventana.setVisible(true);
    }
}
