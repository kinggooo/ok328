package club.ok328.wnz.srv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import club.ok328.wnz.srv.common.Result;
import club.ok328.wnz.srv.common.ResultFactory;

@RestController
@RequestMapping(value = "/redis")
public class RedisController {
    private static Logger log = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private Environment env;// 读取环境变量

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/rdsSet/{key}/{val}", method = RequestMethod.GET)
    @ResponseBody
    public Result rdsSet(@PathVariable String key, @PathVariable String val) {
        redisTemplate.opsForValue().set(key, val);
        return ResultFactory.buildSuccessResult(redisTemplate.opsForValue().get(key));
    }
}
