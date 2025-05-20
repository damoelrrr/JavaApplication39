
package javaapplication61;

import java.util.Objects;

public class Persona {
    private String nombre;
    private String usuario;
    private String contrasena;
    private String telefono;
    private CuentaBancaria cuenta;

    public Persona(String nombre, String usuario, String contrasena, String telefono) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.cuenta = new CuentaBancaria();
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    public void cambiarContrasena(String actual, String nueva) {
        if (Objects.equals(this.contrasena, actual)) {
            this.contrasena = nueva;
        }
    }
}

