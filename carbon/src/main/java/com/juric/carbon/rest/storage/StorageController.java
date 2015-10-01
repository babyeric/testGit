package com.juric.carbon.rest.storage;

import com.juric.carbon.rest.mvc.Version;
import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;
import com.practice.exception.ValidationException;
import com.practice.rest.AbstractRestController;
import org.juric.storage.configurer.StorageConfiguration;
import org.juric.storage.service.StorageService;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/29/15
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */

@Import(StorageConfiguration.class)
@Version("1")
@RestController()
@RequestMapping("/storage")
public class StorageController extends StorageControllerSupport {
    @Resource(name = "storageService")
    private StorageService storageService;

    @RequestMapping(method = RequestMethod.POST)
    public StoragePath createPath(@RequestParam(value="repo") EnumRepository repo,
                                  @RequestParam(value="schema") EnumSchema schema,
                                  @RequestParam(value="shardParam") Long shardParam,
                                  @RequestParam(value="ext") String ext) {

        return storageService.generateStoragePath(repo, schema, shardParam, ext);
        //throw new ValidationException("abc");
    }


}
