package dev.filipposcaramuzza.db2_telco_webemployee.utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {
    public static String getSHAPassword(String plainPassword) {
        byte[] bytesOfPassword = plainPassword.getBytes(StandardCharsets.UTF_8);

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        byte[] passwordDigest = md.digest(bytesOfPassword);
        StringBuffer sb = new StringBuffer();
        for (byte b : passwordDigest) sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

        return sb.toString();
    }
}
