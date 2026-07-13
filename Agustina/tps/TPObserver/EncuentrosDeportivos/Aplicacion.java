package ar.edu.unq.po2.TPObserver.EncuentrosDeportivos;

import java.util.ArrayList;
import java.util.List;

public class Aplicacion implements Observer{
    private String usuario;
    private List<String> deportesDeInteres;
    private List<String> contrincantesDeInteres;
    private int alertasRecibidas = 0;

    public Aplicacion(String usuario) {
            this.usuario = usuario;
            this.deportesDeInteres = new ArrayList<>();
            this.contrincantesDeInteres = new ArrayList<>();
        }

    public void suscribirADeporte(String deporte) {
        this.deportesDeInteres.add(deporte.toLowerCase());
    }

    public void suscribirAContrincante(String contrincante) {
        this.contrincantesDeInteres.add(contrincante.toLowerCase());
    }

    public int getAlertasRecibidas() {
        return alertasRecibidas;
    }

    @Override
    public void update(Partido partido) {
        String deportePartido = partido.getDeporte().toLowerCase();

        boolean leInteresaElDeporte = this.deportesDeInteres.contains(deportePartido);

        boolean leInteresaAlgunContrincante = false;
        for (String contrincantePartido : partido.getContrincantes()) {
            for (String fav : this.contrincantesDeInteres) {
                if (fav.equalsIgnoreCase(contrincantePartido)) {
                    leInteresaAlgunContrincante = true;
                    break;
                }
            }
        }

        if (leInteresaElDeporte || leInteresaAlgunContrincante) {
            this.alertasRecibidas++;
            System.out.println("Alerta para el celular de " + this.usuario + ": ["
                    + partido.getDeporte() + "] " + partido.getResultado());
        }
    }
}