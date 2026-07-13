package ar.edu.unq.po2.TPTemplateAdapter.Filtros;

import java.util.List;
import java.util.Map;

public interface WikipediaPage {

    String getTitle(); /*retorna el título de la página.*/
    List<WikipediaPage> getLinks(); /*retorna una Lista de las páginas de Wikipedia con las que se conecta.*/
    Map<String, WikipediaPage> getInfobox(); /*retorna un Map con un valor en texto y la pagina que describe ese valor que aparecen en los
                                                infobox de la página de Wikipedia.*/


}


//La lógica de cada filtro es la siguiente:
//MismaLetraInicial retorna como páginas similares aquellas que poseen la misma
//primera letra en el comienzo del título, por ejemplo “La Plata” es similar con “Lucas Art” y “Lobo”.
//LinkEnComun retorna como páginas similares aquellas que posean al menos un link a
//una página en común, por ejemplo si la página de “Gimnasia y Esgrima La Plata” tiene un
//link a la página “La Plata” y la página “Buenos Aires” tiene un link a “La Plata” esas páginas serian similares.
//PropiedadEnComun, retorna aquellas páginas que poseen alguna propiedad del infobox en común, por ejemplo si la página
//de una persona tiene la propiedad “birth_place” y otra página posee también la propiedad “birth_place” serian similares mutuamente. En este
//caso, no importa que el valor de la propiedad sea diferente.

