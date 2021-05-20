package com.tx.base.exception;

import com.tx.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理器
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 捕获所有异常
    @ExceptionHandler(Exception.class)
    // 确保返回json数据
    @ResponseBody
    public CommonResult error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return CommonResult.error().message("服务器发生错误");
    }

    // 处理自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public CommonResult error(MyException e) {
        e.printStackTrace();
        return CommonResult.error().message(e.getMsg()).code(e.getCode());
    }
}
