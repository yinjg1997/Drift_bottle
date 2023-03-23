package cn.edu.zust.common.exception;


import cn.edu.zust.common.result.ResponseCode;
import cn.edu.zust.common.result.ResultMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice // 定义该类为全局异常处理类,就是一个特殊的@Controller, 他会拦截标注有@Controller的控制器触发的异常
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class) // 定义该方法为 BusinessException 异常处理方法
    @ResponseBody
    public ResultMode handlerCCWException(HttpServletRequest request, Exception ex) {

        BusinessException tempEX = (BusinessException) ex;
        int code = tempEX.getCode();
        String msg = tempEX.getMsg();
        // 由于我打算在BussinessEx中强制只使用Response,所以不需要这一步了
        // ResponseCode resultMsgCode = tempEX.getResultMsgCode();
        // if (code == 0) {
        //     if (resultMsgCode == null) {
        //         resultMsgCode = ResponseCode.SYSTEM_INNER_ERROR;
        //     }
        //     code = resultMsgCode.getCode();
        //     msg = resultMsgCode.getMsg();
        // }
        LOGGER.error("\n path: {}\n errorCode: {}\n msg:{}", request.getServletPath(), code, msg);

        return ResultMode.error(code, msg, ex.getMessage());
    }

    // 通用的异常捕获
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultMode<String> handlerException(HttpServletRequest request, Exception ex) {
        LOGGER.error("\npath : {}\n", request.getServletPath(), ex);
        return ResultMode.error(ResponseCode.SYSTEM_INNER_ERROR, ex.getMessage());
    }

    /**
     * 请求参数不合法
     * {
     *     "ok": false,
     *     "code":10001
     *     "message": "参数无效",
     *     "data": [
     *         "年龄必须在[1,120]之间",
     *         "bg 字段的整数位最多为3位，小数位最多为1位",
     *         "name 不能为空",
     *         "email 格式错误"
     *     ]
     * }
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultMode<List<String>> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultMode.error(ResponseCode.PARAM_IS_INVALID, collect);
    }



    /**
     * 获得http状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
    /**
     * 获取校验错误信息
     * @param fieldErrors
     * @return
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        List<String> errorList = new ArrayList<String>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }

}
