package com.juric.carbon.service.hash;

import java.util.Arrays;

/**
 * Created by Eric on 10/11/2015.
 */
public enum HashVersion {
    SHA256_1000(new SHA256$1000());

    private HashAlgorithm hashAlgorithm;

    HashVersion(HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public HashAlgorithm getAlgorithm() {
        return hashAlgorithm;
    }
    public static HashVersion fromValue(int value) {
        HashVersion[] values = HashVersion.values();
        if (value < values.length && value >= 0) {
            return values[value];
        } else {
            throw new IllegalArgumentException("invalid value for HashVersion, value=" + value);
        }
    }

    public static HashVersion[] findRoute(HashVersion from, HashVersion to) {
        int ordinalFrom = from == null? 0 : from.ordinal()+1;
        int ordinalTo = to.ordinal()+1;
        return Arrays.copyOfRange(HashVersion.values(), ordinalFrom, ordinalTo);
    }

}
