package srv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import srv.common.Result;
import srv.common.ResultFactory;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Environment env;// 读取环境变量

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    @ResponseBody
    public Result test1() {
        return ResultFactory.buildSuccessResult(env.getProperty("spring.application.name"));
    }
}
