package com.juric.carbon.service.hash;

/**
 * Created by Eric on 10/11/2015.
 */
public interface PasswordHashService {
    byte[] hash(byte[] input, HashVersion currentVersion);
    byte[] hash(byte[] input, HashVersion currentVersion, HashVersion targetVersion);
}
