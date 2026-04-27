package com.example.utez2elibreriajavafxequipo13;

/**
 * Clase encargada de la validación exhaustiva de datos.
 * Aplica reglas estrictas para evitar datos basura, duplicados o mal formados.
 */
public class InputValidator {

    /**
     * Método principal de validación.
     * @param tit Título del libro
     * @param aut Autor del libro
     * @param gen Género del libro
     * @param anioStr Año en formato String
     * @throws Exception Si alguna regla de integridad es violada.
     */
    public static void validar(String tit, String aut, String gen, String anioStr) throws Exception {

        // 1. VALIDACIÓN DE NULOS Y VACÍOS
        // No permite que ningún campo quede sin información real (usando trim para ignorar espacios).
        if (tit == null || tit.trim().isEmpty() || aut == null || aut.trim().isEmpty() ||
                gen == null || gen.trim().isEmpty() || anioStr == null || anioStr.trim().isEmpty()) {
            throw new Exception("Error: Todos los campos son obligatorios y no pueden estar vacíos.");
        }

        // 2. VALIDACIÓN DE FORMATO DE AÑO (EXACTITUD)
        // El patrón ^[0-9]{4}$ obliga a que sean exactamente 4 dígitos del 0 al 9.
        if (!anioStr.trim().matches("^[0-9]{4}$")) {
            throw new Exception("Error: El año debe tener un formato de exactamente 4 números (ej. 2024).");
        }

        // 3. VALIDACIÓN DE RANGO CRONOLÓGICO
        // Convierte el texto a entero y verifica que esté en el rango permitido.
        try {
            int a = Integer.parseInt(anioStr.trim());
            // 1450 es la fecha aproximada de la invención de la imprenta.
            if (a < 1450 || a > 2026) {
                throw new Exception("Error: El año debe estar en un rango válido (1450 - 2026).");
            }
        } catch (NumberFormatException e) {
            // Este catch atrapa errores si el usuario intentó meter algo que no es un número.
            throw new Exception("Error: El valor del año no es un número válido.");
        }

        // 4. VALIDACIÓN DE COHERENCIA (FILTRO ANTI-BASURA)
        // Se aplican filtros de repetición y estructura de lenguaje al Título y al Autor.
        if (esBasura(tit)) {
            throw new Exception("Error: El título parece contener texto inválido o basura.");
        }

        if (esBasura(aut)) {
            throw new Exception("Error: El nombre del autor contiene patrones de texto no permitidos.");
        }

        if (tieneRepeticiones(tit) || tieneRepeticiones(aut)) {
            throw new Exception("Error: Se detectaron demasiadas letras repetidas consecutivas.");
        }
    }

    /**
     * Busca caracteres repetidos tres veces seguidas (ej. "aaa", "lll").
     * Es sumamente estricto para evitar entradas accidentales o de broma.
     */
    private static boolean tieneRepeticiones(String texto) {
        if (texto == null || texto.length() < 3) return false;

        String t = texto.toLowerCase();
        for (int i = 0; i < t.length() - 2; i++) {
            // Si la letra actual es igual a la siguiente Y a la que sigue después de esa.
            if (t.charAt(i) == t.charAt(i + 1) && t.charAt(i) == t.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determina si una cadena es considerada "basura" mediante reglas lingüísticas.
     */
    private static boolean esBasura(String t) {
        if (t == null) return true;
        String txt = t.toLowerCase().trim();

        // REGLA A: Debe tener al menos una vocal (a, e, i, o, u o acentuadas).
        // Si no tiene vocales, es imposible que sea una palabra en español/inglés.
        boolean sinVocales = !txt.matches(".*[aeiouáéíóú].*");

        // REGLA B: No permite 3 o más consonantes seguidas (Estricto).
        // Antes era {4,}, al bajarlo a {3,} detectará como basura textos como "asf", "dgh".
        boolean muchasConsonantes = txt.matches(".*[bcdfghjklmnpqrstvwxyz]{3,}.*");

        // REGLA C: Longitud mínima de seguridad.
        boolean muyCorto = txt.length() < 3;

        // Si cumple cualquiera de estas, el sistema lo marca como BASURA (true).
        return sinVocales || muchasConsonantes || muyCorto;
    }
}