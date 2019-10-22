package com.china.chen.quantitativecoretrade.response.common;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isSuccess = true;
    private Integer code = ResultCode.SUCCESS.getCode();
    private String message = "success";
    private String debugMessage ;
    private T data;
    /**
     * 列字段
     */
    private List<T> field;

    public ResultBean(Integer code, String msg, T data, List<T> field) {
        super();
        this.code = code;
        this.message = msg;
        this.data = data;
        this.field = field;
        this.isSuccess = (this.code == ResultCode.SUCCESS.getCode());
    }

    public ResultBean(Integer code, String msg, T data, List<T> field,String debugMessage) {
        super();
        this.code = code;
        this.message = msg;
        this.data = data;
        this.field = field;
        this.isSuccess = (this.code == ResultCode.SUCCESS.getCode());
        this.debugMessage = debugMessage ;
    }


    public ResultBean(Integer code, String msg, List<T> field) {
        this.code = code;
        this.message = msg;
        this.field = field;
        this.isSuccess = (this.code == ResultCode.SUCCESS.getCode());
    }

    public static <T> ResultBean<T> success() {
        return new ResultBean(ResultCode.SUCCESS.getCode(), null, null, null);
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean(ResultCode.SUCCESS.getCode(), null, data, null);
    }

    public static <T> ResultBean<T> success(String msg, T data) {
        return new ResultBean(ResultCode.SUCCESS.getCode(), msg, data, null);
    }

    public static <T> ResultBean<T> fail() {
        return new ResultBean(ResultCode.FAIL.getCode(), null, null, null);
    }

    public static <T> ResultBean<T> fail(String msg) {
        return new ResultBean(ResultCode.FAIL.getCode(), msg, null, null);
    }

    public static <T> ResultBean<T> fail(Integer code, String msg) {
        return new ResultBean(code, msg, null, null);
    }

    public static <T> ResultBean<T> serverError(String msg) {
        return new ResultBean(ResultCode.SERVER_ERROR.getCode(), msg, null, null);
    }


    public static <T> ResultBean<T> code(Integer code) {
        return new ResultBean(code, null, null, null);
    }

    public static <T> ResultBean<T> codeWithDebugInfo(Integer code,String debugMessage) {
        return new ResultBean(code, null, null, null,debugMessage);
    }

    public static <T> ResultBean<T> code(Integer code, String msg) {
        return new ResultBean(code, msg, null, null);
    }

    public static <T> ResultBean<T> code(Integer code, String msg,String debugMessage) {
        return new ResultBean(code, msg, null, null,debugMessage);
    }


    @Override
    public String toString() {
        return "ResultBean{" +
                "success=" + isSuccess +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + (data == null ? "null" : data.toString()) +
                '}';
    }
}
