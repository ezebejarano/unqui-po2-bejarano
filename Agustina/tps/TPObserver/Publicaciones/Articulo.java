package ar.edu.unq.po2.TPObserver.Publicaciones;

public class Articulo {
    private String titulo;
    private String autores;
    private String filiaciones;
    private String tipo;
    private String lugarPublicado;
    private String palabrasClave;

    public Articulo(String titulo, String autores, String filiaciones, String tipo, String lugarPublicado, String palabrasClave){
        this.titulo = titulo;
        this.autores = autores;
        this.filiaciones = filiaciones;
        this.tipo = tipo;
        this.lugarPublicado = lugarPublicado;
        this.palabrasClave = palabrasClave;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getAutores(){
        return this.autores;
    }

    public String getFiliaciones(){
        return this.filiaciones;
    }

    public String getTipo(){
        return this.tipo;
    }

    public String getLugarPublicado(){
        return this.lugarPublicado;
    }

    public String getPalabrasClave(){
        return this.palabrasClave;
    }
}
