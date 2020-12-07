package club.ok328.wnz.srv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import club.ok328.wnz.srv.common.Result;
import club.ok328.wnz.srv.common.ResultFactory;
import club.ok328.wnz.srv.vo.LoginInfoVo;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 登录控制器，前后端分离用的不同协议和端口，所以需要加入@CrossOrigin支持跨域。
     * 给VueLoginInfoVo对象加入@Valid注解，并在参数中加入BindingResult来获取错误信息。
     * 在逻辑处理中我们判断BindingResult知否含有错误信息，如果有错误信息，则直接返回错误信息。
     */
    @CrossOrigin
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result login(@Valid @RequestBody LoginInfoVo loginInfoVo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = String.format("登陆失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }

        if (!"wangnz".equals(loginInfoVo.getUsername()) || !"123456".equals(loginInfoVo.getPassword())) {
            String message = String.format("登陆失败，详细信息[用户名、密码信息不正确]。");
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult("登陆成功。");
    }
}
