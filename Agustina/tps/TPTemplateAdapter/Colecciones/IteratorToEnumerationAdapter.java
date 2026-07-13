package ar.edu.unq.po2.TPTemplateAdapter.Colecciones;

import java.util.Enumeration;
import java.util.Iterator;

// Adaptamos el Iterator para que se comporte como un Enumeration
public class IteratorToEnumerationAdapter implements Enumeration {
    private Iterator iterator; // Composición (Adaptee)

    public IteratorToEnumerationAdapter(Iterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasMoreElements() {
        return this.iterator.hasNext(); // Traduce el mensaje viejo al nuevo
    }

    @Override
    public Object nextElement() {
        return this.iterator.next(); // Traduce el mensaje viejo al nuevo
    }
}
