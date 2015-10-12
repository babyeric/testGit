package com.juric.carbon.service.user.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Eric on 10/11/2015.
 */
public class SHA512$1000 implements HashAlgorithm {
    private final static int ROUNDS = 1000;

    @Override
    public byte[] hash(byte[] input) {
        MessageDigest md;
        try {
             md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }

        byte[] ret = input;
        for (int i=0; i<ROUNDS; ++i) {
            md.update(ret);
            ret = md.digest();
        }
        return ret;
    }
}
