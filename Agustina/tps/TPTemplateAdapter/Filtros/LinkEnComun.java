package ar.edu.unq.po2.TPTemplateAdapter.Filtros;

import java.util.List;
import java.util.Map;

public class LinkEnComun extends FiltrosWikipediaPage{

    @Override
    protected boolean cumpleHeuristica(WikipediaPage page, WikipediaPage otraPagina) {
        List<WikipediaPage> links    = page.getLinks();
        List<WikipediaPage> linksOtro = otraPagina.getLinks();

        if (links == null || linksOtro == null) {
            return false;
        }
        return links.stream().anyMatch(link -> linksOtro.contains(link));
    }
}

//LinkEnComun retorna como páginas similares aquellas que posean al menos un link a
//una página en común, por ejemplo si la página de “Gimnasia y Esgrima La Plata” tiene un
//link a la página “La Plata” y la página “Buenos Aires” tiene un link a “La Plata” esas páginas serian similares.
