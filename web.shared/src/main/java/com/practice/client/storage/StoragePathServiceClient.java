package com.practice.client.storage;

import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.schema.storage.path.EnumRepository;
import com.juric.carbon.schema.storage.path.EnumSchema;
import com.juric.carbon.schema.storage.path.StoragePath;
import com.practice.client.common.AbstractServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by Eric on 9/28/2015.
 */
public class StoragePathServiceClient extends AbstractServiceClient implements StoragePathService {

    @Override
    public StoragePath generatePath(EnumRepository repo, EnumSchema schema, Long shardParam, String ext) {
        String url = carbonRoot + "/1/storage";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("repo", repo)
                .queryParam("schema", schema)
                .queryParam("shardParam", shardParam)
                .queryParam("ext", ext);

        StoragePath storagePath = restTemplate.postForObject(builder.toUriString(), null, StoragePath.class);

        return storagePath;
    }
}
