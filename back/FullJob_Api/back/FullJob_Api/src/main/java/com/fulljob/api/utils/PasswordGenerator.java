package com.fulljob.api.utils;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class PasswordGenerator {

    /**
     * Hemos creado este método para generar una contraseña aleatoria con letras y números.
     *
     * Lo que hemos hecho paso a paso es:
     * 1. Hemos preparado una cadena con todos los caracteres que se pueden usar en la contraseña.
     * 2. Hemos usado un generador de números aleatorios seguros para elegir letras y números al azar.
     * 3. Hemos generado tantos caracteres como nos pidan (según la longitud que nos digan).
     * 4. Por cada número aleatorio generado, hemos cogido la letra o número correspondiente de la cadena.
     * 5. Al final, hemos unido todos los caracteres en una sola cadena que es la contraseña final.
     *
     * @param longitud La cantidad de caracteres que debe tener la contraseña.
     * @return La contraseña generada con letras y números, sin encriptar.
     */

    // Aquí hemos puesto todos los caracteres que pueden formar la contraseña (mayúsculas, minúsculas y números)
    private static final String CHARS_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Hemos creado un generador de números aleatorios que es seguro para generar las contraseñas
    private static final SecureRandom RNG = new SecureRandom();

    // Este método genera una contraseña aleatoria con la longitud que le digamos
    public static String generarPasswordAleatoria(int longitud) {
        return RNG.ints(longitud, 0, CHARS_PERMITIDOS.length())                  // Hemos generado 'longitud' números aleatorios entre 0 y el tamaño de la cadena de caracteres
                 .mapToObj(i -> String.valueOf(CHARS_PERMITIDOS.charAt(i)))     // Con cada número, hemos cogido el carácter que corresponde en la cadena
                 .collect(Collectors.joining());                                // Y los hemos unido todos en una única contraseña
    }
}
