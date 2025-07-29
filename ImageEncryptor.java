import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;

public class ImageEncryptor {

    public static void encrypt(File inputFile, File outputFile, String key) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile, key);
    }

    public static void decrypt(File inputFile, File outputFile, String key) throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile, key);
    }

    private static void doCrypto(int cipherMode, File inputFile, File outputFile, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipherMode, secretKey);

        byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
        byte[] outputBytes = cipher.doFinal(inputBytes);

        Files.write(outputFile.toPath(), outputBytes);
    }
}
