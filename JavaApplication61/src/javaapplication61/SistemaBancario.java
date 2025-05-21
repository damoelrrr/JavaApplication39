
package javaapplication61;

import java.util.HashMap;
import java.util.Map;

public class SistemaBancario {
    private Map<String, Persona> usuarios;

    public SistemaBancario() {
        usuarios = new HashMap<>();
    }

    public boolean registrarUsuario(String nombre, String usuario, String contrasena, String telefono) {
        if (usuarios.containsKey(usuario)) {
            return false; 
        }
        Persona nuevaPersona = new Persona(nombre, usuario, contrasena, telefono);
        usuarios.put(usuario, nuevaPersona);
        return true;
    }

    public boolean validarLogin(String usuario, String contrasena) {
        if (!usuarios.containsKey(usuario)) return false;
        return usuarios.get(usuario).getContrasena().equals(contrasena);
    }

    public Persona obtenerUsuario(String usuario) {
        return usuarios.get(usuario);
    }

    public void depositar(String usuario, double monto) {
        Persona p = usuarios.get(usuario);
        if (p != null) {
            p.getCuenta().depositar(monto);
        }
    }
    public boolean retirar(String usuario, double monto) {
    Persona p = obtenerUsuario(usuario);
    if (p != null && monto > 0) {
        return p.getCuenta().retirar(monto);
    }
    return false;
}


    public boolean transferir(String origenUsuario, Persona destino, double monto) {
        Persona origen = usuarios.get(origenUsuario);
        if (origen != null && destino != null) {
            return origen.getCuenta().transferir(destino.getCuenta(), monto);
        }
        return false;
    }

    public void solicitarPrestamo(String usuario, double monto) {
        Persona p = usuarios.get(usuario);
        if (p != null) {
            p.getCuenta().solicitarPrestamo(monto);
        }
    }

    public void pagarPrestamo(String usuario, int index, double cantidad) {
        Persona p = usuarios.get(usuario);
        if (p != null) {
            p.getCuenta().pagarPrestamo(index, cantidad, cantidad);
        }
    }

    public boolean cambiarContrasena(String usuario, String actual, String nueva) {
        Persona p = usuarios.get(usuario);
        if (p != null) {
            String contraActual = p.getContrasena();
            if (contraActual.equals(actual) && nueva != null && nueva.length() >= 4) {
                p.cambiarContrasena(actual, nueva);
                return true;
            }
        }
        return false;
    }
}
