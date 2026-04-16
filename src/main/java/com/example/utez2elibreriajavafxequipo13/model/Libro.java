package com.example.utez2elibreriajavafxequipo13.model;

public class Libro {
    private String id, titulo, autor, genero;
    private int anio;
    private boolean disponible;

    public Libro(String id, String titulo, String autor, int anio, String genero, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
        this.disponible = disponible;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public String getGenero() { return genero; }
    public boolean isDisponible() { return disponible; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitulo(String t) { this.titulo = t; }
    public void setAutor(String a) { this.autor = a; }
    public void setAnio(int n) { this.anio = n; }
    public void setGenero(String g) { this.genero = g; }
    public void setDisponible(boolean d) { this.disponible = d; }

    @Override
    public String toString() {
        return id + "|" + titulo + "|" + autor + "|" + anio + "|" + genero + "|" + disponible;
    }
}