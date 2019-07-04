package club.ok328.www.srv.controller;

import club.ok328.www.srv.common.Result;
import club.ok328.www.srv.common.ResultFactory;
import club.ok328.www.srv.entity.LoanApplyInfo;
import club.ok328.www.srv.mapper.LoanApplyInfoMapper;
import club.ok328.www.srv.vo.LoginInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DBController {
    private static Logger log = LoggerFactory.getLogger(DBController.class);
    @Autowired
    LoanApplyInfoMapper loanApplyInfoMapper;

    @CrossOrigin
    @GetMapping(value = "/queryAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result login() {
        List<LoanApplyInfo> ls = loanApplyInfoMapper.selectByCond();
        return ResultFactory.buildSuccessResult(ls);
    }
}
