package com.juric.carbon.rest.storage;

import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.configuration.CarbonConfiguration;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.storage.path.EnumRepository;
import com.juric.carbon.schema.storage.path.EnumSchema;
import com.juric.carbon.schema.storage.path.StoragePath;
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

@Import(CarbonConfiguration.class)
@Version("1")
@RestController()
@RequestMapping("/storage")
public class StorageController extends StorageControllerSupport {
    @Resource(name = "storagePathService")
    private StoragePathService storagePathService;

    @RequestMapping(method = RequestMethod.POST)
    public StoragePath createPath(@RequestParam(value="repo") EnumRepository repo,
                                  @RequestParam(value="schema") EnumSchema schema,
                                  @RequestParam(value="shardParam") Long shardParam,
                                  @RequestParam(value="ext") String ext) {

        return storagePathService.generatePath(repo, schema, shardParam, ext);
    }
}
