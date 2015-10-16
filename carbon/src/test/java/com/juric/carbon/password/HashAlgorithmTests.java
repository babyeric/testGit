package com.juric.carbon.password;

import com.juric.carbon.service.user.password.HashAlgorithm;
import com.juric.carbon.service.user.password.HashVersion;
import com.juric.carbon.service.user.password.SHA512$1000;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Eric on 10/11/2015.
 */
public class HashAlgorithmTests {

    @Test
    public void testHashVersion() {
        Assert.assertEquals(0, HashVersion.SHA512_1000.value());

        Assert.assertEquals(HashVersion.SHA512_1000, HashVersion.fromValue(0));

        HashVersion[] route = HashVersion.findRoute(null, HashVersion.SHA512_1000);
        Assert.assertEquals(1, route.length);
        Assert.assertEquals(HashVersion.SHA512_1000, route[0]);

        route = HashVersion.findRoute(HashVersion.SHA512_1000, HashVersion.SHA512_1000);
        Assert.assertEquals(0, route.length);
    }

    @Test
    public void testSHA512$1000() {
        HashAlgorithm algorithm = new SHA512$1000();
        byte[] input = "test string as input".getBytes();
        byte[] output = algorithm.hash(input);
        Assert.assertTrue(ArrayUtils.isEquals(64, output.length));
        Assert.assertEquals(-70, (int) output[0]);
        Assert.assertEquals(-14, (int) output[63]);
    }
}
