package com.ocularminds.expad.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringEncrypter {

    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    public static final String DES_ENCRYPTION_SCHEME = "DES";
    public static final String DEFAULT_ENCRYPTION_KEY = "CXVHAll$yahweigh'* O C U L A R - M I N D S SHA#";
    private KeySpec keySpec;
    private SecretKeyFactory keyFactory;
    private Cipher cipher;
    static final Logger LOG = LoggerFactory.getLogger(StringEncrypter.class);

    public StringEncrypter() throws EncryptionException {
        this("DES");
    }

    public StringEncrypter(String encryptionScheme) throws EncryptionException {
        this(encryptionScheme, "CXVHAll$yahweigh'* O C U L A R - M I N D S SHA#");
    }

    public StringEncrypter(String encryptionScheme, String encryptionKey) throws EncryptionException {
        if (encryptionKey == null) {
            throw new IllegalArgumentException("encryption key was null");
        }
        if (encryptionKey.trim().length() < 24) {
            throw new IllegalArgumentException("encryption key was less than 24 characters");
        }
        try {
            byte[] keyAsBytes = encryptionKey.getBytes("UTF8");
            switch (encryptionScheme) {
                case "DESede":
                    this.keySpec = new DESedeKeySpec(keyAsBytes);
                    break;
                case "DES":
                    this.keySpec = new DESKeySpec(keyAsBytes);
                    break;
                default:
                    throw new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme);
            }
            this.keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            this.cipher = Cipher.getInstance(encryptionScheme);
        } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new EncryptionException(e);
        }
    }

    public String encrypt(String unencryptedString) throws EncryptionException {
        if (unencryptedString == null || unencryptedString.trim().length() == 0) {
            throw new IllegalArgumentException("unencrypted string was null or empty");
        }
        try {
            SecretKey key = this.keyFactory.generateSecret(this.keySpec);
            this.cipher.init(1, key);
            byte[] cleartext = unencryptedString.getBytes("UTF8");
            byte[] ciphertext = this.cipher.doFinal(cleartext);
            Base64.Encoder base64encoder = Base64.getEncoder();
            return base64encoder.encodeToString(ciphertext);
        } catch (UnsupportedEncodingException | InvalidKeyException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException e) {
            throw new EncryptionException(e);
        }
    }

    public String decrypt(String encryptedString) throws EncryptionException {
        if (encryptedString == null || encryptedString.trim().length() <= 0) {
            throw new IllegalArgumentException("encrypted string was null or empty");
        }
        try {
            SecretKey key = this.keyFactory.generateSecret(this.keySpec);
            this.cipher.init(2, key);
            Base64.Decoder base64decoder = Base64.getDecoder();
            byte[] cleartext = base64decoder.decode(encryptedString);
            byte[] ciphertext = this.cipher.doFinal(cleartext);
            return StringEncrypter.bytes2String(ciphertext);
        } catch (InvalidKeyException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException e) {
            throw new EncryptionException(e);
        }
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            stringBuffer.append(bytes[i]);
        }
        return stringBuffer.toString();
    }

    public void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = (b & 240) >> 4;
        int low = b & 15;
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    public String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; ++i) {
            this.byte2hex(block[i], buf);
            if (i >= len - 1) {
                continue;
            }
            buf.append(":");
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        String accountno = "4556238577532239";
        String encoded;
        try {
            encoded = new StringEncrypter().toHexString(accountno.getBytes());
            LOG.info("ACCT:" + accountno);
            LOG.info("ENCD:" + encoded);
        } catch (EncryptionException e) {
            LOG.error("error ", e);
        }
    }
}
