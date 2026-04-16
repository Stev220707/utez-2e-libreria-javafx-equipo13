package com.example.utez2elibreriajavafxequipo13.service;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.repository.FileRepository;
import java.util.*;

public class LibroService {
    private static final List<Libro> libros = new ArrayList<>();
    private static final FileRepository repo = new FileRepository();

    public LibroService() {
        if (libros.isEmpty()) libros.addAll(repo.cargar());
    }

    public List<Libro> getLibros() { return libros; }

    public void agregar(Libro libro) {
        // Validar que no exista un libro con el mismo título y autor
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(libro.getTitulo()) && l.getAutor().equalsIgnoreCase(libro.getAutor())) {
                throw new RuntimeException("Este libro ya está registrado en el sistema.");
            }
        }
        libros.add(libro);
        guardarCambios();
    }

    public void eliminar(Libro libro) {
        libros.remove(libro);
        guardarCambios();
    }

    public void guardarCambios() { repo.guardar(libros); }

    public String generarId() {
        int max = libros.stream()
                .map(l -> l.getId().replace("LIB-", ""))
                .mapToInt(Integer::parseInt)
                .max().orElse(0);
        return String.format("LIB-%03d", max + 1);
    }

    public void exportar() { repo.exportarCSV(libros); }
}