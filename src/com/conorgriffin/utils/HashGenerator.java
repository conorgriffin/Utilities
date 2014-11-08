package com.conorgriffin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple utility class for calculating various hash values of Files or Strings
 */
public class HashGenerator {

    private String hashString;

    /**
     * Creates a HashGenerator to generate a hash of a given file
     *
     * @param file
     * @param algorithm
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public HashGenerator(File file, Algorithm algorithm) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance(algorithm.getValue());
        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[8192];
        int bytesRead;
        while ((bytesRead = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, bytesRead);
        }
        generateHashString(md.digest());
        fis.close();
    }

    /**
     * Creates a HashGenerator to generate a hash of a given String using the specified algorithm and default
     * character encoding of UTF-8
     *
     * @param input
     * @param algorithm
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public HashGenerator(String input, Algorithm algorithm) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        this(input, algorithm, "UTF-8");
    }

    /**
     * Creates a HashGenerator to generate a hash of a given String using the specified algorithm and character
     * encoding
     *
     * @param input
     * @param algorithm
     * @param encoding
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public HashGenerator(String input, Algorithm algorithm, String encoding) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(algorithm.getValue());
        md.reset();
        md.update(input.getBytes(encoding));
        generateHashString(md.digest());
    }

    /**
     * Get the value of the calculated hash as a String
     * @return
     */
    public String getHashString() {
        return hashString;
    }

    /**
     * Convert a byte array to a hash String
     * @param digestBytes
     */
    private void generateHashString(byte[] digestBytes) {
        StringBuilder sb = new StringBuilder("");
        for (byte b : digestBytes) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        hashString = sb.toString();
    }

    public enum Algorithm {
        MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA_256("SHA-256"), SHA_512("SHA-512");

        private String value;

        private Algorithm(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
