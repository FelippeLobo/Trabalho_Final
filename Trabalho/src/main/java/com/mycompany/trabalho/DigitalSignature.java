/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabalho;

import java.security.*;
import java.util.Base64;

/**
 *
 * @author felip
 */
public class DigitalSignature {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public DigitalSignature() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public String signHash(String hash) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(hash.getBytes());
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }

    public boolean verifySignature(String hash, String digitalSignature) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(hash.getBytes());
        byte[] digitalSignatureBytes = Base64.getDecoder().decode(digitalSignature);
        return signature.verify(digitalSignatureBytes);
    }
}
