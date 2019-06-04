package srv.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import srv.common.Result;
import srv.common.ResultFactory;
import srv.vo.UserInfo;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
@RequestMapping(value = "/mongo")
public class MongoController {
    private static Logger log = LoggerFactory.getLogger(MongoController.class);

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
    public Result addUser(@RequestBody UserInfo userInfo) {
        userInfo.setCreateTime(new Date());
        mongoTemplate.insert(userInfo);
        return ResultFactory.buildSuccessResult(userInfo);
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.GET)
    @ResponseBody
    public Result saveUser(@RequestBody UserInfo userInfo) {
        userInfo.setCreateTime(new Date());
        mongoTemplate.save(userInfo);
        return ResultFactory.buildSuccessResult(userInfo);
    }

    @RequestMapping(value = "/findUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result findUserById(@PathVariable String id) {
        UserInfo userInfo = mongoTemplate.findById(id, UserInfo.class);
        return ResultFactory.buildSuccessResult(userInfo);
    }

    @RequestMapping(value = "/findUser/{cond}/{value}", method = RequestMethod.GET)
    @ResponseBody
    public Result findUserByCond(@PathVariable String cond, @PathVariable String value) {
        Criteria ct = where(cond).in(value);
        List<UserInfo> userInfo = mongoTemplate.find(query(ct), UserInfo.class);
        return ResultFactory.buildSuccessResult(userInfo);
    }

    @RequestMapping(value = "/updateUser/{id}/{age}", method = RequestMethod.GET)
    @ResponseBody
    public Result updateUser(@PathVariable String id, @PathVariable String age) {
        Criteria ct = where("id").in(id);
        Update upd = new Update();
        upd.set("age", age);
        UpdateResult result = mongoTemplate.updateMulti(query(ct), upd, UserInfo.class);
        return ResultFactory.buildSuccessResult(result.getModifiedCount());
    }

    @RequestMapping(value = "/removeUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result removeUser(@PathVariable String id) {
        Criteria ct = where("id").in(id);
        DeleteResult result = mongoTemplate.remove(query(ct), UserInfo.class);
        return ResultFactory.buildSuccessResult(result.getDeletedCount());
    }
}
