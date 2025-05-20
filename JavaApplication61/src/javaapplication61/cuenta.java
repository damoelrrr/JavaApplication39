package javaapplication61;

import javax.swing.JOptionPane;

class cuenta {

    String usuarioRegistrado;
    String contrasenaRegistrada;
    String nombre;

    void registrarCuenta() {
        usuarioRegistrado = JOptionPane.showInputDialog("Ingrese un nombre de usuario:");
        contrasenaRegistrada = JOptionPane.showInputDialog("Ingrese una contraseña:");
        nombre = JOptionPane.showInputDialog("Ingrese su nombre completo:");
    }
}
