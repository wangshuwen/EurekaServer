package com.cst.xinhe.base.result;


import com.alibaba.fastjson.JSON;
import com.cst.xinhe.base.enums.ResultEnum;

/**
 * 返回统一结果 工具类
 *
 * @auther li
 * @date 2018/1/3-14:26
 */
public class ResultUtil {

    /**
     * 返回success结果值
     *
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(666);
        result.setMsg("成功！");
        result.setData(object);
        return result;
    }

    /**
     * 返回success结果值
     *
     * @param msg
     * @return
     */
    public static Result success(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回Success结果值 json to string
     *
     * @param object
     * @return
     */
    public static String jsonToStringSuccess(Object object) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("成功！");
        result.setData(object);
        String str = JSON.toJSONString(result);
        return str;
    }
    /**
     * 返回Success结果值 json to string
     *
     * @param object
     * @return
     */
    public static String jsonToStringSuccess(String msg, Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        String str = JSON.toJSONString(result);
        return str;
    }

    /**
     * 返回Success结果值 json to string
     *
     * @param object
     * @return
     */
//    public static String jsonToStringSuccess(String msg) {
//        Result result = new Result();
//        result.setCode(200);
//        result.setMsg(msg);
//        result.setData(null);
//        String str = JSON.toJSONString(result);
//        return str;
//    }
    /**
     * 返回Success结果值 json to string
     *
     * @param resultEnum
     * @return
     */
//    public static String jsonToStringSuccess(ResultEnum resultEnum) {
//        Result result = new Result();
//        result.setCode(resultEnum.getCode());
//        result.setMsg(resultEnum.getMsg());
//        result.setData(null);
//        String str = JSON.toJSONString(result);
//        return str;
//    }

    /**
     * 返回Success结果值 json to string
     *
     * @return
     */
    public static String jsonToStringSuccess() {
        return jsonToStringSuccess(null);
    }

    public static Result success() {
        return success(null);
    }

    /**
     * 返回错误结果方法
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * 返回Error结果值 json to string
     *
     * @param code
     * @param msg
     * @return
     */
    public static String jsonToStringError(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        String str = JSON.toJSONString(result);
        return str;
    }

    /**
     * 枚举类型参数的返回工具类方法
     *
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(null);
        return result;
    }

    /**
     * 返回Error结果值 json to string
     *
     * @param resultEnum
     * @return
     */
    public static String jsonToStringError(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(null);
        String str = JSON.toJSONString(result);
        return str;
    }


}
