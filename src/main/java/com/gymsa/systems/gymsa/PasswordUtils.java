package com.gymsa.systems.gymsa;

import org.mindrot.jbcrypt.BCrypt;

//Colección de métodos para trabajar con contraseñas encriptadas

public class PasswordUtils {

    // Método para hash de la contraseña
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Método para verificar la contraseña
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
