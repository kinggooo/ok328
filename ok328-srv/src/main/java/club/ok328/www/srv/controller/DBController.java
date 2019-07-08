package club.ok328.www.srv.controller;

import club.ok328.www.srv.common.Result;
import club.ok328.www.srv.common.ResultFactory;
import club.ok328.www.srv.entity.UserInfo;
import club.ok328.www.srv.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DBController {
    private static Logger log = LoggerFactory.getLogger(DBController.class);

    @Autowired
    UserInfoMapper userInfoMapper;

    @CrossOrigin
    @GetMapping(value = "/queryAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result login() {
        List<UserInfo> ls = userInfoMapper.selectByCond();
        return ResultFactory.buildSuccessResult(ls);
    }
}
