package ar.edu.unq.po2.TPTemplateAdapter.PalabrasOrdenadas;

import javax.swing.*;

public class ListaDePalabrasOrdenadasAdapter extends DefaultListModel<String>{

    // Composición: Guardamos la referencia al objeto que queremos adaptar (Adaptee)
    private ListaDePalabrasOrdenadas listaOrdenada;

    public ListaDePalabrasOrdenadasAdapter(ListaDePalabrasOrdenadas listaOrdenada) {
        this.listaOrdenada = listaOrdenada;
    }

    @Override
    public void addElement(String element) {
        // 1. Delegamos la responsabilidad de agregar y ordenar a la clase original
        this.listaOrdenada.agregarPalabra(element);

        // 2. Sincronizamos el modelo visual de Swing (DefaultListModel)
        this.actualizarModeloVisual();
    }

    private void actualizarModeloVisual() {
        // Limpiamos los elementos que tenía el DefaultListModel para no duplicar
        this.clear();

        // Recorremos la lista interna adaptada y la volcamos al modelo visual
        for (int i = 0; i < this.listaOrdenada.cantidadDePalabras(); i++) {
            super.addElement(this.listaOrdenada.getPalabraDePosicion(i));
        }
    }
}
