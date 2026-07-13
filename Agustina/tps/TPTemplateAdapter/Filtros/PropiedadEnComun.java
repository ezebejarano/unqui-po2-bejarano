package ar.edu.unq.po2.TPTemplateAdapter.Filtros;

import java.util.List;
import java.util.Map;

public class PropiedadEnComun extends FiltrosWikipediaPage{

    @Override
    protected boolean cumpleHeuristica(WikipediaPage page, WikipediaPage otraPagina) {
        Map<String, WikipediaPage> infoboxPage = page.getInfobox();
        Map<String, WikipediaPage> infoboxOtra = otraPagina.getInfobox();

        if (infoboxPage == null || infoboxOtra == null) {
            return false;
        }

        // Evaluamos si comparten al menos una clave (key) en el mapa
        return infoboxPage.keySet().stream().anyMatch(key -> infoboxOtra.containsKey(key));
    }
}

//PropiedadEnComun, retorna aquellas páginas que poseen alguna propiedad del infobox en común, por ejemplo si la página
//de una persona tiene la propiedad “birth_place” y otra página posee también la propiedad “birth_place” serian similares mutuamente. En este
//caso, no importa que el valor de la propiedad sea diferente.