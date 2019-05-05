package srv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import srv.common.Result;
import srv.common.ResultFactory;
import srv.vo.User;

import java.util.Date;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Environment env;// 读取环境变量

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseBody
    public Result test1() {
        return ResultFactory.buildSuccessResult(env.getProperty("spring.application.name"));
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    @ResponseBody
    public Result addUser(@RequestBody User user) {
        user.setCreateTime(new Date());
        mongoTemplate.insert(user);
        return ResultFactory.buildSuccessResult(user);
    }
}
