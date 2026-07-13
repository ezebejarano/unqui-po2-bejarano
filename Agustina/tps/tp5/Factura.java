package ar.edu.unq.po2.tp5;

public abstract class Factura {

    public abstract Double getMontoAPagar();

    public void registrarEnAgencia(Agencia agencia) {
        agencia.registrarPago(this);
        };
}


// incorporar pago de facturas de servicios e impuestos en las cajas de venta.
// los servicios tienen un costo por unidad consumida y una cantidad de unidades consumidas
// en el periodo facturado, y para calcular el monto a pagar deben multiplicarse ambas cantidades
// En el caso de los impuestos, el monto a pagar esta determinado por la tasa del servicio
// que es un valor fijo en pesos. Ademas, es necesario registrar el pago del servicio/impuesto,
// que en ambos casos consiste en notificar a la agencia recaudadora. Para ello se dispone de una
// interface como se describe a continuacion

// public interface Agencia {
//     public void registrarPago (Factura factura);
// }