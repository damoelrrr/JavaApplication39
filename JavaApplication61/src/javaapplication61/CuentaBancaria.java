package javaapplication61;

import java.util.ArrayList;
import java.util.List;

public class CuentaBancaria {
    private double saldo;
    private final List<String> historial;
    private final List<Prestamo> prestamos;

    public CuentaBancaria() {
        this.saldo = 0;
        this.historial = new ArrayList<>();
        this.prestamos = new ArrayList<>();
    }

    public double getSaldo() {
        return saldo;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void depositar(double monto) {
        if (monto <= 0) return;
        saldo += monto;
        historial.add("Depósito: $" + monto);
    }

    public boolean retirar(double monto) {
        if (monto <= 0 || monto > saldo) return false;
        saldo -= monto;
        historial.add("Retiro: $" + monto);
        return true;
    }

    public boolean transferir(CuentaBancaria destino, double monto) {
        if (monto <= 0 || monto > saldo) return false;
        saldo -= monto;
        destino.saldo += monto;
        historial.add("Transferencia enviada: $" + monto);
        destino.historial.add("Transferencia recibida: $" + monto);
        return true;
    }

    public void solicitarPrestamo(double monto) {
        if (monto <= 0) return;
        Prestamo nuevo = new Prestamo(monto);
        prestamos.add(nuevo);
        saldo += monto;
        historial.add("Préstamo solicitado: $" + monto);
    }

    public void pagarPrestamo(int index, double cantidad) {
        if (index < 0 || index >= prestamos.size() || cantidad <= 0) return;
        Prestamo p = prestamos.get(index);
        if (cantidad > saldo) return;
        saldo -= cantidad;
        p.pagar(cantidad);
        historial.add("Pago de préstamo: $" + cantidad);
        if (p.estaPagado()) {
            prestamos.remove(index);
            historial.add("Préstamo pagado y cerrado");
        }
    }
}

