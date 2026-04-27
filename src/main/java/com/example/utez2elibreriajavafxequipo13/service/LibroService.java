package com.example.utez2elibreriajavafxequipo13.service;

import com.example.utez2elibreriajavafxequipo13.model.Libro;
import com.example.utez2elibreriajavafxequipo13.repository.FileRepository;
import java.util.*;

/**
 * El Service es la capa intermedia. Aquí se toman decisiones lógicas antes
 * de mandar los datos al repositorio o mostrarlos en la pantalla.
 */
public class LibroService {

    /*
       static: Al ser estática, la lista pertenece a la clase y no a un objeto.
       Esto asegura que todas las ventanas (Main y Formulario) vean EXACTAMENTE
       la misma lista de libros. Si uno añade algo, el otro lo ve al instante.
    */
    private static final List<Libro> libros = new ArrayList<>();
    // Instancia del repositorio para leer y escribir en el disco duro.
    private static final FileRepository repo = new FileRepository();

    /**
     * Constructor del servicio.
     * Se encarga de "despertar" los datos. Si la lista en memoria está vacía,
     * va al repositorio, lee el archivo libros.txt y llena la lista.
     */
    public LibroService() {
        if (libros.isEmpty()) libros.addAll(repo.cargar());
    }

    // Método simple que entrega la lista de libros actual a quien la pida (como la tabla).
    public List<Libro> getLibros() { return libros; }

    /**
     * Regla de negocio: No permite registrar dos libros con el mismo título y autor.
     */
    public void agregar(Libro libro) {
        // Recorremos la lista libro por libro para comparar.
        for (Libro l : libros) {
            // equalsIgnoreCase: Compara sin importar mayúsculas o minúsculas.
            if (l.getTitulo().equalsIgnoreCase(libro.getTitulo()) && l.getAutor().equalsIgnoreCase(libro.getAutor())) {
                // Si coinciden, lanzamos una excepción que detiene el proceso de guardado.
                throw new RuntimeException("Este libro ya esta registrado en el sistema.");
            }
        }
        // Si pasó la validación de arriba, se añade a la lista en memoria.
        libros.add(libro);
        // Inmediatamente guardamos en el archivo para que no se pierda el cambio.
        guardarCambios();
    }

    /**
     * Quita un libro de la lista y actualiza el archivo físico.
     */
    public void eliminar(Libro libro) {
        libros.remove(libro);
        guardarCambios();
    }

    // Método puente que le ordena al repositorio escribir la lista actual en el TXT.
    public void guardarCambios() { repo.guardar(libros); }

    /**
     * Generador de ID Automático (Formato LIB-001, LIB-002, etc.)
     * Es más seguro que dejar que el usuario lo escriba, porque evita IDs repetidos.
     */
    public String generarId() {
        /*
           .stream(): Convierte la lista en un flujo de datos para procesarla rápido.
           .map(): Transforma el ID (LIB-005) eliminando el prefijo para quedarnos con "005".
           .mapToInt(): Convierte el texto "005" en el número entero 5.
           .max(): Busca el número más alto en la lista.
           .orElse(0): Si la lista está vacía, empieza desde el número 0.
        */
        int max = libros.stream()
                .map(l -> l.getId().replace("LIB-", ""))
                .mapToInt(Integer::parseInt)
                .max().orElse(0);

        /* String.format: Crea el nuevo ID sumándole 1 al máximo encontrado.
           %03d: Significa "Dígito entero de 3 espacios, rellena con ceros a la izquierda".
        */
        return String.format("LIB-%03d", max + 1);
    }

    /**
     * Pide al repositorio crear el archivo .csv para usar en Excel.
     */
    public void exportar() { repo.exportarCSV(libros); }
}