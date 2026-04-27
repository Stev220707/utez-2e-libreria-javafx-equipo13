package com.example.utez2elibreriajavafxequipo13.repository;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import java.io.*;
import java.util.*;

/**
 * Esta clase es el Repositorio. Su único trabajo es comunicarse con el disco duro.
 * Se encarga de que los datos no se borren al cerrar el programa.
 */
public class FileRepository {

    // Define el nombre del archivo donde se guardara todo.
    // Al no ponerle una ruta definida como C:/..., se crea en la carpeta raíz del proyecto.
    private final String RUTA = "libros.txt";

    /**
     * Lee el archivo libros.txt y convierte el texto de nuevo en objetos Libro.
     * @return Una lista de objetos Libro.
     */
    public List<Libro> cargar() {
        // Creamos una lista vacía para ir metiendo los libros que encontremos en el archivo.
        List<Libro> lista = new ArrayList<>();

        // Creamos un objeto File para representar el archivo físicamente.
        File archivo = new File(RUTA);

        // Validación de seguridad: Si el archivo no existe, regresamos la lista vacía para que el programa no truene.
        if (!archivo.exists()) return lista;

        /*
           Try-with-resources: Esta estructura (el try con paréntesis) asegura que
           el archivo se cierre automáticamente aunque ocurra un error.
           BufferedReader: Es como un "almacén temporal" que lee el texto rápido.
           FileReader: Es el que abre la puerta del archivo para leerlo.
        */
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // br.readLine() lee el archivo línea por línea hasta llegar al final (null).
            while ((linea = br.readLine()) != null) {
                // El método split("\\|") corta la línea cada vez que encuentra un palito |.
                // Usamos doble diagonal porque el palito es un caracter especial en Java.
                String[] p = linea.split("\\|");

                // PARSEO: Convertimos las cadenas de texto a sus tipos originales (int y boolean).
                // Luego creamos el objeto Libro y lo añadimos a nuestra lista.
                lista.add(new Libro(p[0], p[1], p[2], Integer.parseInt(p[3]), p[4], Boolean.parseBoolean(p[5])));
            }
        } catch (Exception e) {
            // Si el archivo está corrupto o no se puede leer, imprime el error para el programador.
            e.printStackTrace();
        }

        // Devolvemos la lista llena de objetos reales para que el controlador los use.
        return lista;
    }

    /**
     * Toma la lista de libros de la memoria y la escribe en el archivo libros.txt.
     * Importante: Esto sobrescribe el archivo para mantenerlo actualizado.
     */
    public void guardar(List<Libro> lista) {
        /*
           PrintWriter y FileWriter: Herramientas para ESCRIBIR texto en un archivo.
           Si el archivo no existe, lo crean automáticamente.
        */
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            // Recorremos la lista de objetos libro uno por uno.
            for (Libro l : lista) {
                // Usamos el método toString() que definimos en la clase Libro.
                // Esto guarda el libro con el formato de palitos: ID|Titulo|Autor...
                pw.println(l.toString());
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Crea un archivo CSV (compatible con Excel) para generar un reporte.
     */
    public void exportarCSV(List<Libro> lista) {
        // El nombre del archivo ahora termina en .csv
        try (PrintWriter pw = new PrintWriter(new FileWriter("reporte_libros.csv"))) {
            // Escribimos la primera línea (los encabezados de las columnas).
            pw.println("ID,Titulo,Autor,Año,Genero,Disponible");

            // Recorremos la lista para transformar cada libro en formato separado por COMAS.
            for (Libro l : lista) {
                // String.format nos ayuda a poner comas entre cada dato de forma limpia.
                pw.println(String.format("%s,%s,%s,%d,%s,%s",
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getAnio(),
                        l.getGenero(),
                        // Convertimos el true/false en un "Si" o "No" para que el reporte sea legible.
                        l.isDisponible() ? "Si" : "No"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}