
package javaapplication61;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BancoGUI extends JFrame {
    private final SistemaBancario sistema;
    private String usuarioActual;

    
    private JTextField tfUsuario;
    private JPasswordField pfContrasena;
    private JButton btnLogin, btnRegistrar;

    
    private JLabel lblBienvenida, lblSaldo;
    private JTextArea taHistorial;
    private JButton btnDepositar, btnRetirar, btnTransferir, btnSolicitarPrestamo, btnPagarPrestamo, btnCambiarContrasena, btnCerrarSesion;

    public BancoGUI() {
        sistema = new SistemaBancario();
        setTitle("Sistema Bancario");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        iniciarPanelLogin();
    }

    private void iniciarPanelLogin() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblUsuario = new JLabel("Usuario:");
        tfUsuario = new JTextField(15);
        JLabel lblContrasena = new JLabel("Contraseña:");
        pfContrasena = new JPasswordField(15);
        btnLogin = new JButton("Ingresar");
        btnRegistrar = new JButton("Registrar");

        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0; gbc.gridy = 0; panel.add(lblUsuario, gbc);
        gbc.gridx = 1; panel.add(tfUsuario, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblContrasena, gbc);
        gbc.gridx = 1; panel.add(pfContrasena, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(btnLogin, gbc);
        gbc.gridx = 1; panel.add(btnRegistrar, gbc);

        btnLogin.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> registrar());

        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    private void iniciarPanelDashboard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        lblBienvenida = new JLabel("Bienvenido, " + usuarioActual, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBienvenida, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout());
        lblSaldo = new JLabel("Saldo: $" + sistema.obtenerUsuario(usuarioActual).getCuenta().getSaldo());
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelCentro.add(lblSaldo, BorderLayout.NORTH);

        taHistorial = new JTextArea();
        taHistorial.setEditable(false);
        actualizarHistorial();
        JScrollPane sp = new JScrollPane(taHistorial);
        panelCentro.add(sp, BorderLayout.CENTER);
        add(panelCentro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new GridLayout(4, 2, 5, 5));

        btnDepositar = new JButton("Depositar");
        btnRetirar = new JButton("Retirar");
        btnTransferir = new JButton("Transferir");
        btnSolicitarPrestamo = new JButton("Solicitar Préstamo");
        btnPagarPrestamo = new JButton("Pagar Préstamo");
        btnCambiarContrasena = new JButton("Cambiar Contraseña");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        btnDepositar.addActionListener(e -> depositar());
        btnRetirar.addActionListener(e -> retirar());
        btnTransferir.addActionListener(e -> transferir());
        btnSolicitarPrestamo.addActionListener(e -> solicitarPrestamo());
        btnPagarPrestamo.addActionListener(e -> pagarPrestamo());
        btnCambiarContrasena.addActionListener(e -> cambiarContrasena());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelSur.add(btnDepositar);
        panelSur.add(btnRetirar);
        panelSur.add(btnTransferir);
        panelSur.add(btnSolicitarPrestamo);
        panelSur.add(btnPagarPrestamo);
        panelSur.add(btnCambiarContrasena);
        panelSur.add(btnCerrarSesion);

        add(panelSur, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void login() {
        String usuario = tfUsuario.getText().trim();
        String contrasena = new String(pfContrasena.getPassword());
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (sistema.validarLogin(usuario, contrasena)) {
            usuarioActual = usuario;
            iniciarPanelDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrar() {
        JTextField tfNombre = new JTextField();
        JTextField tfUsuarioNuevo = new JTextField();
        JPasswordField pfContraNueva = new JPasswordField();
        JTextField tfTelefono = new JTextField();

        Object[] campos = {
            "Nombre:", tfNombre,
            "Usuario:", tfUsuarioNuevo,
            "Contraseña:", pfContraNueva,
            "Teléfono:", tfTelefono
        };

        int res = JOptionPane.showConfirmDialog(this, campos, "Registrar nuevo usuario", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String nombre = tfNombre.getText().trim();
            String usuario = tfUsuarioNuevo.getText().trim();
            String contrasena = new String(pfContraNueva.getPassword());
            String telefono = tfTelefono.getText().trim();

            if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (contrasena.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (sistema.registrarUsuario(nombre, usuario, contrasena, telefono)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void depositar() {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese monto a depositar:");
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) throw new NumberFormatException();
            sistema.depositar(usuarioActual, monto);
            JOptionPane.showMessageDialog(this, "Depósito realizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarHistorial();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retirar() {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese monto a retirar:");
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) throw new NumberFormatException();
            boolean exito = sistema.retirar(usuarioActual, monto);
            if (!exito) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente o monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Retiro realizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarHistorial();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void transferir() {
        String destino = JOptionPane.showInputDialog(this, "Ingrese usuario destino:");
        if (destino == null || destino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un usuario destino", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Persona pDestino = sistema.obtenerUsuario(destino.trim());
        if (pDestino == null) {
            JOptionPane.showMessageDialog(this, "Usuario destino no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String montoStr = JOptionPane.showInputDialog(this, "Ingrese monto a transferir:");
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) throw new NumberFormatException();
            boolean exito = sistema.transferir(usuarioActual, pDestino, monto);
            if (!exito) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente o monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Transferencia exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarHistorial();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solicitarPrestamo() {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese monto del préstamo:");
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) throw new NumberFormatException();
            sistema.solicitarPrestamo(usuarioActual, monto);
            JOptionPane.showMessageDialog(this, "Préstamo solicitado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarHistorial();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pagarPrestamo() {
        Persona p = sistema.obtenerUsuario(usuarioActual);
        CuentaBancaria cuenta = p.getCuenta();
        if (cuenta.getPrestamos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tiene préstamos pendientes", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder prestamosStr = new StringBuilder("Préstamos:\n");
        for (int i = 0; i < cuenta.getPrestamos().size(); i++) {
            Prestamo pr = cuenta.getPrestamos().get(i);
            prestamosStr.append(i).append(". Monto: $").append(pr.getMonto())
                    .append(", Pagado: $").append(pr.getPagado()).append("\n");
        }
        String indiceStr = JOptionPane.showInputDialog(this, prestamosStr + "Ingrese índice del préstamo a pagar:");
        try {
            int indice = Integer.parseInt(indiceStr);
            if (indice < 0 || indice >= cuenta.getPrestamos().size()) {
                JOptionPane.showMessageDialog(this, "Índice inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String cantidadStr = JOptionPane.showInputDialog(this, "Ingrese monto a pagar:");
            double cantidad = Double.parseDouble(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();

            sistema.pagarPrestamo(usuarioActual, indice, cantidad);
            JOptionPane.showMessageDialog(this, "Pago realizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarHistorial();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cambiarContrasena() {
        JPasswordField pfActual = new JPasswordField();
        JPasswordField pfNueva = new JPasswordField();
        Object[] campos = {
            "Contraseña actual:", pfActual,
            "Nueva contraseña:", pfNueva
        };
        int res = JOptionPane.showConfirmDialog(this, campos, "Cambiar contraseña", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String actual = new String(pfActual.getPassword());
            String nueva = new String(pfNueva.getPassword());
            if (nueva.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (sistema.cambiarContrasena(usuarioActual, actual, nueva)) {
                JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña actual incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cerrarSesion() {
        usuarioActual = null;
        iniciarPanelLogin();
    }

    private void actualizarHistorial() {
        Persona p = sistema.obtenerUsuario(usuarioActual);
        if (p == null) return;
        lblSaldo.setText("Saldo: $" + p.getCuenta().getSaldo());
        StringBuilder sb = new StringBuilder();
        for (String s : p.getCuenta().getHistorial()) {
            sb.append(s).append("\n");
        }
        taHistorial.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BancoGUI gui = new BancoGUI();
            gui.setVisible(true);
        });
    }
}

