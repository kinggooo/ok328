package club.ok328.wnz.ms.controller;

import club.ok328.wnz.ms.vo.AppliedCompanyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    private String HkAppliedCompanyAscCacheKey = "emis:datacenter:ipocenter:HkAppliedCompanyZSetTest";

    @RequestMapping(value = "/setObj", method = RequestMethod.GET)
    @ResponseBody
    public String setObj(Model model) {
        AppliedCompanyVo item = new AppliedCompanyVo();
        item.setName("aaa");
        item.setStatus("1");
        item.setDate("100000000");
        redisTemplate.opsForZSet().add(HkAppliedCompanyAscCacheKey, item, Integer.valueOf(item.getDate()));
        item = new AppliedCompanyVo();
        item.setName("bbb");
        item.setStatus("2");
        item.setDate("100000001");
        redisTemplate.opsForZSet().add(HkAppliedCompanyAscCacheKey, item, Integer.valueOf(item.getDate()));
        return "ok";
    }

    @RequestMapping(value = "/getObj", method = RequestMethod.GET)
    @ResponseBody
    public String getObj(Model model) {
        Set<AppliedCompanyVo> datasSet = new HashSet<>();
        datasSet = redisTemplate.opsForZSet().rangeByScore(HkAppliedCompanyAscCacheKey, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 2);
        for (AppliedCompanyVo item : datasSet) {
            System.out.println(item.getName());
        }

        return "ok";
    }

}
