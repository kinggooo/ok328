package srv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import srv.common.Result;
import srv.common.ResultFactory;
import srv.service.TestService;
import srv.vo.UserInfo;

@RestController
@RequestMapping(value = "/cache")
public class CacheController {
    private static Logger log = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private Environment env;// 读取环境变量

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TestService testServiceImpl;

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getUser(@PathVariable String id) {
        UserInfo user = testServiceImpl.getUser(id);
        return ResultFactory.buildSuccessResult(user);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    @ResponseBody
    public Result updateUser(@RequestBody UserInfo updUser) {
        UserInfo user = testServiceImpl.updateUser(updUser);
        return ResultFactory.buildSuccessResult(user);
    }

    @RequestMapping(value = "/evictUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result evictUser(@PathVariable String id) {
        testServiceImpl.evictUser(id);
        return ResultFactory.buildSuccessResult(null);
    }
}
