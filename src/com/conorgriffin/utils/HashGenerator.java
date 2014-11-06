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

    private String hashValue;

    public HashGenerator(File file, String algorithm) throws NoSuchAlgorithmException, IOException {

        MessageDigest md = MessageDigest.getInstance(algorithm);
        FileInputStream fis = new FileInputStream(file);
        byte[] dataBytes = new byte[8192];
        int bytesRead = 0;

        while ((bytesRead = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, bytesRead);
        };

        byte[] digestBytes = md.digest();

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < digestBytes.length; i++) {
            sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        hashValue = sb.toString();
    }

    public HashGenerator(String input, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this(input, algorithm, "UTF-8");
    }
    public HashGenerator(String input, String algorithm, String encoding) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.reset();
        md.update(input.getBytes(encoding));

        byte[] digestBytes = md.digest();

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < digestBytes.length; i++) {
            sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        hashValue = sb.toString();
    }

    public String getHashValue() {
        return hashValue;
    }

    public static void main(String[] args) {
        try {
            HashGenerator hg = new HashGenerator("Conor", "SHA-256");
            System.out.println("SHA1 hash for \"Conor\" is \"" + hg.getHashValue() + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
