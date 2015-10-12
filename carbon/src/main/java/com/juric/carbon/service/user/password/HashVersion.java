package com.juric.carbon.service.user.password;

import java.util.Arrays;

/**
 * Created by Eric on 10/11/2015.
 */
public enum HashVersion {
    SHA512_1000(new SHA512$1000());

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

    public int value() {
        return this.ordinal();
    }

    public static HashVersion[] findRoute(HashVersion from, HashVersion to) {
        int ordinalFrom = from == null? 0 : from.ordinal()+1;
        int ordinalTo = to.ordinal()+1;
        return Arrays.copyOfRange(HashVersion.values(), ordinalFrom, ordinalTo);
    }

}
