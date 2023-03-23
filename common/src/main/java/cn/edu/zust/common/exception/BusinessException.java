package cn.edu.zust.common.exception;


import cn.edu.zust.common.result.ResponseCode;

public class BusinessException extends RuntimeException{

    private final Integer code;
    private final String msg;
    private ResponseCode resultMsgCode;

    public BusinessException(ResponseCode responseCode) {
        if (responseCode == null) {
            this.resultMsgCode = ResponseCode.SYSTEM_INNER_ERROR;
        }
        else {
            this.resultMsgCode = responseCode;
        }
        this.code = resultMsgCode.getCode();
        this.msg = resultMsgCode.getMsg();
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseCode getResultMsgCode() {
        return resultMsgCode;
    }
}
