package com.example.utez2elibreriajavafxequipo13.model;

/**
 * Esta clase representa la entidad "Libro". Es el "molde" con el que creamos
 * cada registro de nuestra biblioteca.
 */
public class Libro {

    /*
       (Atributos)
       Estas son las características o datos que definen a un libro.
       Se declaran como 'private' por el concepto de ENCAPSULAMIENTO.
       Significa que estas variables están "protegidas" y no pueden ser modificadas
       directamente desde otras clases (como el Controller), evitando errores de datos.
    */
    private String id, titulo, autor, genero; // Datos de texto
    private int anio; // Dato numérico entero
    private boolean disponible; // Dato lógico: true (Disponible) o false (Prestado)

    /*
       Constructor
       Es el método que "nace" con el objeto. Se activa cuando haces: new Libro(...).
       Su función es recibir los datos que vienen del formulario y asignarlos
       a las variables privadas que definimos arriba.

       this: Se usa para diferenciar. "this.id" es la variable de la clase (la privada),
       mientras que "id" (sin el this) es el dato que acaba de llegar por el paréntesis.
    */
    public Libro(String id, String titulo, String autor, int anio, String genero, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
        this.disponible = disponible;
    }

    /*
       Getters: Métodos para LEER.
       Como las variables son privadas, otras clases (como la Tabla) no pueden "ver" el título.
       Entonces llaman a 'getTitulo()' para que el objeto les devuelva el valor de forma controlada.
    */
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public String getGenero() { return genero; }

    // En los booleanos, el "get" cambia de nombre a "is" (¿Es disponible?)
    public boolean isDisponible() { return disponible; }

    /*
       Setters: Métodos para ESCRIBIR o MODIFICAR.
       Se usan principalmente cuando editamos un libro. El programa toma el objeto existente
       y usa estos métodos para cambiar la información vieja por la nueva que escribió el usuario.
    */
    public void setId(String id) { this.id = id; }
    public void setTitulo(String t) { this.titulo = t; }
    public void setAutor(String a) { this.autor = a; }
    public void setAnio(int n) { this.anio = n; }
    public void setGenero(String g) { this.genero = g; }
    public void setDisponible(boolean d) { this.disponible = d; }

    /*
       toString(): Es el "traductor" a texto plano.
       @Override: Indica que estamos personalizando un método que ya existe en Java.

       Cuando el LibroService quiere guardar los libros en el archivo 'libros.txt',
       usa este método para convertir el objeto en una sola línea de texto.

       El formato usa palitos (|) para que luego sea fácil volver a separar los datos.
       Ejemplo de salida: LIB-001|Don Quijote|Cervantes|1605|Novela|true
    */
    @Override
    public String toString() {
        return id + "|" + titulo + "|" + autor + "|" + anio + "|" + genero + "|" + disponible;
    }
}