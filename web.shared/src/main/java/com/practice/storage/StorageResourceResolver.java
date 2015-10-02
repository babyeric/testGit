package com.practice.storage;

import com.juric.carbon.schema.storage.path.StoragePath;
import org.juric.storage.service.StorageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by Eric on 9/22/2015.
 */
public class StorageResourceResolver implements ResourceResolver {

    private StorageService storageService;

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return getResource(requestPath);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return (getResource(resourcePath) != null ? resourcePath : null);
    }

    private Resource getResource(String requestPath) {
        StoragePath storagePath = StoragePathWebMvcCodec.decode(requestPath);
        File file = storageService.toFile(storagePath, false);
        return new FileSystemResource(file);
    }
}
