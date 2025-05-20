
package javaapplication61;

public class Prestamo {
    private final double monto;
    private double pagado;

    public Prestamo(double monto) {
        this.monto = monto;
        this.pagado = 0;
    }

    public double getMonto() {
        return monto;
    }

    public double getPagado() {
        return pagado;
    }

    public void pagar(double cantidad) {
        pagado += cantidad;
        if (pagado > monto) pagado = monto;
    }

    public boolean estaPagado() {
        return pagado >= monto;
    }
}
