package com.example.utez2elibreriajavafxequipo13.service;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.repository.FileRepository;
import java.util.*;

public class LibroService {

    private static final List<Libro> libros = new ArrayList<>();
    private static final FileRepository repo = new FileRepository();

    public LibroService() {
        if (libros.isEmpty()) {
            libros.addAll(repo.cargar());
        }
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void agregar(Libro libro) {
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(libro.getTitulo()) &&
                    l.getAutor().equalsIgnoreCase(libro.getAutor())) {
                throw new RuntimeException("Ese libro ya existe");
            }
        }
        libros.add(libro);
        repo.guardar(libros);
    }

    public void eliminar(Libro libro) {
        libros.remove(libro);
        repo.guardar(libros);
    }

    public void guardarCambios() {
        repo.guardar(libros);
    }

    public String generarId() {
        int max = 0;
        for (Libro l : libros) {
            try {
                String num = l.getId().replace("LIB-", "");
                int n = Integer.parseInt(num);
                if (n > max) max = n;
            } catch (Exception ignored) {}
        }
        return String.format("LIB-%03d", max + 1);
    }

    public String exportar() {
        return repo.exportar(libros);
    }

    public void exportarComo(String ruta) {
        repo.exportarComo(libros, ruta);
    }
}