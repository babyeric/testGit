package com.practice.client.storage;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 9/28/2015.
 */
public class StoragePathServiceClientImpl implements StoragePathServiceClient {

    @Override
    public StoragePath generateStoragePath(EnumRepository repo, EnumSchema schema, Long shardParam, String ext) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8090/1/storage")
                .queryParam("repo", repo)
                .queryParam("schema", schema)
                .queryParam("shardParam", shardParam)
                .queryParam("ext", ext);

        StoragePath storagePath = restTemplate.postForObject(builder.toUriString(), null, StoragePath.class);

        return storagePath;
    }
}
