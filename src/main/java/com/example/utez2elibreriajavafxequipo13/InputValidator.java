package com.example.utez2elibreriajavafxequipo13.utils;

public class InputValidator {

    public static void validar(String tit, String aut, String gen, String anioStr) throws Exception {

        // 1. Validar que no haya nada vacío
        if (tit.trim().isEmpty() || aut.trim().isEmpty() || gen.trim().isEmpty() || anioStr.trim().isEmpty()) {
            throw new Exception("Todos los campos son obligatorios. No puedes dejar espacios vacíos.");
        }

        // 2. Validar "Basura" (Evita golpes de teclado como 'asdfg' o 'vvvvv')
        // Si tiene 3 letras iguales seguidas o no tiene vocales, es basura.
        if (aut.matches(".*(.)\\1\\1.*") || gen.matches(".*(.)\\1\\1.*")) {
            throw new Exception("El nombre del Autor o Género parece inválido (letras repetidas).");
        }

        // 3. Validar Autor y Género (Solo letras y espacios, mínimo 3 letras)
        if (!aut.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || aut.trim().length() < 3) {
            throw new Exception("El Autor debe ser un nombre real (mínimo 3 letras, sin números).");
        }

        if (!gen.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || gen.trim().length() < 3) {
            throw new Exception("El Género debe ser válido (mínimo 3 letras, sin números).");
        }

        // 4. Validar Título (Mínimo 3 caracteres)
        if (tit.trim().length() < 3) {
            throw new Exception("El Título es demasiado corto.");
        }

        // 5. Validar Año (4 dígitos y rango lógico)
        if (!anioStr.trim().matches("\\d{4}")) {
            throw new Exception("El año debe ser un número de 4 dígitos.");
        }

        int a = Integer.parseInt(anioStr.trim());
        if (a < 1450 || a > 2026) {
            throw new Exception("El año debe estar entre 1450 y 2026.");
        }
    }
}