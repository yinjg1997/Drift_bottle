package cn.edu.zust.common.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;

@ApiModel(value = "统一返回结果集")
public class ResultMode<T> {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;




    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private ResultMode(){}
    private ResultMode(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    private ResultMode(Boolean success, ResponseCode responseCode, T data) {
        this.success = success;
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.data = data;
    }



    public static <T> ResultMode<T> ok(){
        return new ResultMode<T>(true, ResponseCode.SUCCESS, null);
    }
    public static <T> ResultMode<T> ok(T data){
        return new ResultMode<T>(true, ResponseCode.SUCCESS, data);
    }
    // 上面两个简化版本
    public static <T> ResultMode<T> ok(Integer code, String message, T data){
        return new ResultMode<T>(true, code, message, data);
    }
    public static <T> ResultMode<T> ok(ResponseCode responseCode, T data){
        return new ResultMode<T>(true, responseCode, data);
    }


    public static <T> ResultMode<T> error(){
        return new ResultMode<T>(false, ResponseCode.ERROR, null);
    }
    public static <T> ResultMode<T> error(T data){
        return new ResultMode<T>(false, ResponseCode.ERROR, data);
    }
    // 上面两个简化版本
    public static <T> ResultMode<T> error(Integer code, String message, T data){
        return new ResultMode<T>(false, code, message, data);
    }
    public static <T> ResultMode<T> error(ResponseCode responseCode, T data){
        return new ResultMode<T>(false, responseCode, data);
    }

    @Override
    public String toString() {
        return "ResultMode{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
