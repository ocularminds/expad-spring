package com.ocularminds.expad.crypto;

import com.ocularminds.expad.common.Strings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crypto {

    static final Logger LOG = LoggerFactory.getLogger(Crypto.class);
    private final Cipher cipher;
    public static final String KEY_DIR = System.getenv("JBOSS_HOME") + File.separator + "expad";

    public Crypto() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
    }

    public void generate(String keyAlgorithm, int numbits) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGen.initialize(numbits);
            KeyPair keyPair = keyGen.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            LOG.info("\n" + "Generating key/value pair using {} algorithm", privateKey.getAlgorithm());
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();

            if (!new File(KEY_DIR).exists()) {
                new File(KEY_DIR).mkdir();
            }
            Encoder base64 = Base64.getEncoder();
            saveToFile(KEY_DIR, "duk_public.key", new String(base64.encode(publicKeyBytes)));
            saveToFile(KEY_DIR, "duk_private.key", new String(base64.encode(privateKeyBytes)));

        } catch (NoSuchAlgorithmException | IOException e) {
            LOG.error("error ", e);
        }
    }

    public void saveToFile(String dir, String file, String data) throws IOException {
        try {
            java.io.FileWriter fw = new java.io.FileWriter(new File(dir, file));
            try (java.io.PrintWriter out = new java.io.PrintWriter(new java.io.BufferedWriter(fw))) {
                out.print(data);
                out.flush();
            }
        } catch (IOException e) {
            throw new IOException("Unexpected error", e);
        }
    }

    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(keyBytes));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(keyBytes));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public void encryptFile(byte[] input, File output, PrivateKey key)
            throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    public void decryptFile(byte[] input, File output, PublicKey key)
            throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    private void writeToFile(File output, byte[] toWrite)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(toWrite);
            fos.flush();
        }
    }

    public String encryptText(String msg, PrivateKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Strings.byte2hex(cipher.doFinal(msg.getBytes("UTF-8")));
    }

    public String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Strings.hex2byte(msg)), "UTF-8");
    }

    public byte[] getFileInBytes(File f) throws IOException {
        byte[] fbytes;
        try (FileInputStream fis = new FileInputStream(f)) {
            fbytes = new byte[(int) f.length()];
            fis.read(fbytes);
        }
        return fbytes;
    }

    public static void main(String[] args) throws Exception {
        Crypto ac = new Crypto();
        PrivateKey privateKey = ac.getPrivate(KEY_DIR + File.separator + "duk_private.key");
        PublicKey publicKey = ac.getPublic(KEY_DIR + File.separator + "duk_public.key");

        String msg = "Cryptography is fun!";
        String encrypted_msg = ac.encryptText(msg, privateKey);
        String decrypted_msg = ac.decryptText(encrypted_msg, publicKey);
        LOG.info("Original Message: " + msg
                + "\nEncrypted Message: " + encrypted_msg
                + "\nDecrypted Message: " + decrypted_msg);

        if (new File("KeyPair/text.txt").exists()) {
            ac.encryptFile(ac.getFileInBytes(new File("KeyPair/text.txt")),
                    new File("KeyPair/text_encrypted.txt"), privateKey);
            ac.decryptFile(ac.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
                    new File("KeyPair/text_decrypted.txt"), publicKey);
        } else {
            LOG.info("Create a file text.txt under folder KeyPair");
        }
    }
}
