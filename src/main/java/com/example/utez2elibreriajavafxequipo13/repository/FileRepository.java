package com.example.utez2elibreriajavafxequipo13.repository;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import java.io.*;
import java.util.*;

public class FileRepository {
    private final String RUTA = "libros.txt";

    public List<Libro> cargar() {
        List<Libro> lista = new ArrayList<>();
        File archivo = new File(RUTA);
        if (!archivo.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\|");
                lista.add(new Libro(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], Boolean.parseBoolean(p[5])));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void guardar(List<Libro> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Libro l : lista) pw.println(l.toString());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void exportarCSV(List<Libro> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reporte_libros.csv"))) {
            pw.println("ID,Titulo,Autor,Año,Genero,Disponible");
            for (Libro l : lista) {
                pw.println(String.format("%s,%s,%s,%d,%s,%s",
                        l.getId(), l.getTitulo(), l.getAutor(), l.getAnio(), l.getGenero(),
                        l.isDisponible() ? "Si" : "No"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}