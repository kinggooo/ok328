package club.ok328.wnz.srv.vo;

import javax.validation.constraints.NotNull;

/**
 * @author WANGNANZHI
 * @description Vue登录页面demo信息对象实体
 * @date 2019年3月25日 下午10:57:53
 * @Copyright 版权所有 (c) www.ok328.com
 * @memo 备注信息
 */
public class LoginInfoVo {

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "密码不允许为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
