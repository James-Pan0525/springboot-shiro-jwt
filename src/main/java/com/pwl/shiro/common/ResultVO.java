package com.pwl.shiro.common;

import java.io.Serializable;

/**
 * @author Pan Weilong
 * @date 2019/6/20 15:03
 * @description: 结果统一返回
 */
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS = 200;

    public static final int FAIL = 1;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public ResultVO() {
        super();
    }

    public ResultVO(T data) {
        super();
        this.data = data;
    }

    public ResultVO(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public ResultVO(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    /**
     *
     * 返回成功
     * @param data
     * @return
     */
    public ResultVO<T> returnSuccess(T data) {
        this.data = data;
        return this;
    }

    /**
     *
     * 返回失败
     *
     * @param code
     * @param msg
     * @return
     */
    public ResultVO<T> returnFail(Integer code , String msg) {

        this.code = code;

        this.msg = msg;

        return  this;
    }

    public ResultVO<T> returnFail(String msg) {

        this.code = 500;

        this.msg = msg;

        return  this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
