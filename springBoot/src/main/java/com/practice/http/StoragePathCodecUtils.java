package com.practice.http;

import org.juric.storage.path.StoragePath;

/**
 * Created by Eric on 9/22/2015.
 */
public class StoragePathCodecUtils {
    public final static TokenMap[] tokenMaps = new TokenMap[]{new TokenMap(" ", "_"), new TokenMap(StoragePath.SEPARATOR, "__")};

    private static class TokenMap {
        private String from;
        private String to;

        public TokenMap(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public String encode(String str) {
            return str.replace(from, to);
        }

        public String decode(String str) {
            return str.replace(to, from);
        }
    }

    static String encode(String str) {
        for (TokenMap tokenMap : tokenMaps) {
            str = tokenMap.encode(str);
        }

        return str;
    }

    static String decode(String str) {
        for (int i=tokenMaps.length -1; i>=0; --i) {
            str = tokenMaps[i].decode(str);
        }

        return str;
    }
}
