package com.example.utez2elibreriajavafxequipo13.utils;

public class InputValidator {

    public static String validarLibro(String id, String titulo, String autor, String genero, String anioStr) {

        if (id == null || id.isBlank()) {
            return "El ID no puede estar vacio";
        }

        if (!id.matches("lib-\\d{3}")) {
            return "El ID debe tener formato: lib-001";
        }

        if (titulo == null || titulo.isBlank()) {
            return "El titulo no puede estar vacio";
        }

        if (titulo.matches(".*\\d.*")) {
            return "El titulo no debe contener numeros";
        }

        if (autor == null || autor.isBlank()) {
            return "El autor no puede estar vacio";
        }

        if (autor.matches(".*\\d.*")) {
            return "El autor no debe contener numeros";
        }

        if (genero == null || genero.isBlank()) {
            return "El genero no puede estar vacio";
        }

        if (genero.matches(".*\\d.*")) {
            return "El genero no debe contener numeros";
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (Exception e) {
            return "El año debe ser un numero valido";
        }

        if (anio < 1400 || anio > 2026) {
            return "El año debe estar entre 1400 y 2026";
        }

        return null;
    }
}