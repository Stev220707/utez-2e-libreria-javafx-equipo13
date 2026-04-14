package com.example.utez2elibreriajavafxequipo13.repository;

import com.example.utez2elibreriajavafxequipo13.model.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository {

    private final String RUTA = "libros.txt";

    public List<Libro> cargar() {
        List<Libro> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\|");

                lista.add(new Libro(
                        p[0], p[1], p[2],
                        Integer.parseInt(p[3]),
                        p[4],
                        Boolean.parseBoolean(p[5])
                ));
            }
        } catch (Exception e) {

        }

        return lista;
    }

    public void guardar(List<Libro> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Libro l : lista) {
                pw.println(
                        l.getId() + "|" +
                                l.getTitulo() + "|" +
                                l.getAutor() + "|" +
                                l.getAnio() + "|" +
                                l.getGenero() + "|" +
                                l.isDisponible()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String exportar(List<Libro> lista) {
        String ruta = "reporte_libros.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.println("ID,Titulo,Autor,Año,Genero,Disponible");

            for (Libro l : lista) {
                pw.println(
                        l.getId() + "," +
                                l.getTitulo() + "," +
                                l.getAutor() + "," +
                                l.getAnio() + "," +
                                l.getGenero() + "," +
                                (l.isDisponible() ? "Si" : "No")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File(ruta).getAbsolutePath();
    }

    public void exportarComo(List<Libro> lista, String ruta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            pw.println("ID,Titulo,Autor,Año,Genero,Disponible");

            for (Libro l : lista) {
                pw.println(
                        l.getId() + "," +
                                l.getTitulo() + "," +
                                l.getAutor() + "," +
                                l.getAnio() + "," +
                                l.getGenero() + "," +
                                (l.isDisponible() ? "Si" : "No")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}