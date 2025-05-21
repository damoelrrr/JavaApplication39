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
        if (monto <= 0) {
            return;
        }
        saldo += monto;
        historial.add("Depósito: $" + monto);
    }

    public boolean retirar(double monto) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }
        saldo -= monto;
        historial.add("Retiro: $" + monto);
        return true;
    }

    public boolean transferir(CuentaBancaria destino, double monto) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }
        saldo -= monto;
        destino.saldo += monto;
        historial.add("Transferencia enviada: $" + monto);
        destino.historial.add("Transferencia recibida: $" + monto);
        return true;
    }

    public void solicitarPrestamo(double monto) {
        if (monto <= 0) {
            return;
        }
        Prestamo nuevo = new Prestamo(monto);
        prestamos.add(nuevo);
        saldo += monto;
        historial.add("Prestamo solicitado: $" + monto);
    }

    public void pagarPrestamo(int index, double cantidad, double cantidad2) {
        if (index < 0 || index >= prestamos.size() || cantidad <= 0) {
            return;
        }
        Prestamo p = prestamos.get(index);

        if (p.estaPagado() || cantidad > saldo) {
            return;
        }
        double restante = p.getMonto() - p.getPagado();

        if (cantidad > restante) {
            historial.add("Intento de pagar más del préstamo: $" + cantidad
                    + ". Solo se necesitaban $" + restante + ". Se pagó solo lo necesario.");
            cantidad = restante;  
        }

        double pagoReal = Math.min(cantidad, restante);

        saldo -= pagoReal;
        p.pagar(pagoReal);
        historial.add("Pago de préstamo: $" + pagoReal);

        if (p.estaPagado()) {
            historial.add("Préstamo pagado y cerrado");
            prestamos.remove(index);
        }
    }

    public double getTotalDeuda() {
        double total = 0;
        for (Prestamo p : prestamos) {
            total += (p.getMonto() - p.getPagado());
        }
        return total;
    }
}
