package com.source.util;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/10/13/10:35
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    // 成功
    private static final Integer SUCCESS = 0;
    // 警告
    private static final Integer WARN = 1;
    // 异常 失败
    private static final Integer FAIL = 500;
    // 未认证
    private static final Integer UNAUTHORIZED = 401;
    // 超频
    private static final Integer OVERCLOCKING = 666;

    public Result() {
        put("code", SUCCESS);
        put("msg", "操作成功");
    }

    public static Result error(Object msg) {
        Result result = new Result();
        result.put("code", FAIL);
        result.put("msg", msg);
        return result;
    }

    public static Result warn(Object msg) {
        Result result = new Result();
        result.put("code", WARN);
        result.put("msg", msg);
        return result;
    }

    public static Result ok(Object msg) {
        Result result = new Result();
        result.put("code", SUCCESS);
        result.put("msg", msg);
        return result;
    }

    public static Result unAuthorized(Object msg) {
        Result result = new Result();
        result.put("code", UNAUTHORIZED);
        result.put("msg", msg);
        return result;
    }

    public static Result overClocking(Object msg) {
        Result result = new Result();
        result.put("code", OVERCLOCKING);
        result.put("msg", msg);
        return result;
    }

    public static Result ok() {
        return new Result();
    }

    public static Result error() {
        return Result.error("");
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
