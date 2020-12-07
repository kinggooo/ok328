package club.ok328.wnz.srv.common;

/**
 * @author WANGNANZHI
 * @description 统一 API响应结果封装
 * @date 2019年3月25日 下午10:57:53
 * @Copyright 版权所有 (c) www.ok328.com
 * @memo 控制Result权限
 */
public class Result {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应提示信息
     */
    private String message;
    /**
     * 响应结果对象
     */
    private Object data;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

