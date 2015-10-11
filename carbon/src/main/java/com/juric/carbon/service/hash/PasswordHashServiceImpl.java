package com.juric.carbon.service.hash;

/**
 * Created by Eric on 10/11/2015.
 */
public class PasswordHashServiceImpl implements PasswordHashService {
    public HashVersion systemHashVersion;

    public void setSystemHashVersion(HashVersion hashVersion) {
        systemHashVersion = hashVersion;
    }

    @Override
    public byte[] hash(byte[] input, HashVersion currentVersion, HashVersion targetVersion) {
        byte[] ret = input;
        HashVersion[] hashVersions = HashVersion.findRoute(currentVersion, targetVersion);
        for(HashVersion hashVersion : hashVersions) {
            ret = hashVersion.getAlgorithm().hash(ret);
        }
        return ret;
    }

    @Override
    public byte[] hash(byte[] input, HashVersion currentVersion) {
        return hash(input, currentVersion, systemHashVersion);
    }
}
