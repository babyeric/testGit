package com.juric.carbon.service.hash;

/**
 * Created by Eric on 10/11/2015.
 */
public interface HashAlgorithm {
    byte[] hash(byte[] input);
}
