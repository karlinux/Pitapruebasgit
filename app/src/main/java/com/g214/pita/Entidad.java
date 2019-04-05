package com.g214.pita;

public class Entidad {

    private String id;
    private String titulo;
    private String contenido;

    public Entidad(String id, String titulo, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public  String getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }
}
