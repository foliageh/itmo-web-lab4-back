package web.twillice.rest.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncrypter {
    private static final int BCOST = 12;

    public static String encrypt(String password) {
        return BCrypt.withDefaults().hashToString(BCOST, password.toCharArray());
    }

    public static boolean checkPassword(String password, String encryptedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword).verified;
    }
}
