package ar.edu.unq.po2.TPTemplateAdapter.Filtros;

import java.util.List;
import java.util.Map;

public class MismaLetraInicial extends FiltrosWikipediaPage{

    @Override
    protected boolean cumpleHeuristica(WikipediaPage page, WikipediaPage otraPagina) {
        if (page.getTitle().isEmpty() || otraPagina.getTitle().isEmpty()) {
            return false;
        }
        char letraBase = page.getTitle().toLowerCase().charAt(0);
        char letraOtra = otraPagina.getTitle().toLowerCase().charAt(0);

        return letraBase == letraOtra;
    }
}

//MismaLetraInicial retorna como páginas similares aquellas que poseen la misma
//primera letra en el comienzo del título, por ejemplo “La Plata” es similar con “Lucas Art” y “Lobo”.
