package ar.edu.unq.po2.TPTemplateAdapter.Filtros;
import java.util.List;
import java.util.ArrayList;

public abstract  class FiltrosWikipediaPage { //TEMPLATE METHOD
    public final List<WikipediaPage> getSimilarPages(WikipediaPage page, List<WikipediaPage> wikipedia) {
        List<WikipediaPage> similares = new ArrayList<>();
        
        for (WikipediaPage otraPagina : wikipedia) {
            // Regla general: No comparamos la página contra sí misma y debe cumplir la heurística
            if (!page.getTitle().equals(otraPagina.getTitle()) && this.cumpleHeuristica(page, otraPagina)) {
                similares.add(otraPagina);
            }
        }
        return similares;
    }

    // OPERACIÓN PRIMITIVA: Cada subclase la implementa a su manera
    protected abstract boolean cumpleHeuristica(WikipediaPage page, WikipediaPage otraPagina);
}
