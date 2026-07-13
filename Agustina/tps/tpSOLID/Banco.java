package ar.edu.unq.po2.tpSOLID;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<Cliente> clientes;
    private List<SolicitudDeCredito> solicitudesDeCredito;

    public Banco(){
        this.clientes = new ArrayList<>();
        this.solicitudesDeCredito = new ArrayList<>();
    }
    public void registrarCliente(Cliente c){
        this.clientes.add(c);
    }

    public void registrarSolicitudDeCredito(SolicitudDeCredito sc){
        this.solicitudesDeCredito.add(sc);
    }

    public Double montoTotalADesembolsar(){
        Double total = 0.0;
        for (SolicitudDeCredito s : this.solicitudesDeCredito) {
            if (s.esAceptable()) {
                total += s.getMontoSolicitado();
            }
        }

        return total;
    }
}


// Cumple con SOLID por estas razones:
// por un lado hago abstraccion en solicitudDeCredito, ayudando al principio de responsabilidad unica SRP. Define la responsabilidad general de ser
// una solicitud, delegando los detalles a las hijas.
// Herencia en solicitudDeCreditoPersonal y solicitudDeCreditoHipotecario. Decidiendo que los atributos plazoMeses y montoSolicitud vivan en la clase madre
// relacionado con LSP. Principio de Sustitución de Liskov Al heredar de una clase madre común, garantizás que las hijas mantengan la "forma" de una solicitud,
// permitiendo que el Banco las use indistintamente.
// Polimorfismo. en montoTotalADesembolar del banco. Relacion con OCP. Principio Abierto/Cerrado. gracias al polimorfismo el banco esta abierto a
// nuevos o diferentes tipos de solicitud de credito

//"Mi modelo cumple con SOLID porque:
//SRP (Responsabilidad Única): Separé la lógica de la propiedad inmobiliaria en su propia clase, evitando que la solicitud gestione datos que no le corresponden.
//OCP (Abierto/Cerrado): Mediante la abstracción de SolicitudDeCredito, el sistema permite agregar nuevos tipos de préstamos sin modificar la lógica del Banco.
//LSP (Sustitución de Liskov): Las clases hijas respetan el contrato de la madre, permitiendo que el Banco las procese en una lista genérica sin errores.
//DIP (Inversión de Dependencias): El Banco depende de la interfaz/clase abstracta SolicitudDeCredito y no de implementaciones concretas, lo que reduce el acoplamiento."