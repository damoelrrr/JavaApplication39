
package javaapplication61;

import java.util.*;

public class Grafo {
    private final Map<String, List<String>> conexiones = new HashMap<>();

    
    public void agregarConexion(String origen, String destino) {
        if (origen == null || destino == null || origen.equals(destino)) return;

        conexiones.computeIfAbsent(origen, k -> new ArrayList<>());
        conexiones.computeIfAbsent(destino, k -> new ArrayList<>());

        if (!conexiones.get(origen).contains(destino)) {
            conexiones.get(origen).add(destino);
        }
        if (!conexiones.get(destino).contains(origen)) {
            conexiones.get(destino).add(origen);
        }
    }

    
    public List<String> obtenerConexiones(String usuario) {
        return conexiones.getOrDefault(usuario, new ArrayList<>());
    }

    
    public boolean estanConectados(String usuario1, String usuario2) {
        List<String> conexionesUsuario1 = conexiones.get(usuario1);
        return conexionesUsuario1 != null && conexionesUsuario1.contains(usuario2);
    }

   
    public Map<String, List<String>> obtenerMapa() {
        return conexiones;
    }
}
